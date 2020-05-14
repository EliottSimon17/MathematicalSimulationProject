package Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static java.lang.System.out;

/*
    This class displays how we could write out data to .txt files
        --> to be able to save simulation data from Java and load it into Matlab
 */

public class writeOnTxt {
    public static void writeOnTxt(ArrayList<Time> arrivalTime, ArrayList<String> customerType, ArrayList<Time> finishTime) throws FileNotFoundException {
        out.println("writing ");
        PrintWriter writer = new PrintWriter(new File("MS.csv"));

        StringBuilder sb = new StringBuilder();

        sb.append("customer type: ");
        sb.append("arrival times: ");
        sb.append("finishing times: ");
        sb.append("\n");

        try {
            for (int i = 0; i < arrivalTime.size(); i++) {
                sb.append(customerType.get(i));
                sb.append(", ");
                sb.append(arrivalTime.get(i));
                sb.append(", ");
                sb.append(finishTime.get(i));
                sb.append("\n");
            }
        } catch (IndexOutOfBoundsException e) {
            writer.write(sb.toString());
            writer.close();
        }

        //out.println(sb.toString());
        writer.write(sb.toString());
        writer.close();

    }
}
