package e_2020;

/**
 * The simulation parameters
 */
public interface Conf {
	// Orignal value: 100
	int MAX_DAY = 100;
	// Original value: 3
	int MAX_CONTACTS_PER_DAY = 3;
	// Original value: 10000
	int POPULATION = 10000;
	// Original value: 34
	int INITALLY_SICK = 34;

	// Original value: 0.5
	double SICK_PROBABILITY = 0.5;
	/*
		Example mortality rates of known diseases:
		Coronavirus (Italy): 0.14 (Calculated from numbers found May 20th, 2020 from: https://www.worldometers.info/coronavirus/country/italy/)
		SARS-12 (gemeral): 0.11, SARS-12 (high): 0.17 (https://en.wikipedia.org/wiki/Severe_acute_respiratory_syndrome)
	 */
	// Original value: 0.4
	double DEATH_PROBILITY = 0.4;
	// Original value: 3
	int MIN_SICK_DAYS = 3;
	// Original value: 10
	int MAX_SICK_DAYS = 10;
}
