package Simulation;

import java.util.Random;

public class Corporate extends Product {

    public double getPoissonRandom(double time) {
        double ratePerMinutes = 0;
        double correctTime = time%24;

        if (correctTime > 8 && correctTime < 18) {
            ratePerMinutes = 1;

        }else{
            ratePerMinutes = 0.2;
        }

        Random r = new Random();

        //Based on Donald Knuth The art of programming 3.4.1
        return Math.log(1.0 - r.nextDouble())/-ratePerMinutes; // Need a minus sign, else we get a negative number

    }
}
