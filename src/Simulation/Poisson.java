package Simulation;

import java.util.Random;


/* TODO:
    - change lines 100, 112, 115 to work with Time objects
        --> comparison or computations of Time objects
 */


/**
 * Generate random numbers from a Poisson distribution
 * using the Thinning algorithm by Lewis and Shedler,
 * which we saw on slide 13, of the first clip of Lecture 7
 */
public class Poisson implements Distribution {
    private Random rnd;
    private double lambdaStar;                  // the maximum rate of arrival
    private Time prevArrTime;
    private LambdaT lambdaGivenTime;

    /**
     * Non-fully parametric constructor for the Poisson distribution.
     * NOTE that the parameter lambdaGivenTime still has to be set BEFORE calling drawRandom()
     *
     * @param lambdaStar the maximum rate of the Poisson distribution at any given time
     */
    public Poisson(double lambdaStar) {
        this(lambdaStar, null);
    }

    /**
     * Full parametric constructor for this class
     *
     * @param lambdaStar the maximum rate of the Poisson distribution at any given time
     * @param lambdaT object giving the value of lambda(t) for a given t
     */
    public Poisson (double lambdaStar, LambdaT lambdaT) {
        this(lambdaStar, lambdaT, new Time(0));
    }

    /**
     * Full parametric constructor for this class
     *
     * @param lambdaStar the maximum rate of the Poisson distribution at any given time
     * @param lambdaT object giving the value of lambda(t) for a given t
     * @param previousArrivalTime the last generated arrival time, by default holds value 0
     */
    public Poisson (double lambdaStar, LambdaT lambdaT, Time previousArrivalTime) {
        // Save the given parameters in the corresponding variables
        this.lambdaStar = lambdaStar;
        this.lambdaGivenTime = lambdaT;
        this.prevArrTime = previousArrivalTime;

        // Initialize the random number generator
        rnd = new Random();
    }

    /**
     * Setter for lambdaGivenTime
     *
     * @param rate the object giving the rate of arrivals for a certain time
     */
    public void setLambdaT(LambdaT rate) {
        this.lambdaGivenTime = rate;
    }

    /**
     * Setter for prevArrTime
     *
     * @param newValue the new value of the previous arrival time
     */
    public void setPreviousArrivalTime(Time newValue) {
        this.prevArrTime = newValue;
    }

    /**
     * Getter for prevArrTime
     *
     * @return the value of the last arrival time
     */
    public Time getPreviousArrivalTime() {
        return this.prevArrTime;
    }

    /**
     * Generates a random number according to a Poisson distribution,
     * according to the given rate lambdaGivenTime.getLambda(t), with maximum value lambdaStar
     * The random variates are generated using the Thinning algorithm by Lewis and Shedler.
     *
     * Only works if lambdaStar is not null
     *
     * @return the generated random variate
     */
    public Time drawRandom () {
        // Make sure that lambdaGivenTime is not null before attempting to generate a new random variate
        if (this.lambdaGivenTime != null) {
            /* -------------------------------------------- TODO ----------------------------------------- */
            double t = this.prevArrTime;

            double u1, u2;
            do {
                u1 = this.rnd.nextDouble();
                u2 = this.rnd.nextDouble();

                /** TODO THIS HAS TO BE REPLACED TO USE A TIME OBJECT */
                t = t - (1 / this.lambdaStar) * Math.log(u1);
            } while (u2 > (this.lambdaGivenTime.getLambda(t) / this.lambdaStar));

            /* -------------------------------------------- TODO ----------------------------------------- */
            this.prevArrTime = t;

            /* -------------------------------------------- TODO ----------------------------------------- */
            return t;
        }
        // If lambdaGivenTime has not yet been set, we cannot generate a random variate
        else {
            throw new NullPointerException("Variable lambdaGivenTime has to be instantiated to non-null value before generating random Poisson variates.");
        }
    }
}