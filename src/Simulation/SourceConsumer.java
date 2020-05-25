package Simulation;

public class SourceConsumer implements CProcess{
    /** Eventlist that will be requested to construct events */
    private CEventList list;
    /** Queue that buffers products for the machine */
    private ProductAcceptor queue;
    /** Name of the source */
    private String name;

    public SourceConsumer(ProductAcceptor q,CEventList l,String n) {
        list = l;
        queue = q;
        name = n;
        addFirstEvent();
    }

    /**
     *  Initializes the event list
     */
    private void addFirstEvent() {
        Time newEventTime = Consumer.getNewArrivalTime();


        // DEBUGGING !!! DEBUG !!!
        // System.out.println("Generated new first arrival at time t=" + newEventTime);



        list.add(this, 0, newEventTime);
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

        Consumer cust = new Consumer();
        cust.stamp(tme,"Creation",name);
        queue.giveProduct(cust);

        list.add(this, 0, Consumer.getNewArrivalTime());
    }
}
