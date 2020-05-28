package Simulation;

public class CEventListWithShift extends CEventList {
    // The time-points where there are shift changes
    private Time[] shiftTransitions = {new Time(0, 0, 6), new Time(0, 0, 14), new Time(0, 0, 22)};

    /**
     *	Method for starting the eventlist.
     *	It will run until there are no longer events in the list or that a maximum time has elapsed
     *	@param mx The maximum time of the simulation
     * @param sim The simulation that has to be started
     */
    public void start(Time mx, Simulation sim) {
        // Add the termination event at the ending time
        add(this,-1,mx);

        // Keep track of which shift we're in
        int currentShiftEnd = 0;

        // stop criterion
        while((events.size()>0)&&(!stopFlag)) {
            // Make the similation time equal to the execution time of the first event in the list that has to be processed
            currentTime=events.get(0).getExecutionTime();

            // If we are currently in the next shift,
            if (currentTime.inShift(shiftTransitions[currentShiftEnd], shiftTransitions[(currentShiftEnd+1)%shiftTransitions.length])) {
                // We have a shift transition,
                // Update the currentShift's ending time tracker
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
