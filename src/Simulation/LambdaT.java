package Simulation;

/**
 * This class should be used to generate the lambda rates of non-stationary Poisson Processes, given a certain time
 * NOTE: One could instantiate the objects by using so-called Lambda expressions
 *      --> for explanation, see https://www.tutorialspoint.com/java8/java8_lambda_expressions.htm
 *
 * Basically, to create an object, you would do for example:
 * LambdaT variableName = (time) -> {
 *     if (time > 8)
 *      return 3.5
 *     else
 *      return 2
 * }
 */
public interface LambdaT {
    // This method should be implemented such as to return the rate lambda given the current time as Time object
    public double getLambda(double time);
}
