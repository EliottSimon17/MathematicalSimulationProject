package Simulation;

/**
 *	Machine in a factory
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Machine implements CProcess,ProductAcceptor
{
	/** Product that is being handled  */
	protected Product product;
	/** Eventlist that will manage events */
	protected final CEventList eventlist;
	/** Queue from which the machine has to take products */
	protected Queue queue;
	/** Sink to dump products */
	protected ProductAcceptor sink;
	/** Status of the machine (b=busy, i=idle) */
	protected char status;
	/** Machine name */
	protected final String name;
	/** Mean processing time */
	protected Time meanProcTime;
	/** Processing times (in case pre-specified) */
	private Time[] processingTimes;
	/** Processing time iterator */
	private int procCnt;
	

	/**
	*	Constructor
	*        Service times are exponentially distributed with mean 30
	*	@param q	Queue from which the machine has to take products
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*   @param giveQueue whether we should already ask the queue for a product
	*/
	public Machine(Queue q, ProductAcceptor s, CEventList e, String n, boolean giveQueue)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		meanProcTime=new Time(-1);

		// Only give the queue the product if we want to
		if (giveQueue) {
			queue.askProduct(this);
		}
	}

	/**
	*	Constructor
	*        Service times are pre-specified
	*	@param q	Queue from which the machine has to take products
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*        @param st	service times
	*   @param giveQueue whether we should already ask the queue for a product
	*/
	public Machine(Queue q, ProductAcceptor s, CEventList e, String n, Time[] st, boolean giveQueue)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		meanProcTime=new Time(-1);
		processingTimes=st;
		procCnt=0;

		if (giveQueue)
			queue.askProduct(this);
	}

	/**
	*	Method to have this object execute an event
	*	@param type	The type of the event that has to be executed
	*	@param tme	The current time
	*/
	public void execute(int type, Time tme)
	{
		// show arrival
		if (Simulation.DEBUG_PRINT)
			System.out.println("Consumer Product finished at time =  " + tme);
		// Remove product from system
		product.stamp(tme,"Production complete",name);
		sink.giveProduct(product);
		product=null;

		// set machine status to idle
		status='i';
		// Ask the queue for products
		queue.askProduct(this);
	}
	
	/**
	*	Let the machine accept a product and let it start handling it
	*	@param p	The product that is offered
	*	@return	true if the product is accepted and started, false in all other cases
	*/
        @Override
	public boolean giveProduct(Product p)
	{
		// Only accept something if the machine is idle
		if(status=='i')
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
	
	/**
	*	Starting routine for the production
	*	Start the handling of the current product with an exponentionally distributed processingtime with average 30
	*	This time is placed in the eventlist
	*/
	protected void startProduction()
	{
            // generate duration
            if(meanProcTime.greaterNoDay(new Time(0)))
            {
                double duration = drawRandomExponential(meanProcTime.getSeconds());
                // Create a new event in the eventlist
                Time tme = eventlist.getTime();    
                eventlist.add(this,0, new Time(tme.getSeconds() + duration)); //target,type,time
                // set status to busy
                status='b';
            }
            else
            {
                if(processingTimes.length>procCnt)
                {
                    eventlist.add(this,0,new Time(eventlist.getTime().getSeconds() + processingTimes[procCnt].getSeconds())); //target,type,time
                    // set status to busy
                    status='b';
                    procCnt++;
                }
                else
                {
                    eventlist.stop();
                }
            }
	}

	/* WILL NOT WORK WITH THE TIME OBJECT ANYMORE, WOULD NEED TO BE MODIFIED.
		HOWEVER, WE SHOULD NOT NEED THIS METHOD ANYMORE
	 */
	public static double drawRandomExponential(double mean)
	{
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with mean 33
		double res = -mean*Math.log(u);
		return res;
	}
}