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
        super(q,s,e,n, false);
        shift = shiftN;

        if (e.getTime().inShift(getShift(shift))) {
            q.askProduct(this);
        }
        else {
            status = 'n';
        }
    }
    
    protected void startProduction(Product p) {
        //System.out.println("Starting production of product " + p);

        Time duration;

        if (p instanceof Consumer) {
            duration = Consumer.getNewServiceTime();
        } else {
            duration = Corporate.getNewServiceTime();
        }

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
        if(status!='b')
        {
            if(eventlist.getTime().inShift(getShift(shift))) {  // starting of the shift
                if (status == 'n') {
                    status = 'i';
                }
            }else{
                if (status == 'i') {
                    status = 'n';
                }

                return false;
            }
            
            // accept the product
            product=(Customer)p;
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

    /** Check whether we start our shift now
     *
     */
    public void checkShift (Time current) {
        if (current.inShift(getShift(shift))) {
            if (status == 'n') {
                // Change the state to working
                status = 'i';

                // Ask the queue for products
                queue.askProduct(this);
            }
        } else {
            if (status == 'i') {
                status = 'n';
            }
        }
    }

    /**
     *	Method to have this object execute an event
     *	@param type	The type of the event that has to be executed
     *	@param tme	The current time
     */
    public void execute(int type, Time tme)
    {
        // show arrival
        System.out.println("Consumer Product finished at time =  " + tme);
        // Remove product from system
        product.stamp(tme,"Production complete",name);
        sink.giveProduct(product);
        product=null;

        // Make sure we are still in the shift
        if (tme.inShift(getShift(shift))) {
            // set machine status to idle
            status='i';
            // Ask the queue for products
            queue.askProduct(this);
        }
        else {
            status = 'n';
        }
    }
        
    public int getShift() {
        return shift;
    }

    /** Returns the current shift as Time array for n=1, 2, 3
     *
     * @param n the code of the shift
     * @return an array with {start, end} of the shifts
     */
    public Time[] getShift(int n) {
        int h1 = 6 + 8 * n;
        int h2 = 6 + 8 * (n + 1);
        return new Time[]{new Time(0, 0, h1 % 24, 0), new Time(0, 0, h2 % 24, 0)};
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
}