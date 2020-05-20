package e_2020.events;

import e_2020.RandomWrapper;

/**
 * @author evenal
 */
// Kaller Event for AbstractEvent istedenfor
public abstract class AbstractEvent implements Comparable<AbstractEvent> {

	public final int day;
	// tilgjengelig for alle subklasser av AbstractEvent
	protected RandomWrapper random;

	public AbstractEvent(int day) {
		this.day = day;
		random = RandomWrapper.getInstance();
	}

	public abstract void happen();

	public int getDay() {
		return day;
	}

	@Override
	public int compareTo(AbstractEvent that) {
		return (this.day - that.day);
	}
}