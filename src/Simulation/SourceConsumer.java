package Simulation;

public class SourceConsumer implements CProcess{
    /** Eventlist that will be requested to construct events */
    private CEventList list;
    /** Queue that buffers products for the machine */
    private ProductAcceptor queue;
    /** Name of the source */
    private String name;

    public int numberConsumer = 0;

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
        list.add(this, 0, Consumer.getNewArrivalTime());
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
        if (Simulation.DEBUG_PRINT)
            System.out.println("Consumer Arrival at time = " + tme);
        //Feed the product to the queue

        Consumer cust = new Consumer();
        cust.stamp(tme,"Creation",name);
        queue.giveProduct(cust);

        list.add(this, 0, Consumer.getNewArrivalTime());
        numberConsumer ++;
    }
}
