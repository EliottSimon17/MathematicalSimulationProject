package Simulation;

import java.util.ArrayList;

/**
 *	Event processing mechanism
 *	Events are created here and it is ensured that they are processed in the proper order
 *	The simulation clock is located here.
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class CEventList implements CProcess {
    /** The time in the simulation */
    protected Time currentTime;
    /** List of events that have to be executed */
    protected final ArrayList<CEvent> events;
    /** Stop flag */
    protected boolean stopFlag;

    /**
    *	Standard constructor
    *	Create an CEventList object
    */
    public CEventList() {
        currentTime = new Time(0);
        stopFlag = false;
        events = new ArrayList<>();
    }
	
    /**
    *	Method for the construction of a new event.
    *	@param target The object that will process the event
    *	@param type A type indicator of the event for objects that can process multiple types of events.
    *	@param tme The time at which the event will be executed
    */
    public void add(CProcess target, int type, Time tme) {
        boolean success=false;

        // First create a new event using the parameters
        CEvent evnt;
        evnt = new CEvent(target,type,tme);

        // Now it is examined where the event has to be inserted in the list
        for(int i=0;i<events.size();i++) {
            // The events are sorted chronologically			
            if(events.get(i).getExecutionTime().greater(evnt.getExecutionTime())) {
                // If an event is found in the list that has to be executed after the current event
                success=true;
                // Then the new event is inserted before that element
                events.add(i,evnt);
                break;
            }
        }
        if(!success) {
            // Else the new event is appended to the list
            events.add(evnt);
        }
    }
	
    /**
    *	Method for starting the eventlist.
    *	It will run until there are no longer events in the list
    */
    public void start() {
        // stop criterion
        while((events.size()>0)&&(!stopFlag)) {
            // Make the similation time equal to the execution time of the first event in the list that has to be processed
            currentTime=events.get(0).getExecutionTime();
            // Let the element be processed
            events.get(0).execute();
            // Remove the event from the list
            events.remove(0);
        }
    }

    /**
    *	Method for starting the eventlist.
    *	It will run until there are no longer events in the list or that a maximum time has elapsed
    *	@param mx The maximum time of the simulation
    */
    public void start(Time mx) {
        // Add the termination event at the ending time
        add(this,-1,mx);

        // stop criterion
        while((events.size()>0)&&(!stopFlag)) {
            // Make the similation time equal to the execution time of the first event in the list that has to be processed
            currentTime=events.get(0).getExecutionTime();
            // Let the element be processed
            events.get(0).execute();
            // Remove the event from the list
            events.remove(0);
        }
    }

    /**
     * End the simulation
     */
    public void stop()
    {
        stopFlag = true;
    }

    /**
    *	Method for accessing the simulation time.
    *	The variable with the time is private to ensure that no other object can alter it.
    *	This method makes it possible to read the time.
    *	@return The current time in the simulation
    */
    public Time getTime() {
        return currentTime;
    }

    /**
    *	Method to have this object process an event
    *	@param type	The type of the event that has to be executed
    *	@param tme	The current time
    */
    @Override
    public void execute(int type, Time tme) {
        if(type==-1)
            stop();
    }

}