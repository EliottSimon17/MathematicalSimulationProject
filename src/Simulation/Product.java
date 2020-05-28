package Simulation;


import java.util.ArrayList;
/**
 *	Product that is sent trough the system
 *	@author Joel Karel
 *	@version %I%, %G%
 */
abstract class Product {
	/** Stamps for the products */
	private ArrayList<Time> times;			// the times of events
	private ArrayList<String> events;		// the string representing the events
	private ArrayList<String> stations;	// the station of the event (what has happened)
	
	/** 
	*	Constructor for the product
	*	Mark the time at which it is created
	*/
	public Product() {
		times = new ArrayList<>();
		events = new ArrayList<>();
		stations = new ArrayList<>();
	}

	public void stamp(Time time,String event,String station) {
		times.add(time);
		events.add(event);
		stations.add(station);
	}
	
	public ArrayList<Time> getTimes() {
		return times;
	}

	public ArrayList<String> getEvents() {
		return events;
	}

	public ArrayList<String> getStations()	{
		return stations;
	}
	
	public Time[] getTimesAsArray() {
		times.trimToSize();
		Time[] tmp = new Time[times.size()];
		for (int i=0; i < times.size(); i++)
		{
			tmp[i] = (Time)times.get(i);
		}

		return tmp;
	}

	public String[] getEventsAsArray() 	{
		String[] tmp = new String[events.size()];
		tmp = events.toArray(tmp);
		return tmp;
	}

	public String[] getStationsAsArray() {
		String[] tmp = new String[stations.size()];
		tmp = stations.toArray(tmp);
		return tmp;
	}
}