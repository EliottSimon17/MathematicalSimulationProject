package Simulation;

/* TODO:
    - change line 30 to work with Time objects
        --> comparison of Time objects
 */

public class CSACorporate extends CSAConsumer {    
    //status: b->busy, i->idle, n->not working
    private Queue altQueue;

    public CSACorporate(Queue q1, Queue q2, ProductAcceptor s, CEventList e, String n, int shiftN) {        
        super(q1,s,e,n, shiftN);                
        costHour = 60;
        altQueue = q2;
    }
    
    @Override
    public void execute(int type, Time tme) {
        //TODO check time shift        
                
        // show arrival
        System.out.println("Product finished at time = " + tme);
        // Remove product from system
        product.stamp(tme,"Production complete",name);
        sink.giveProduct(product);
        product=null;
        // set machine status to idle
        /* -------------------------------------------- TODO ----------------------------------------- */
        if(tme > 0 && tme < 0) {    //TODO check time shift
            status='i';
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
        return super.giveProduct(p) && true; //TODO product type
        //      --> shouldn't super.giveProduct(p) and True come down to super.giveProduct(p) ?
    }
}
