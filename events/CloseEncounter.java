package e_2020.events;

import e_2020.PandemiSim;
import e_2020.Person;
import e_2020.State;

import java.util.PriorityQueue;

import static e_2020.Conf.SICK_PROBABILITY;

public class CloseEncounter extends AbstractEvent {

	private Person healthy;
	private PandemiSim sim;

	public CloseEncounter(int day, Person healthy, PandemiSim sim) {
		super(day);
		//this.sick = sick;
		this.healthy = healthy;
		this.sim = sim;
	}

	@Override
	public void happen() {
		// Sjekk om den friske personen blir syk av å møte den syke personen
		boolean getsSick = random.isNextDoubleWithinProbability(SICK_PROBABILITY);
		if(getsSick) {
			if(sim.isDebugging()) {
				System.out.println("[CloseEncounter]" + healthy.getName() + " is infected on day " + day);
			}
			sim.decrementClean();
			sim.incrementSick();
			// Jeg velger at de ikke viser symptomer før den neste dagen, derav day+1
			sim.addEvent(new SickEvent(day+1, healthy, sim));
		}
	}
}
