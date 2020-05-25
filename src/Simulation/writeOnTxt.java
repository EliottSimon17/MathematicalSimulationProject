package Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static java.lang.System.out;

public class WriteOnTxt {

    public static void writeOnTxt(ArrayList<Sink> sinks) throws FileNotFoundException {
        out.println(sinks.get(0).getEvents().length);

        out.println("writing ");
        PrintWriter writer = new PrintWriter(new File("MS.txt"));

        StringBuilder sb = new StringBuilder();

        // TODO make sure the times are in a fixed unit (seconds seems good ?)

        // TODO add the time when the Customer starts being processed by a Machine
        // TODO add which CSA type the Customer was processed by

        int counter = 0;
        for (Sink s : sinks) {
            sb.append("simulation: ").append(counter++).append("\n");

            sb.append("Number: ");
            sb.append("customer type: ");
            sb.append("event: ");
            sb.append("station: ");
            sb.append("time: ");
            sb.append("\n");

            int[] numbers = s.getNumbers();
            String[] customer = s.getProduct();
            String[] events = s.getEvents();
            String[] stations = s.getStations();
            Time[] times = s.getTimes();


            try {
                for (int i = 0; i < times.length; i++) {
                    sb.append(numbers[i]);
                    sb.append(", ");
                    sb.append(customer[i]);
                    sb.append(", ");
                    sb.append(events[i]);
                    sb.append(", ");
                    sb.append(stations[i]);
                    sb.append(", ");
                    sb.append(times[i].toSeconds());
                    sb.append("\n");
                }
            } catch (IndexOutOfBoundsException e) {
                writer.write(sb.toString());
                writer.close();
            }
        }

        //out.println(sb.toString());
        writer.write(sb.toString());
        writer.close();

    }
}
