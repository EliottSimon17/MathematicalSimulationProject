//Implement common perts for CSACorporate and CSAConsumer
package Simulation;

public class CSAConsumer extends Machine implements CSA{
    //status: b->busy, i->idle, n->not working
    //TODO use Time class
    protected double costHour = 0;
    protected double workingTime = 0;         //Amount of time this CSA has been working
    protected int shift = 0;
    protected int customers = 0;
    protected double totalServiceTime = 0;    //Sum of service time for all the customesr
    //protected Distribution tr;              //In the product object
    
    public CSAConsumer(Queue q, ProductAcceptor s, CEventList e, String n, int shiftN) {
        super(q,s,e,n, 0);
        shift = shiftN;
    }
    
    protected void startProduction(Product p) {                               
        double duration = p.getServiceTime();        
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
    
    public Time[] getShiftInterval(int n) {
        //TODO
        return new Time[]{};
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
    
    @Override
    public double getMeanServiceTime() {
        return totalServiceTime / customers;
    }
}
