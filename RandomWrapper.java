package e_2020;

import java.util.Random;

public class RandomWrapper {

	private static RandomWrapper theInstance;

	public static RandomWrapper getInstance() {
		if (theInstance == null)
			theInstance = new RandomWrapper();
		return theInstance;
	}

	private final Random random;

	private RandomWrapper() {
		random = new Random();
		// Bruk seed for samme resultat hver gang
		//random.setSeed(20200520);
	}

	/* return a random value between 0 and 1 */
	public double nextDouble() {
		return random.nextDouble();
	}

	/* return a random int */
	public int nextInt() {
		return random.nextInt();
	}

	/* return an int between zero and max */
	public int nextInt(int max) {
		return random.nextInt(max);
	}

	/* return an int between min and max */
	public int nextInt(int min, int max) {
		return min + random.nextInt(max - min);
	}

	/**
	 * Decide whether an event with the given probability will happen. For
	 * eksample: If there is a 25 % probability of rain, call this method with
	 * an argument of 0.25
	 *
	 * @param probability
	 * @return true, if the next random double is within the specified
	 * probability
	 */
	public boolean isNextDoubleWithinProbability(double probability) {
		return random.nextDouble() < probability;
	}
}
