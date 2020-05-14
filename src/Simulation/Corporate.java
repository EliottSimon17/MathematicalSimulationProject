package Simulation;

import java.util.Random;


/* TODO CHANGE SIMILARLY TO CONSUMER.java */


public class Corporate extends Customer {
    private Poisson arrivalDistr = Poisson(param1, param2, ...);
    private TruncNormal serviceDistr = TruncNormal(param1, param2);

    public Corporate () {
        super();
        arrivalDistr.setPreviousArrivalTime(0);
    }

    public double getPoissonRandom(double time) {
        double ratePerMinutes = 0;
        double correctTime = time%24;
        Random r = new Random();

        if (correctTime > 8 && correctTime < 18) {
            ratePerMinutes = 1;

        }else{
            ratePerMinutes = 0.2;
        }

        return time + Math.log(r.nextDouble())/-ratePerMinutes;
    }

    public static double getNewArrivalTime(double currentTime) {
        // Give the current time
        arrivalDistr.setCurrentTime(currentTime);
        // Draw a random number
        arrivalDistr.drawRandom();
    }

    public static double getNewServiceTime() {
        // TODO
    }
}
