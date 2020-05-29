package Simulation;

public class CSAConsumer extends CSA{          
    public CSAConsumer(Queue q, ProductAcceptor s, CEventList e, String n, int shiftN) {
        super(q,s,e,n, shiftN);
        costHour = 35;
    }

    @Override
    public boolean giveProduct(Product p)
    {        
        if(status!='b')
        {
            if(eventlist.getTime().getTimeOfTheDay().inShift(getShift(shift))) {  // starting of the shift
                if (status == 'n') {
                    status = 'i';
                }
            }else{
                if (status == 'i') {
                    status = 'n';
                }
                return false;
            }

            // accept the product only if the product is a Consumer
            /*if (p instanceof Consumer) {
                product = p;
                // mark starting time
                product.stamp(eventlist.getTime(), "Production started", name);

                // start production
                startProduction(p);
                // Flag that the product has arrived
                return true;
            }
            else {
                return false;
            }*/
            product=(Consumer)p;
            // mark starting time
            product.stamp(eventlist.getTime(),"Production started",name);
            // start production
            startProduction(p);
            // Flag that the product has arrived
            return true;
        }
        // Flag that the product has been rejected
        else{            
            return false;
        }
    }
}
