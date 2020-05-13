package Simulation;


/* TODO MODIFY PRODUCT TO MAKE IT IMPLEMENT (OR HAVE ABSTRACT CLASSES)
 * FOR EVERYTHING THAT IS NEEDED IN THE CUSTOMER CLASSES
 * --> see Consumer.java
 *
 * ALSO: CHANGE IT TO USE TIME OBJECTS INSTEAD OF DOUBLE TIMES
 */

import java.util.ArrayList;
/**
 *	Product that is send trough the system
 *	@author Joel Karel
 *	@version %I%, %G%
 */
abstract class Product {
	/** Stamps for the products */
	private ArrayList<Double> times;
	private ArrayList<String> events;
	private ArrayList<String> stations;
	
	/** 
	*	Constructor for the product
	*	Mark the time at which it is created
	*	@param create The current time
	*/
	public Product() {
		times = new ArrayList<>();
		events = new ArrayList<>();
		stations = new ArrayList<>();
	}
	
	public void stamp(double time,String event,String station) {
		times.add(time);
		events.add(event);
		stations.add(station);
	}
	
	public ArrayList<Double> getTimes()
	{
		return times;
	}

	public ArrayList<String> getEvents()
	{
		return events;
	}

	public ArrayList<String> getStations()
	{
		return stations;
	}
	
	public double[] getTimesAsArray()
	{
		times.trimToSize();
		double[] tmp = new double[times.size()];
		for (int i=0; i < times.size(); i++)
		{
			tmp[i] = (times.get(i)).doubleValue();
		}
		return tmp;
	}

	public String[] getEventsAsArray()
	{
		String[] tmp = new String[events.size()];
		tmp = events.toArray(tmp);
		return tmp;
	}

	public String[] getStationsAsArray()
	{
		String[] tmp = new String[stations.size()];
		tmp = stations.toArray(tmp);
		return tmp;
	}

	public static double getNewServiceTime() {
		// This method should be overwritten in the subclasses
		throw new UnsupportedOperationException("This method should have been overwritten in the subclass.");
	}

	public static double getNewArrivalTime() {
		// This method should be overwritten in the subclass(es)
		throw new UnsupportedOperationException("This method should have been overwritten in the subclass.");
	}
}