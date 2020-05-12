package Simulation;

import java.util.Random;

public class Corporate extends Product {
    private boolean being_served = false;

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

    public boolean getServed() {
        return being_served;
    }
}
