/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;

/**
 *
 * @author gianluca
 */
public interface CSA {
    double getCurrentCost();    //time * cost/hour
    double getCostPerHour();    //cost * hour
    double getWorkingTime();    //time spent working    -> ex. for shift 2pm-10pm after one day is 10h
    int getTotalCustomers();    //customers processed
    int getShift();             //in which shift it is active -> ex from 2pm to 10pm
}
