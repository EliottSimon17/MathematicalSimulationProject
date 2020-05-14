package Simulation;

public abstract class CSA extends Machine {
    //status: b->busy, i->idle, n->not working    
    protected double costHour = 0;
    protected Time workingTime = new Time(0);         //Amount of time this CSA has been working
    protected int shift = 0;
    protected int customers = 0;
    protected Time totalServiceTime = new Time(0);    //Sum of service time for all the customers    
    
    public CSA(Queue q, ProductAcceptor s, CEventList e, String n, int shiftN) {
        super(q,s,e,n, new Time(0));
        shift = shiftN;
    }        
    
    protected void startProduction(Product p) {                               
        Time duration = ((Customer)p).getNewServiceTime();
        // Create a new event in the eventlist
        Time tme = eventlist.getTime();
        /* -------------------------------------------- TODO ----------------------------------------- */
        eventlist.add(this,0,tme.sum(duration)); //target,type,time
        // set status to busy
        status='b';
        
        totalServiceTime = duration;
        customers++;
    }
    
    @Override
    public boolean giveProduct(Product p)
    {
        //TODO override -> adapt to customers/corporate, depends on policy                
        if(status!='b')
        {            
            if(eventlist.getTime().greaterEq(getShift(shift)[0])) {   //Starting of the shift
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
        
    public int getShift() {
        return shift;
    }
        
    public double getCurrentCost() {        
        return (workingTime.getDays() * 24 + workingTime.getHours() + workingTime.getMinutes() / 60 + workingTime.getSeconds() / 3600) * costHour;
    }
        
    public double getCostPerHour() {
        return costHour;
    }
        
    public Time getWorkingTime() {
        return workingTime;
    }          
        
    public int getTotalCustomers() {
        return customers;
    } 
        
    public Time getMeanServiceTime() {
        //TODO use seconds with doubles
        return new Time((int)totalServiceTime.getSeconds() / customers);
    }    
    
    public Time[] getShift(int n) {
        int h1 = 6 + 8 * n;
        int h2 = 6 + 8 * (n + 1);                 
        return new Time[]{new Time(0, h1 % 24, 0, 0), new Time(0, h2 % 24, 0, 0)};
    }
}