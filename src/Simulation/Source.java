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
	/** Eventlist that will be requested to construct events */
	private CEventList list;
	/** Queue that buffers products for the machine */
	private ProductAcceptor queue;
	/** Name of the source */
	private String name;
	/** Interarrival times (in case pre-specified) */
	private Time[] interarrivalTimes;
	/** Interarrival time iterator */
	private int interArrCnt;
	// boolean whether the interarrival times were pre-specified
	private boolean iaTimesPrespecified;
	// the last arrival time, not sure if it's still needed
	private Time previousArrivalTime;

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
		previousArrivalTime = new Time(0);
		iaTimesPrespecified = false;
		// put first event in list for initialization
		//list.add(this,0,drawRandomExponential(meanArrTime)); //target,type,time
		//list.add(this,0,drawNextPoisson(previousArrivalTime)); //target,type,time
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are prespecified
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param ia	interarrival times
	*/
	public Source(ProductAcceptor q,CEventList l,String n,Time[] ia)
	{
		list = l;
		queue = q;
		name = n;
		//meanArrTime=-1;
		interarrivalTimes=ia;
		interArrCnt=0;
		iaTimesPrespecified = true;
		// put first event in list for initialization
		list.add(this,0,interarrivalTimes[0]); //target,type,time
	}
	
        @Override
	public void execute(int type, Time tme)
	{
		// show arrival
		if (Simulation.DEBUG_PRINT)
			System.out.println("Arrival at time =           " + tme);
		// give arrived product to queue
		//Product p = new Product();
		//p.stamp(tme,"Creation",name);
		//queue.giveProduct(p);
		// generate duration
		if (! iaTimesPrespecified)
		{
			//Time nextTime = drawNextPoisson(previousArrivalTime);
			// Create a new event in the eventlist
			//list.add(this,0,nextTime); //target,type,time
		}
		else
		{
			interArrCnt++;
			if(interarrivalTimes.length>interArrCnt)
			{
				tme.add(interarrivalTimes[interArrCnt]);
				list.add(this,0,tme); //target,type,time
			}
			else
			{
				list.stop();
			}
		}
	}
}