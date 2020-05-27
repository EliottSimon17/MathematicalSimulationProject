package Simulation;

import java.util.Random;



/**
 * Generate random numbers from a Poisson distribution
 * using the Thinning algorithm by Lewis and Shedler,
 * which we saw on slide 13, of the first clip of Lecture 7
 */
public class Poisson implements Distribution {
    private Random rnd;
    private double lambdaStar;              // the maximum rate of arrivals per second
    private Time prevArrTime;
    private LambdaT lambdaGivenTime;        // the rate of arrivals at time t per second

    private String name;

    /**
     * Non-fully parametric constructor for the Poisson distribution.
     * NOTE that the parameter lambdaGivenTime still has to be set BEFORE calling drawRandom()
     *
     * @param lambdaStar the maximum rate of the Poisson distribution at any given time of arrivals per second
     */
    public Poisson(double lambdaStar) {
        this(lambdaStar, null);
    }

    /**
     * Full parametric constructor for this class
     *
     * @param lambdaStar the maximum rate of the Poisson distribution at any given time of arrivals per second
     * @param lambdaT object giving the value of lambda(t) for a given t of arrivals per second
     */
    public Poisson (double lambdaStar, LambdaT lambdaT) {
        this(lambdaStar, lambdaT, new Time(0));
    }

    /**
     * Full parametric constructor for this class
     *
     * @param lambdaStar the maximum rate of the Poisson distribution at any given time of arrivals per second
     * @param lambdaT object giving the value of lambda(t) for a given t of arrivals per second
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
     * Full parametric constructor for this class
     *
     * @param lambdaStar the maximum rate of the Poisson distribution at any given time of arrivals per second
     * @param lambdaT object giving the value of lambda(t) for a given t of arrivals per second
     * @param previousArrivalTime the last generated arrival time, by default holds value 0
     * @param seed the seed the random number generator should start at
     */
    public Poisson (double lambdaStar, LambdaT lambdaT, Time previousArrivalTime, long seed) {
        this(lambdaStar, lambdaT, previousArrivalTime);

        // Initialize the random number generator
        rnd = new Random(seed);
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
        return new Time(this.prevArrTime);
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
            //Retrieve the last generated arrival time
            double t = getPreviousArrivalTime().toSeconds();

            // Generate the new arrival time
            double u1, u2;
            do {
                u1 = this.rnd.nextDouble();
                u2 = this.rnd.nextDouble();

                t = t - (1 / this.lambdaStar) * Math.log(u1);
            } while (u2 > (this.lambdaGivenTime.getLambda(new Time(t)) / this.lambdaStar));

            // Test for debugging !!! DEBUG !!! DEBUGGING !!!
            //System.out.println(name);
            //System.out.println("Previous Arrival time:        " + getPreviousArrivalTime());
            //System.out.println("Newly generated arrival time: " + new Time(t));

            //Set the last arrival time to the newly generated one and return it
            this.setPreviousArrivalTime(new Time(t));
            return new Time(t);
        }
        // If lambdaGivenTime has not yet been set, we cannot generate a random variate
        else {
            throw new NullPointerException("Variable lambdaGivenTime has to be instantiated to non-null value before generating random Poisson variates.");
        }
    }
}