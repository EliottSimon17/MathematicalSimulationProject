package Simulation;

public abstract class CSA extends Machine {
    //status: b->busy, i->idle, n->not working    
    protected double costHour = 0;
    protected Time workingTime = new Time(0);         //Amount of time this CSA has been working
    protected int shift = 0;
    // An int that encodes the shift of this csa:
    //      - 0 = 6  am (= 6h)  to 2 pm (=14h)
    //      - 1 = 2  pm (=14h) to 10 pm (=22h)
    //      - 2 = 10 pm (=22h) to 6 am  (= 6h)
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
        duration.add(tme);
        eventlist.add(this,0,duration); //target,type,time
        // set status to busy
        status='b';
        
        totalServiceTime.updateTime(duration);
        customers++;
    }
    
    @Override
    public boolean giveProduct(Product p)
    {
        //TODO override -> adapt to customers/corporate, depends on policy                
        if(status!='b')
        {            
            if(eventlist.getTime().inShift(getShift(shift))) {  // starting of the shift
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

    /*      IS THIS STILL NEEDED ???
    public Time[] getShiftInterval(int n) {
        //TODO
        return new Time[]{new Time(0,0)};
    }
    */
        
    public int getShift() {
        return shift;
    }
        
    public double getCurrentCost() {
        return (workingTime.getDays() * 24 + workingTime.getHours() + workingTime.getMinutes() / 60.0 + workingTime.getSeconds() / 3600) * costHour;
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
        return new Time(totalServiceTime.getSeconds() / customers);
    }    
    
    public Time[] getShift(int n) {
        int h1 = 6 + 8 * n;
        int h2 = 6 + 8 * (n + 1);
        return new Time[]{new Time(0, h1 % 24, 0, 0), new Time(0, h2 % 24, 0, 0)};
    }
}