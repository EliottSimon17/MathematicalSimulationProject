package Simulation;

public class CEventListWithShift extends CEventList {
    private Time[] shiftTransitions = {new Time(0, 0, 6), new Time(0, 0, 14), new Time(0, 0, 22)};

    /**
     *	Method for starting the eventlist.
     *	It will run until there are no longer events in the list or that a maximum time has elapsed
     *	@param mx The maximum time of the simulation
     * @param sim The simulation that has to be started
     */
    public void start(Time mx, Simulation sim)
    {
        add(this,-1,mx);
        // stop criterion
        int currentShiftEnd = 0;

        while((events.size()>0)&&(!stopFlag))
        {
            // Make the similation time equal to the execution time of the first event in the list that has to be processed
            currentTime=events.get(0).getExecutionTime();

            if (currentTime.inShift(shiftTransitions[currentShiftEnd], shiftTransitions[(currentShiftEnd+1)%shiftTransitions.length])) {
                // We have a shift transition
                currentShiftEnd = (currentShiftEnd + 1) % shiftTransitions.length;

                // Call the method to call all CSA's that are now starting their shift to work
                sim.callCSAtoWork(currentTime);
            }

            // Let the element be processed
            events.get(0).execute();
            // Remove the event from the list
            events.remove(0);
        }
    }
}
