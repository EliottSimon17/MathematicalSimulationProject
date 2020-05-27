package Simulation;


public class CSACorporate extends CSA {
    //status: b->busy, i->idle, n->not working
    private Queue altQueue;

    // this variable will be the number of CSACorporate that should be free such that they start taking Consumer customers
    private static int limitForTakingConsumers;
    private static int freeCSACorporate = 0;

    public CSACorporate(Queue q1, Queue q2, ProductAcceptor s, CEventList e, String n, int shiftN, int limitForTakingConsumers) {
        super(q1,s,e,n, shiftN);                
        costHour = 60;
        altQueue = q2;
        this.limitForTakingConsumers = limitForTakingConsumers;
        // add one free csacorporate to the count
        freeCSACorporate ++;
    }

    @Override
    public void execute(int type, Time tme) {
        // show arrival
        System.out.println("Corporate Product finished at time = " + tme);

        // Remove product from system
        product.stamp(tme,"Production complete",name);
        sink.giveProduct(product);
        product=null;
        // set machine status to idle
        if (tme.inShift(getShift(shift))) {
            status='i';
            freeCSACorporate ++;
            // Ask the queue for products
            if(!queue.askProduct(this)) {
                altQueue.askProduct(this);
            }
        }else {
            status='n';
        }        
    }            
    
    @Override
    //The queue call this method to give a product to this machine
    //If giveProduct returns false the object is kept in the queue
    public boolean giveProduct(Product p)
    {
        if(status!='b')
        {
            if(eventlist.getTime().inShift(getShift(shift))) {
                //Starting of the shift, one more free csa corporate
                status = 'i';
                freeCSACorporate ++;
            }else{
                // End of the shift, one less free csacorporate
                freeCSACorporate --;
                status = 'n';
                return false;
            }

            // accept the product only if it is a Corporate or there are enough free csacorporate
            if ((p instanceof Corporate) || (freeCSACorporate >= limitForTakingConsumers)) {
                // We accept the product, so the number of freeCSACorporate is decreased by 1
                freeCSACorporate --;

                // Create the product
                product = p;
                // mark starting time
                product.stamp(eventlist.getTime(), "Production started", name);
                // start production
                startProduction(p);
                // Flag that the product has arrived
                return true;
            }
            else{
                // Flag that the product is rejected
                return false;
            }
        }
        // Flag that the product has been rejected
        else return false;
    }

    /** Check whether we start our shift now
     *
     */
    @Override
    public void checkShift (Time current) {
        if (current.inShift(getShift(shift))) {
            // Change the state to working
            status = 'i';
            // Add a idle CSA
            freeCSACorporate ++;

            // Ask the queue for products
            if (! queue.askProduct(this)) {
                altQueue.askProduct(this);
            }
        }
    }

    /**
     * Resets the number of free CSA Corporate to 0
     */
    public static void resetNumFreeCSACorporate () {
        freeCSACorporate = 0;
    }
}
