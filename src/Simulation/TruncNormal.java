//It might be too heavy, in case we could move the drawTruncNorm to some other class
package Simulation;

import java.util.Random;
/**
 * 
 * Generate random numbers from a truncated normal distribution by using the acceptance-rejection method
 * If the truncation boundaries are not set it is a normal distribution
 */
public class TruncNormal implements Distribution{
        private double mean = 0;
        private double sd = 1;
        private Random rnd;                 //Random generator for U(0,1) and N(0,1)
        private double min = Double.NaN;    //Nan or infinite mean not set -> no truncation
        private double max = Double.NaN;
        
        
        /**
         * Standard normal N(0,1)
         */
        public TruncNormal() {            
            this(0, 1);
        }
        
        /**
         * Standard normal N(0,1) from a given seed
         */
        public TruncNormal(long seed) {
            this(0, 1, seed);
        }
        
        /**
         * Normal N(mean, sd^2)
         */
        public TruncNormal(double mean, double sd) {
            this(mean, sd, Float.NaN, Float.NaN);
        }
        
        /**
         * Normal N(mean, sd^2) from a given seed
         */
        public TruncNormal(double mean, double sd, long seed) {
            this(mean, sd, Float.NaN, Float.NaN, seed);
        }
        
        /**
         * Normal N(mean, sd^2) truncated on the left at min and on the right at max
         * If min or max are NaN or infinite the distribution is not truncated on that side
         */
        public TruncNormal(double mean, double sd, double min, double max) {            
            //setIntegralSteps(n)            
            this.rnd = new Random();
            this.mean = mean;
            this.sd = sd;
            setLeft(min);
            setRight(max);
        }
        
        /**
         * Normal N(mean, sd^2) truncated on the left at min and on the right at max from a given seed
         * If min or max are NaN or infinite the distribution is not truncated on that side
         */
        public TruncNormal(double mean, double sd, double min, double max, long seed) {
            this(mean, sd, min, max);
            rnd.setSeed(seed);
        }
                        
        /**
         * 
         * @return Random number from this distribution
         */
        public double drawRandom() {
            //acceptance-rejection method
                //truncated normal PDF and scaled normal PDF
            
            //truncNorm = Norm * 1/(Fb-Fa)  
            //-> Norm * 1/(Fb-Fa) >= truncNorm
            //-> t = NormPDF * 1/(Fb-Fa)
            //-> scale = Fb-Fa, r = NormPDF
            // return U <= truncNom / (Norm *1/(Fa-Fb) * scale) = U <= trunc / Norm
            
            double y = 0;
            do {
                y = rnd.nextGaussian() * sd + mean;  // random number from r          
            } while(!isInDomain(y));    //check whether U <= f*(Y)/t(Y) -> only when f* is non-zero
            return y;
        }
        
        
        public boolean isInDomain(double x) {
            //check whether x is a<= x <= b
            return !((Double.isFinite(this.min) && (x < min)) || (Double.isFinite(max) && (x > max)));            
        }
        
        public void setLeft(double min) {                     
            this.min = min;
        }
        
        public void setRight(double max) {                     
            this.min = max;
        }
        
        public double getLeftTrunc() { return min; }
        public double getRightTrunc() { return max; }
        
        //TESTING
        public static void main(String[] args) {
            System.out.println("Testing TruncNormal");
            System.out.println("--Constructor");
            TruncNormal tr = new TruncNormal(3.6, 1.2, 0.75, Double.NaN, 2020);
            System.out.println("--Draw 1000 random numbers");
            for(int i = 0; i < 1000; i++)
                System.out.println(tr.drawRandom());
        }
        
        //NOT USED
        /*
        //private double Fa = 0;
        //private double Fb = 1;
        //private double n = 100;
        private double erf(double x) {
            // compute integral[0, x] e^{-t^2/2} dt with Simpson's rule
            double sign = Math.signum(x);
            x = Math.abs(x);
            double h = x / n;   //size of each interval
            double val = 1;
            
            for(int i = 1; i <= n/2; i++) {
                val += 2*Math.exp(-(Math.pow(x + 2*i*h, 2)) / 2);
                val += 4*Math.exp(-(Math.pow(x + 2*(i-1)*h, 2)) / 2);
            }
            val -= Math.exp(-(x*x)/2);  //Counted twice because of the for-loop
            return val * h/3 * sign;    //negative x, negative output
        }
        public void setIntegralSteps(int n) throws IllegalArgumentException{
            if(n % 2 == 1) {
                throw new IllegalArgumentException("n must be even, but it is " + n);
            }
            this.n = n;
        }
        public void setLeft(double min) {
            //compute F(a) = integral[-inf,a] f(x) dx, left truncate
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
        
        public double truncNormPdf(double x) {
            //if((Double.isFinite(this.min) && (x < min)) || (Double.isFinite(max) && (x > max))) //x is in the truncated part
            if(!isInDomain(x))
                return 0;
            return (1 / (sd*Math.sqrt(2*Math.PI)) * Math.exp(-Math.pow((x-mean)/sd,2) / 2)) / (Fb - Fa);
        }
        */
    }