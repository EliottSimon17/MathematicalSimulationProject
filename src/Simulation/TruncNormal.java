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
public class TruncNormal {
        private double Fa = 0;
        private double Fb = 1;
        private double mean = 0;
        private double sd = 1;
        private double n;
        private Random rnd;
        private double min = Double.NaN;    //Nan or infinite mean not set -> no truncation
        private double max = Double.NaN;
        
        public TruncNormal(double n) throws IllegalArgumentException{
            this(0, 1, n);
        }
        
        public TruncNormal(double n, long seed) throws IllegalArgumentException {
            this(0, 1, n, seed);
        }
        
        public TruncNormal(double mean, double sd, double n)  throws IllegalArgumentException {
            this(mean, sd, Float.NaN, Float.NaN, n);
        }
        
        public TruncNormal(double mean, double sd, double n, long seed)  throws IllegalArgumentException {
            this(mean, sd, Float.NaN, Float.NaN, n, seed);
        }
        
        public TruncNormal(double mean, double sd, double min, double max, double n)  throws IllegalArgumentException {
            if(n % 2 == 1) {
                throw new IllegalArgumentException("n must be even, but it is " + n);
            }
            this.n = n;
            this.rnd = new Random();
            this.mean = mean;
            this.sd = sd;
            setLeft(min);
            setRight(max);
        }
        
        public TruncNormal(double mean, double sd, double min, double max, double n, long seed)  throws IllegalArgumentException {
            this(mean, sd, min, max, n);
            rnd.setSeed(seed);
        }
        
        public double truncNormPdf(double x) {
            //if((Double.isFinite(this.min) && (x < min)) || (Double.isFinite(max) && (x > max))) //x is in the truncated part
            if(!isInDomain(x))
                return 0;
            return (1 / (sd*Math.sqrt(2*Math.PI)) * Math.exp(-Math.pow((x-mean)/sd,2) / 2)) / (Fb - Fa);
        }
        
        public double drawTruncNorm() {
            //mean: 3.6 min
            //sd: 1.2 min
            //min: 45 s
            //acceptance-rejection method
                //truncated normal PDF and scaled normal PDF
            
            //truncNorm = Norm * 1/(Fb-Fa)  
            //-> Norm * 1/(Fb-Fa) >= truncNorm
            //-> t = Norm * 1/(Fb-Fa)
            //-> scale = Fb-Fa, r = Norm
            // return U <= truncNom / (Norm *1/(Fa-Fb) * scale) = U <= trunc / Norm
            
            double y = 0;
            do {
                y = rnd.nextGaussian() * sd + mean;
            //} while(rnd.nextDouble() <= truncNormPdf(y) / (Math.exp(-Math.pow((y - mean) / sd, 2) / 2)/(2*Math.sqrt(Math.PI))));
            } while(!isInDomain(y));            
            return y;
        }
        
        private double erf(double x) {
            // compute int[0, x] e^{-t^2/2} dt with Simpson's rule            
            double sign = Math.signum(x);
            x = Math.abs(x);
            double h = x / n;   //size of each interval
            double val = Math.exp(0);
            
            for(int i = 1; i <= n/2; i++) {
                val += 2*Math.exp(-(Math.pow(x + 2*i*h, 2)) / 2);
                val += 4*Math.exp(-(Math.pow(x + 2*(i-1)*h, 2)) / 2);
            }
            val -= Math.exp(-(x*x)/2);  //Counted twice because of the for-loop
            return val * h/3 * sign;    //negative x, negative output
        }
        
        public void setLeft(double min) {
            //compute F(a) = int[-inf,a] f(x) dx, left truncate
            //normal CDF evaluated at a
            this.min = min;
            if(!Double.isFinite(min))
                Fa = 0;
            else
                Fa = 0.5 * (1 + erf((min - mean)/(sd*Math.sqrt(2))));
        }
        
        public void setRight(double max) {
            //compute F(b), right truncate
            this.max = max;
            if(!Double.isFinite(max))
                Fb = 1;
            else
                Fb = 0.5 * (1 + erf((max - mean)/(sd*Math.sqrt(2))));
        }
        
        public boolean isInDomain(double x) {
            //check whether x is a<= x <= b
            return !((Double.isFinite(this.min) && (x < min)) || (Double.isFinite(max) && (x > max)));            
        }
        
        public double getLeftTrunc() { return min; }
        public double getRightTrunc() { return max; }
        
        //TESTING
        public static void main(String[] args) {
            System.out.println("Testing TruncNormal");
            System.out.println("--Constructor");
            TruncNormal tr = new TruncNormal(3.6, 1.2, 0.75, Double.NaN, 100, 2020);
            System.out.println("--Draw 1000 random numbers");
            for(int i = 0; i < 1000; i++)
                System.out.println(tr.drawTruncNorm());
        }
    }