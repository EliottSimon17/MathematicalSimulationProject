package Simulation;

public class SourceCorporate implements CProcess {
    /** Eventlist that will be requested to construct events */
    private CEventList list;
    /** Queue that buffers products for the machine */
    private ProductAcceptor queue;
    /** Name of the source */
    private String name;

    public int numberCorporate = 1;

    public SourceCorporate(ProductAcceptor q,CEventList l,String n) {
        list = l;
        queue = q;
        name = n;
        addFirstEvent();
    }

    /**
     *  Initializes the event list
     */
    private void addFirstEvent() {
        list.add(this, 0, Corporate.getNewArrivalTime());
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
        if (Simulation.DEBUG_PRINT)
            System.out.println("Corporate Arrival at time = " + tme);

        //Feeds the product to the queue
        Corporate cust = new Corporate();
        cust.stamp(tme,"Creation",name);
        queue.giveProduct(cust);

        list.add(this, 0, Corporate.getNewArrivalTime());

        numberCorporate ++;
    }
}
