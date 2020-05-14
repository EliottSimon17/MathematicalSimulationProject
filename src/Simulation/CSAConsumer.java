//Implement common perts for CSACorporate and CSAConsumer
package Simulation;


/* TODO:
    - change lines 30, 46, 79, 100 to work with Time objects
        --> additions, substractions, multiplications, divisions of Time objects
 */

public class CSAConsumer extends Machine implements CSA{
    //status: b->busy, i->idle, n->not working
    //TODO use Time class
    protected double costHour = 0;
    protected Time workingTime = new Time(0);         //Amount of time this CSA has been working
    protected int shift = 0;
    protected int customers = 0;
    protected Time totalServiceTime = new Time(0);    //Sum of service time for all the customers
    //protected Distribution tr;              //In the product object
    
    public CSAConsumer(Queue q, ProductAcceptor s, CEventList e, String n, int shiftN) {
        super(q,s,e,n, new Time(0));
        shift = shiftN;
    }
    
    protected void startProduction(Product p) {                               
        Time duration = p.getServiceTime();
        // Create a new event in the eventlist
        Time tme = eventlist.getTime();
        /* -------------------------------------------- TODO ----------------------------------------- */
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
            /* -------------------------------------------- TODO ----------------------------------------- */
            if(eventlist.getTime() >= new Time(0)) {   //Starting of the shift
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
        /* -------------------------------------------- TODO ----------------------------------------- */
        return workingTime * costHour;
    }
    
    @Override
    public double getCostPerHour() {
        return costHour;
    }
    
    @Override
    public Time getWorkingTime() {
        return workingTime;
    }          
    
    @Override
    public int getTotalCustomers() {
        return customers;
    } 
    
    @Override
    public Time getMeanServiceTime() {
        /* -------------------------------------------- TODO ----------------------------------------- */
        return totalServiceTime / customers;
    }
}
