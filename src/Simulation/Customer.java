package Simulation;


/**
 * A Customer is a type of Product, that arrives, is at some point processed by a CSA (=Machine)
 * And, at some point, once it has been processed, it exits the system
 */
public class Customer extends Product {
    public static double getNewServiceTime() {
        // This method should be overwritten in the subclasses
        throw new UnsupportedOperationException("This method should have been overwritten in the subclass.");
    }

    public static double getNewArrivalTime() {
        // This method should be overwritten in the subclass(es)
        throw new UnsupportedOperationException("This method should have been overwritten in the subclass.");
    }
}