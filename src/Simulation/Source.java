package Simulation;

import java.util.Random;

/**
 *	A source of products
 *	This class implements CProcess so that it can execute events.
 *	By continuously creating new events, the source keeps busy.
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Source implements CProcess
{
        //TODO use 2 queues or 2 sources
	/** Eventlist that will be requested to construct events */
	private CEventList list;
	/** Queue that buffers products for the machine */
	private ProductAcceptor queue;
	/** Name of the source */
	private String name;
	/** Mean interarrival time */
	//private double meanArrTime;
	/** Interarrival times (in case pre-specified) */
	private double[] interarrivalTimes;
	/** Interarrival time iterator */
	private int interArrCnt;
	// The time of the previous arrival, used in the random poisson generator
	private double previousArrivalTime;
	// boolean whether the interarrival times were pre-specified
	private boolean iaTimesPrespecified;
	// the random number generator from Java
	private Random rnd;
	// the maximum rate of the process
	private double lambdaStar;

	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with mean 33
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*/
	public Source(ProductAcceptor q,CEventList l,String n)
	{
		list = l;
		queue = q;
		name = n;
		//meanArrTime=33;
		previousArrivalTime = 0;
		iaTimesPrespecified = false;
		rnd = new Random();
		lambdaStar = 10;									// NOT SURE WHAT VALUE WOULD BE GOOD AS DEFAULT
		// put first event in list for initialization
		//list.add(this,0,drawRandomExponential(meanArrTime)); //target,type,time
		list.add(this,0,drawNextPoisson(previousArrivalTime)); //target,type,time
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with specified mean
	*	@param q			The receiver of the products
	*	@param l			The eventlist that is requested to construct events
	*	@param n			Name of object
	*	@param lambdaStar	the maximum rate of arrivals
	*/
	public Source(ProductAcceptor q,CEventList l,String n,double lambdaStar)
	{
		list = l;
		queue = q;
		name = n;
		//meanArrTime=m;
		previousArrivalTime = 0;
		iaTimesPrespecified = false;
		rnd = new Random();
		this.lambdaStar = lambdaStar;
		// put first event in list for initialization
		//list.add(this,0,drawRandomExponential(meanArrTime)); //target,type,time
		list.add(this,0,drawNextPoisson(previousArrivalTime)); //target,type,time
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are prespecified
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param ia	interarrival times
	*/
	public Source(ProductAcceptor q,CEventList l,String n,double[] ia)
	{
		list = l;
		queue = q;
		name = n;
		//meanArrTime=-1;
		interarrivalTimes=ia;
		interArrCnt=0;
		iaTimesPrespecified = true;
		rnd = new Random();
		lambdaStar = 10;									// NOT SURE WHAT VALUE WOULD BE GOOD AS DEFAULT
		// put first event in list for initialization
		list.add(this,0,interarrivalTimes[0]); //target,type,time
	}
	
        @Override
	public void execute(int type, double tme)
	{
		// show arrival
		System.out.println("Arrival at time = " + tme);
		// give arrived product to queue
		Product p = new Product();
		p.stamp(tme,"Creation",name);
		queue.giveProduct(p);
		// generate duration
		if (! iaTimesPrespecified)
		{
			double nextTime = drawNextPoisson(previousArrivalTime);
			// Create a new event in the eventlist
			list.add(this,0,nextTime); //target,type,time
		}
		else
		{
			interArrCnt++;
			if(interarrivalTimes.length>interArrCnt)
			{
				list.add(this,0,tme+interarrivalTimes[interArrCnt]); //target,type,time
			}
			else
			{
				list.stop();
			}
		}
	}

	/* UNUSED, REPLACED BY drawNextPoisson()
	public static double drawRandomExponential(double mean)
	{
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with mean 33
		double res = -mean*Math.log(u);
		return res;
	}
	*/

	/** This method uses the thinning algorithm to generate the next arrival time
	 *
	 * @param prevArrTime the previous arrival time in seconds
	 * @return a non-stationary poisson distributed random arrival time
	 */
	public double drawNextPoisson(double prevArrTime) {
		double t = prevArrTime;

		//Based on the thinning process from slide 13, first part of Lecture 7
		double u1, u2;
		do {
			u1 = rnd.nextDouble();
			u2 = rnd.nextDouble();

			t = t - (1 / lambdaStar) * Math.log(u1);
		} while (u2 <= (getLambda(t)/lambdaStar));

		return t;
	}

	/** Returns the value of lambda at the given moment
	 *
	 * @param currentTime the time in seconds
	 * @return the rate of arrivals at that time
	 */
	public double getLambda (double currentTime) {
		// TODO somehow link this to the customer to get the rate,
		// as the rate changes depending on time and customer type
	}
}