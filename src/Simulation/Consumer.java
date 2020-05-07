package Simulation;

public class Consumer extends Product implements Customer {
    private boolean being_served = false;       // whether the customer is currently being served

    public Consumer() {
        super();
    }

    public boolean getServed() {
        return being_served;
    }
}
