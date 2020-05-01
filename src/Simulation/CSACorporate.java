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
public class CSACorporate extends Machine implements CSA{ 
    private final double costHour = 60;
    private double workingTime = 0;
    private boolean active = false; //is this CSA woking now?, might be redundant
    private int shift = 0;
    
    public CSACorporate(Queue q, ProductAcceptor s, CEventList e, String n, int shiftN) {
        //TODO set the parameter of the actual distribution
        super(q,s,e,n);
        shift = shiftN;
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
    
    //TODO is it the correct class? arrivals
    private static double drawNonstatPoisson(double[] rates, double[] times) {
        //8am-6pm: 1/minute
        //6pm-8am: 0.2/minute
        return 0;
    }
    
    private static double drawTrunNorm(double mean, double sd, double min) {
        //mean: 3.6 min
        //sd: 1.2 min
        //min: 45 s
        return 0;
    }
}
