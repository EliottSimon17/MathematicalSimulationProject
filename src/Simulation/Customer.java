package Simulation;


/**
 * A Customer is a type of Product, that arrives, is at some point processed by a CSA (=Machine)
 * And, at some point, once it has been processed, it exits the system (and ends in the Sink)
 */
public class Customer extends Product {
    // the method for generating a new service time
    public static Time getNewServiceTime() {
        // This method should be overwritten in the subclasses
        throw new UnsupportedOperationException("This method should have been overwritten in the subclass.");
    }

    // the method for generating a new arrival time
    public static Time getNewArrivalTime() {
        // This method should be overwritten in the subclass(es)
        throw new UnsupportedOperationException("This method should have been overwritten in the subclass.");
    }

    // No, this couldn't be an interface, because static methods can not be overwritten
    //  --> the interface's abstract methods could not be overwritten
}