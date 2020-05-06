package Simulation;

public interface CSA {
    double getCurrentCost();    //time * cost/hour
    double getCostPerHour();    //cost * hour
    double getWorkingTime();    //time spent working    -> ex. for shift 2pm-10pm after one day is 10h
    int getTotalCustomers();    //customers processed
    int getShift();             //in which shift it is active -> ex from 2pm to 10pm
}
