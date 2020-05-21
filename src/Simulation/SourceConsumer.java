package Simulation;

public class SourceConsumer implements CProcess{
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
    // These variables are the distributions used for generating arrival times and service times
    private Consumer consumer;
    // the last arrival time, not sure if it's still needed
    private Time previousArrivalTime;

    public SourceConsumer(ProductAcceptor q,CEventList l,String n) {
        list = l;
        queue = q;
        name = n;
        previousArrivalTime = new Time(0);
        iaTimesPrespecified = false;
        consumer = new Consumer();
        addFirstEvent();
    }

    /**
     *  Initializes the event list
     */
    private void addFirstEvent() {
        list.add(this, 0, consumer.getNewArrivalTime());
    }

    public String getName(){
        return name;
    }
    public CEventList getList(){
        return list;
    }
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
        //Feed the product to the queue

        Customer cust = new Customer();
        cust.stamp(tme,"Creation",name);
        queue.giveProduct(cust);

        list.add(this, 0, consumer.getNewArrivalTime(previousArrivalTime));
    }
}
