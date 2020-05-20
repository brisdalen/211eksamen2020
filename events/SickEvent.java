package e_2020.events;

import e_2020.*;

import static e_2020.Conf.*;

/**
 * SickEvent tar for seg når en Person blir smittet. Det vil avgjøres hvor lenge han/hun (h@n) vil være smittet, og om personen
 * dør eller blir immun etter dagen er omme.
 */
public class SickEvent extends AbstractEvent {

	Person person;
	PandemiSim sim;

	public SickEvent(int day, Person person, PandemiSim sim) {
		super(day);
		this.person = person;
		this.sim = sim;
		// Sett Personens sickDuration til mellom MIN_SICK_DAYS og MAX_SICK_DAYS, men kun om h@n ikke allerede er smitta
		if(person.getState() == State.CLEAN) {
			person.setState(State.SICK);
			this.person.setSickDuration(random.nextInt(MIN_SICK_DAYS, MAX_SICK_DAYS));
		}
	}

	public Person getPerson() {
		return person;
	}

	/**
	 * sjekker først hvor mange CLEAN personer den syke personen møter på denne dagen.
	 * Til slutt sjekkes det om personen overlever dagen.
	 */
	@Override
	public void happen() {
		//System.out.println("[SickEvent]happen() @ day " + day + " for person - " + person.getName());
		/* Jeg plusser på 1 siden den logiske meningen til MAX CONCTACTS inkluderer den øvre grensa, mens metoden
		 ekskluderer denne øvre grensa. Med andre ord, uten +1 returnerte random.nextInt kun 0, 1 eller 2. */
		int numberOfPeopleMetToday = random.nextInt(MAX_CONTACTS_PER_DAY + 1);
		if(sim.isDebugging()) {
			System.out.println("[SickEvent]Person " + person.getName() + " meets " + numberOfPeopleMetToday + " people this day.");
		}
		for(int i = 0; i < numberOfPeopleMetToday; i++) {
			Person other = sim.getRandomPerson();
			if(other.getState() == State.CLEAN) {
				// Om den friske personen blir smittet eller ikke håndteres i CloseEncounter
				sim.addEvent(new CloseEncounter(day, other, sim));
			}
		}
		// Sjekk om Personen overlever denne dagen
		boolean doesDie = random.isNextDoubleWithinProbability(DEATH_PROBILITY);
		if(doesDie) {
			person.setState(State.DEAD);
			sim.decrementSick();
			sim.incrementDead();
			if(sim.isDebugging()) {
				System.out.println("\t[SickEvent]Person " + person.getName() + " died at day " + day + ".");
			}
		} else {
			if(sim.isDebugging()) {
				System.out.println("\t[SickEvent]Person " + person.getName() + " survives another day.");
			}
			// dekrementer sickDuration om h@n overlever
			person.decrementSickDuration();
			// om sickDuration når 0 går personen over til IMMUNE
			if(person.getSickDuration() == 0) {
				person.setState(State.IMMUNE);
				sim.decrementSick();
				sim.incrementImmune();
			// ellers legges et nytt SickEvent til eventQueue med samme person for neste dag
			} else {
				sim.addEvent(new SickEvent(day+1, person, sim));
			}
		}
		if(sim.isDebugging()) {
			// Tom print-statement for å skille personer
			System.out.println();
		}
	}
}
