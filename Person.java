package e_2020;
/**
 *
 * @author evenal
 */
public class Person {
	String name;
	State state;
	// om sickDuration når 0 går personen over til Immun
	int sickDuration;

	public Person(String name, State state) {
		this.name = name;
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getSickDuration() {
		return sickDuration;
	}

	public void decrementSickDuration() {
		if(sickDuration > 0) {
			sickDuration--;
		}
		if(PandemiSim.debugging) {
			System.out.println("\t\t[Person]sickDuration after for " + name + " : " + sickDuration);
		}
	}

	public void setSickDuration(int sickDuration) {
		this.sickDuration = sickDuration;
	}
}
