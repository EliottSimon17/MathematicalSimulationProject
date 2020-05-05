/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulation;
import java.util.Random;

/**
 *
 * @author gianluca
 */
public class CSACorporate extends Machine implements CSA{ 
    private final double costHour = 60;
    private double workingTime = 0;    
    private boolean active = false; //is this CSA woking now?, might be redundant
    private int shift = 0;
    private int customers = 0;
    private TruncNormal tr;
    
    public CSACorporate(Queue q, ProductAcceptor s, CEventList e, String n, int shiftN) {
        //TODO set the parameter of the actual distribution
        super(q,s,e,n);
        shift = shiftN;
        tr = new TruncNormal(1000);  //TODO set parameters, in minutes/seconds/hours?
    }
    
    public void execute(int type, double tme) {
        //TODO override -> check time shift, update counts, ...
        super.execute(type, tme);
    }
    
    private void startProduction() {
        //TODO override -> use TruncNorm instead of exponential
    }
    
    
    public boolean getActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public int getShift() {
        return shift;
    }
    
    public double getCurrentCost() {
        //TODO
        return workingTime * costHour;
    }
    
    public double getCostPerHour() {
        return costHour;
    }
    
    public double getWorkingTime() {
        return workingTime;
    }          
    
    public int getTotalCustomers() {
        return customers;
    }
}
