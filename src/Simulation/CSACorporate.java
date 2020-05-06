package Simulation;

public class CSACorporate extends Machine implements CSA{ 
    private final double costHour = 60;
    private double workingTime = 0;    
    private boolean active = true; //is this CSA woking now?, might be redundant
    private int shift = 0;
    private int customers = 0;
    private TruncNormal tr;
    
    public CSACorporate(Queue q, ProductAcceptor s, CEventList e, String n, int shiftN, double mean, double sd, double min, double max) {
        //TODO set the parameter of the actual distribution
        super(q,s,e,n, 3.6);
        shift = shiftN;
        tr = new TruncNormal(mean, sd, min, Double.NaN, 100);
    }
    
    @Override
    public void execute(int type, double tme) {
        //TODO override -> check time shift, update counts, ...
        //should we check the time shift here or from outside?
        super.execute(type, tme);
    }
    
    @Override
    protected void startProduction() {                               
        double duration = tr.drawTruncNorm();        
        // Create a new event in the eventlist
        double tme = eventlist.getTime();
        eventlist.add(this,0,tme+duration); //target,type,time
        // set status to busy
        status='b';

        customers++;        
    }
    
    @Override
    public boolean giveProduct(Product p)
    {
        //TODO override -> adapt to customers/corporate, depends on policy
        // Only accept something if the machine is idle
        if(status=='i' && getActive())
        {
                // accept the product
                product=p;
                // mark starting time
                product.stamp(eventlist.getTime(),"Production started",name);
                // start production
                startProduction();
                // Flag that the product has arrived
                return true;
        }
        // Flag that the product has been rejected
        else return false;
    }
    
    
    public boolean getActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
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
}
