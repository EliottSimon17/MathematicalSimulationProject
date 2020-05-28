/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulation {
    public CEventListWithShift l;
    public Queue q1;
    public Queue q2;
    public SourceCorporate s1;
    public SourceConsumer s2;
    public Sink si;
    public static ArrayList<Sink> sinks;
    public CSA[] ms;

    public static final double costPerCSAConsumerPerHour = 35;
    public static final double costPerCSACorporatePerHour = 60;
    
    /**
     * 
     * @param corporates: number of corporate machine per shift
     * @param consumers : number of consumers machine per shift
     */
    public Simulation(int[] corporates, int[] consumers, int corporateCSALimit) {
        // First, make sure to reset all static parameters
        CSACorporate.resetNumFreeCSACorporate();
        Corporate.resetPreviousArrivalTime();
        Consumer.resetPreviousArrivalTime();
        // Note: did not yet reset the service times object, because they should not need to be reset

        // Create a new EventList with shift, two queues (one for corporate and one for consumers
        l = new CEventListWithShift();
        q1= new Queue();    //Corporate
        q2= new Queue();    //Consumer

        // Create two sources, one for Corporates and one for Consumers
        s1 = new SourceCorporate(q1, l, "Source corporate");
        s2 = new SourceConsumer(q2, l, "Source consumer");

        // Compute the total number of CSA we will have, and then make an array for these
        int total = corporates[0] + corporates[1] + corporates[2];
        total += consumers[0] + consumers[1] + consumers[2];
        ms = new CSA[total];

        // Create a sink and add it to the list of sinks
        si = new Sink("Sink 1");
        sinks.add(si);

        // Create all CSA's for the 3 shifts
        total = 0;
        for(int i = 0; i < 3; i++) {
            // For each shift, create as many corporate and consumer CSAs as specified, keeping track of which index we are at in ms
            for(int j = 0; j < corporates[i]; j++) {
                ms[total + j] = new CSACorporate(q1, q2, si, l, "CSA Corporate " + i + " " + j , i, corporateCSALimit);
            }
            total += corporates[i];
            for(int j = 0; j < consumers[i]; j++) {
                ms[total + j] = new CSAConsumer(q2, si, l, "CSA consumer " + i + " " + j , i);
            }
            total += consumers[i];
        }
    }
    
    public void start(Time t) {
        l.start(t, this);
    }

    /** This method is supposed to go through the CSA's and make sure that the CSA's that start their shift now start working
     *
     */
    public void callCSAtoWork (Time current) {
        for (int i = 0; i < ms.length; i ++) {
            ms[i].checkShift(current);
        }
    }

    /** Computes the cost of running a simulation with these number of CSAConsumers and CSACorporate for the given time
     *
     * @param CSAConsumerPerShift array containing the number of Consumer CSA for each shift
     * @param CSACorporatePerShift array containing the number of Corporate CSA for each shift
     * @param runTime the time the simulation will run for
     * @return the total cost of this strategy during the given time
     */
    public static double computeCostOfSimulation (int[] CSAConsumerPerShift, int[] CSACorporatePerShift, Time runTime) {
        int totalConsumers = 0;
        for (int i = 0; i < CSAConsumerPerShift.length; i ++) {
            totalConsumers += CSAConsumerPerShift[i];
        }

        int totalCorporate = 0;
        for (int i = 0; i < CSACorporatePerShift.length; i ++) {
            totalCorporate += CSACorporatePerShift[i];
        }

        // Note: This only works for runTime's of only an integer number of days, nothing about hours, seconds or minutes ...
        int days = runTime.getDays();
        // Notice that since we take full days, every CSA will only work one shift
        double cost = (totalConsumers * costPerCSAConsumerPerHour + totalCorporate * costPerCSACorporatePerHour) * 8 * days;
        return cost;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Specify the strategy for the runs
        // First index is for shift 6:00-14:00, second for 14:00-22:00 and third for 22:00-06:00
        int[] consumerCSAPerShift = {10, 5, 10};
        int[] corporateCSAPerShift = {10, 10, 10};
        // The number of CSA Corporate that should be free before taking Consumers
        int CSACorporateLimitForTakingConsumers = 0;

        // Initialize the number of runs and the time they will take
        int runs = 5;
        Time t = new Time(0, 0, 0, 7);

        // Initialize the simulation and Sink array
        Simulation[] sims = new Simulation[runs];
        sinks = new ArrayList<>();

        // Make each separate simulation
        for(int i = 0; i < runs; i++) {

            // Create a new simulation object with the given parameters
            sims[i] = new Simulation(corporateCSAPerShift, consumerCSAPerShift, CSACorporateLimitForTakingConsumers);
            // And start the simulation
            sims[i].start(t);

            // DEBUGGING At the end, prinr the number of customers that each CSA has taken on.
            for (int j = 0; j < sims[i].ms.length; j ++) {
                if (sims[i].ms[j] instanceof CSAConsumer)
                    System.out.println("Consumer CSA took on  " + sims[i].ms[j].customers + " customers");
                else
                    System.out.println("Corporate CSA took on " + sims[i].ms[j].customers + " customers");
            }

            // DEBUGGING Print the overall number of corporate and consumers
            System.out.println("\n\nThere were overall " + sims[i].s1.numberCorporate + " Corporate and " + sims[i].s2.numberConsumer + " Consumers. \nNote that some might still be waiting to be processed.\n");
        }

        // DEBUGGING Print the number of simulations
        System.out.println("Number of simulations: " + sinks.size() + "\n");

        // Print the cost of the simulation
        System.out.println("The cost of the strategy for running during " + t + "\n is " + Simulation.computeCostOfSimulation(consumerCSAPerShift, corporateCSAPerShift, t) + "\n\n");

        // And write the result
        writeOnTxt.writeOnTxt(sinks);
    }
}
