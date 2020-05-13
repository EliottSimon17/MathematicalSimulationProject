package Simulation;

public class CSACorporate extends Machine implements CSA{ 
    //TODO set time unit
    private final double costHour = 60;
    private double workingTime = 0;         //Amount of time this CSA has been working
    private int shift = 0;
    private int customers = 0;
    private double totalServiceTime = 0;    //Sum of service time for all the customers
    //status: b->busy, i->idle, n->not working
    
    public CSACorporate(Queue q, ProductAcceptor s, CEventList e, String n, int shiftN) {
        super(q,s,e,n, 3.6);
        shift = shiftN;
    }
    
    @Override
    public void execute(int type, double tme) {
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
        
        totalServiceTime = duration;
        customers++;
    }
    
    @Override
    public boolean giveProduct(Product p)
    {
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
}
