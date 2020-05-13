package Simulation;

public class CSACorporate extends CSAConsumer {    
    //status: b->busy, i->idle, n->not working
    private Queue altQueue;
    
<<<<<<< HEAD
    public CSACorporate(Queue q, ProductAcceptor s, CEventList e, String n, int shiftN) {
        super(q,s,e,n, 3.6);
        shift = shiftN;
=======
    public CSACorporate(Queue q1, Queue q2, ProductAcceptor s, CEventList e, String n, int shiftN) {        
        super(q1,s,e,n, shiftN);                
        costHour = 60;
        altQueue = q2;
>>>>>>> a7663bea093ea52cc81f7bc896094fccb0537ec0
    }
    
    @Override
    public void execute(int type, double tme) {
<<<<<<< HEAD
        //TODO override -> check time shift, update counts, ...        
        super.execute(type, tme);
        //if tme is outside the shift -> status = 'n'
    }

    protected void startProduction(Product p) {
        double duration = p.getNewServiceTime();
        // Create a new event in the eventlist
        double tme = eventlist.getTime();
        eventlist.add(this,0,tme+duration); //target,type,time
        // set status to busy
        status='b';
=======
        //TODO check time shift        
                
        // show arrival
        System.out.println("Product finished at time = " + tme);
        // Remove product from system
        product.stamp(tme,"Production complete",name);
        sink.giveProduct(product);
        product=null;
        // set machine status to idle
>>>>>>> a7663bea093ea52cc81f7bc896094fccb0537ec0
        
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
<<<<<<< HEAD
        //TODO override -> adapt to customers/corporate, depends on policy        
        // Only accept something if the machine is not busy
        if(status!='b')
        {
            //TODO
            if(eventlist.getTime() >= 0) {   //Starting of the shift
                status = 'i';
            }else{
                status = 'n';
                return false;
            }
            
            // accept the product
            product=p;
            // mark starting time
            product.stamp(eventlist.getTime(),"Production started",name);
            // start production
            startProduction(p);
            // Flag that the product has arrived
            return true;
        }
        // Flag that the product has been rejected
        else return false;
    }
    
    
    /*public boolean getActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }*/
    
    @Override
    public int getShift() {
        return shift;
    }
    
    @Override
    public double getCurrentCost() {        
        return workingTime * costHour;
    }
    
    @Override
    public double getCostPerHour() {
        return costHour;
    }
    
    @Override
    public double getWorkingTime() {
        return workingTime;
    }          
    
    @Override
    public int getTotalCustomers() {
        return customers;
    } 
    
    @Override
    public double getMeanServiceTime() {
        return totalServiceTime / customers;
    }
=======
        return super.giveProduct(p) && true; //TODO product type                    
    }     
>>>>>>> a7663bea093ea52cc81f7bc896094fccb0537ec0
}
