/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Simulation {

    public CEventList l;
    public Queue q1;
    public Queue q2;
    public SourceCorporate s1;
    public SourceConsumer s2;
    public Sink si;
    public static ArrayList<Sink> sinks;
    public CSA[] ms;
    
    /**
     * 
     * @param corporates: number of corporate machine per shift
     * @param consumers : number of consumers machine per shift
     */
    public Simulation(int[] corporates, int[] consumers, int corporateCSALimit) {
        System.out.println("New simulation");
        Consumer.getArrivDistr().setPreviousArrivalTime(new Time(0));

        l = new CEventList();
        q1= new Queue();
        q2= new Queue();

        s1 = new SourceCorporate(q1, l, "Source corporate");
        s2 = new SourceConsumer(q2, l, "Source consumer");
        
        int total = corporates[0] + corporates[1] + corporates[2];
        total += consumers[0] + consumers[1] + consumers[2];

        ms = new CSA[total];

        si = new Sink("Sink 1");
        sinks.add(si);

        total = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < corporates[i]; j++) {
                ms[total + j] = new CSACorporate(q1, q2, si, l, "CSA Corporate " + i + " " + j , i, corporateCSALimit);
            }
            total += corporates[i];
            for(int j = 0; j < consumers[i]; j++) {
                ms[total + j] = new CSAConsumer(q1, si, l, "CSA consumer " + i + " " + j , i);
            }
            total += consumers[i];
        }
    }
    
    public void start(Time t) {
        l.start(t);
    }
    
    // Recipe for a successful simulation:
    //
    // 1 single CEventList
    // 2 different queues, one for Consumers and one for Corporate
    // 2 different sources, one for Consumers (SourceConsumer) and one for Corporate (SourceCorporate)
    // 1 single Sink
    // as many machines as we want, depending on the number of CSA per shift

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        /*// Create an eventlist
        CEventList l = new CEventList();
        // A queue for the machine
        Queue q = new Queue();
        // A source
        Source s = new Source(q, l, "Source 1");
        // A sink
        Sink si = new Sink("Sink 1");
        // A machine
        Machine m = new Machine(q, si, l, "Machine 1");
        // start the eventlist
        l.start(new Time(2000)); // 2000 is maximum time*/

        // Specify the strategy for the runs
        int[] consumerCSAPerShift = {5, 10, 5};
        int[] corporateCSAPerShift = {10, 10, 10};
        int CSACorporateLimitForTakingConsumers = 0;

        sinks = new ArrayList<>();
        int runs = 10;
        Simulation[] sims = new Simulation[runs];
        Time t = new Time(0, 0, 0, 7);
        for(int i = 0; i < runs; i++) {
           sims[i] = new Simulation(corporateCSAPerShift, consumerCSAPerShift, CSACorporateLimitForTakingConsumers);
           sims[i].start(t);

           System.out.println("\nEnd of simulation\n\n");
        }
        System.out.println(sinks.size());
        writeOnTxt.writeOnTxt(sinks);
    }
}
