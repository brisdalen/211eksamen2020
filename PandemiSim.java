package e_2020;

import e_2020.events.AbstractEvent;
import e_2020.events.SickEvent;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * PandemiSim is the main class of the application. It contains the main loop,
 * which advances the time, and a secondary loop that goes through the events of
 * the current day
 */
public class PandemiSim implements Conf {

	// PandemiSim uses the singleton pattern
	// to make the single PandemiSim object available everywhere.
	private static final PandemiSim theInstance
			= new PandemiSim();

	public static PandemiSim getInstance() {
		return theInstance;
	}

	// main just starts the sim
	public static void main(String[] args) {
		PandemiSim sim = PandemiSim.getInstance();
		// kjør med true om du ønsker mer detaljer for hver dag
		sim.run(false);
	}

	// the day that are currently being simulated
	private int today;
	// om det skal vises ekstra print-statements eller ei
	static boolean debugging;
	// hele populasjonen (oppgave 2.b)
	private Person[] population;
	// køen med hendelser (oppgave 1.b)
	private PriorityQueue<AbstractEvent> eventQueue;
	// antall personer som aldri har vært smittet
	private int clean;
	// antall syke personer som lever
	private int sick;
	// antall personer som har dødd
	private int dead;
	// antall personer som har overlevd sykdommen og blitt immun
	private int immune;

	public PandemiSim() {
		// oppgave 2b
		population = new Person[POPULATION];
		for(int i = 0; i < POPULATION; i++) {
			population[i] = new Person("P" + i, State.CLEAN);
		}
		RandomWrapper random = RandomWrapper.getInstance();
		// oppgave 1.b
		eventQueue = new PriorityQueue<>();
		clean = POPULATION;
		sick = 0;
		dead = 0;
		immune = 0;
		// Trekk INITIALLY_SICK tilfeldige personer som er smittet
		for(int i = 0; i < INITALLY_SICK; i++) {
			boolean newSick = false;
			while(!newSick) {
				int randNum = nextInt(POPULATION);
				if(population[randNum].state == State.CLEAN) {
					population[randNum].state = State.SICK;
					newSick = true;
					incrementSick();
					decrementClean();
					// oppgave 1.b
					eventQueue.add(new SickEvent(0, population[randNum], this));
				}
			}
		}
		System.out.println("Starting with " + sick + " initial sick people.");
	}

	public boolean isDebugging() {
		return debugging;
	}

	/**
	 *
	 * @param debugging hvis debugging er satt til true vil det komme mer detaljerte print-statements i konsollen. Hvis
	 *                  false vil kun statistikk komme på slutten av hver dag.
	 */
	public void run(boolean debugging) {
		PandemiSim.debugging = debugging;
		for(int day = 0; day < MAX_DAY; day++) {
			if(debugging) {
				System.out.println("run() - day: " + day);
			}
			runDay(day);
		}
	}

	/**
	 * Get all events that happen on the specified day, and make them happen()
	 *
	 * @param day
	 */
	private void runDay(int day) {
		today = day;
		System.out.println("Day: " + today);
		if(debugging) {
			System.out.println("Total events in queue at start of the day: " + eventQueue.size());
		}
		boolean done = false;
		// Når alle events for dagen er sjekket settes done til true
		while(!eventQueue.isEmpty() && !done) {
			if(eventQueue.peek().day == today) {
				// get the next event, Oppgave 1b
				// and let it happen
				eventQueue.poll().happen();
			} else {
				done = true;
			}
		}
		if(debugging) {
			System.out.println("Total events in queue at end of the day: " + eventQueue.size());
		}
		// oppgave 4
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Following statistics for day " + day);
		System.out.println("Clean, non-immune: " + clean);
		System.out.println("Currently sick: " + sick);
		System.out.println("Number of dead people: " + dead);
		System.out.println("Number of people immune: " + immune);
		System.out.println("-------------------------------------------------------------------------");
	}
	/**
	 * oppgave 2.c
	 * @return en person som ikke har state DEAD
	 */
	public Person getRandomPerson() {
		boolean found = false;
		// Om hele, eller nesten hele, populasjonen dør er det en teoretisk sjanse for at dette skaper en evig løkke.
		int limit = 10000;
		// Derfor legges inn en limit og en variabel som holder styr på antall iterasjoner
		int iteration = 0;
		int i = -1;
		/* Dette gjør at ytelsen vil bli tregere for jo fler som dør, ettersom det tar lenger tid å finne en tilfeldig,
		   ikke-død person. Om jeg hadde brukt 2 ArrayLists (én for levende og én for døde) kunne jeg heller brukt
		   while(!levende.isEmpty()) som conditional. */
		while(!found && iteration < limit) {
			i = nextInt(POPULATION);
			if(!population[i].getState().equals(State.DEAD)) {
				found = true;
			}
			iteration++;
		}
		if(iteration > 9998) {
			System.err.println("Had to break.");
		}
		return population[i];
	}
	// brukes i oppgave 2.c
	public int nextInt(int max) {
		RandomWrapper random = RandomWrapper.getInstance();
		return random.nextInt(POPULATION);
	}

	public void addEvent(AbstractEvent e) {
		// Oppgave 1 c
		eventQueue.add(e);
	}

	/**
	 * Dekrementerer antall clean. Antall clean vil alltid minske, siden en syk person enten dør eller blir immun.
	 */
	public void decrementClean() {
		if(clean > 0) {
			clean--;
		}
	}

	/**
	 * Inkrementerer antall syke personer.
	 */
	public void incrementSick() {
		sick++;
	}

	/**
	 * Dekrementerer antall syke når en syk person enten dør eller blir immun.
	 */
	public void decrementSick() {
		sick--;
	}

	/**
	 * Inkrementerer antall døde; til forskjell fra clean, kan antall døde bare øke.
	 */
	public void incrementDead() {
		dead++;
	}

	/**
	 * Inkrementerer antall immune. Som med antall døde, kan dette tallet bare øke.
	 */
	public void incrementImmune() {
		immune++;
	}
}
