package Simulation;

public class SourceCorporate implements CProcess {
    /** Eventlist that will be requested to construct events */
    private CEventList list;
    /** Queue that buffers products for the machine */
    private ProductAcceptor queue;
    /** Name of the source */
    private String name;
    /** Interarrival time iterator */
    private int interArrCnt;
    // boolean whether the interarrival times were pre-specified
    private boolean iaTimesPrespecified;

    public SourceCorporate(ProductAcceptor q,CEventList l,String n) {
        list = l;
        queue = q;
        name = n;
        iaTimesPrespecified = false;
        addFirstEvent();
    }

    /**
     *  Initializes the event list
     */
    private void addFirstEvent() {
        // TODO : Find OPTIMAL LAMBDA
        double lambda = 0;
        Poisson ps = new Poisson(lambda);
        list.add(this, 0, ps.drawRandom());
    }

    /**
     * @return name of the source
     */
    public String getName(){
        return name;
    }

    /**
     * @return event list of the source
     */
    public CEventList getList(){
        return list;
    }

    /**
     * @return the queue of the list
     */
    public ProductAcceptor getQueue(){
        return queue;
    }

    /** This method generates events
     * @param type	The type of the event that has to be executed
     * @param tme	The current time
     */
    @Override
    public void execute(int type, Time tme) {
        System.out.println("Arrival at time = " + tme);

    }
}
