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
    // the last arrival time, not sure if it's still needed
    private Time previousArrivalTime;

    private Corporate corporate;

    public SourceCorporate(ProductAcceptor q,CEventList l,String n) {
        list = l;
        queue = q;
        name = n;
        iaTimesPrespecified = false;
        previousArrivalTime = new Time(0);
        corporate = new Corporate();
        addFirstEvent();
    }

    /**
     *  Initializes the event list
     */
    private void addFirstEvent() {
        // TODO : Make sure that this has to be initialized this way
        list.add(this, 0, corporate.getNewArrivalTime());
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

        //Feeds the product to the queue
        Corporate cust = new Corporate();
        cust.stamp(tme,"Creation",name);
        queue.giveProduct(cust);

        list.add(this, 0, corporate.getNewArrivalTime());
    }
}
