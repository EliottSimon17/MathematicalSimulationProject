/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

public class Simulation {

    public CEventList list;
    public Queue queue;
    public Source source;
    public Sink sink;
    public Machine mach;

    // Recipe for a successful simulation:
    //
    // 1 single CEventList
    // 2 different queues, one for Consumers and one for Corporate
    // 2 different sources, one for Consumers (SourceConsumer) and one for Corporate (SourceCorporate)
    // 1 single Sink
    // as many machines as we want, depending on the number of CSA per shift

    // this variables specifies how many CSACorporate should be free before they help Consumers
    public final double CSACorporateLimit = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create an eventlist
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
        l.start(new Time(2000)); // 2000 is maximum time
    }
}
