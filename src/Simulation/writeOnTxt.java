package Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static java.lang.System.out;

public class writeOnTxt {

    public static void writeOnTxt(ArrayList<Sink> sinks) throws FileNotFoundException {
        generateConsumerFile(sinks);
        generateCorporateFile(sinks);

    }

    public static void generateConsumerFile(ArrayList<Sink> sinks) throws FileNotFoundException {
        out.println("writing corporate");
        PrintWriter writer = new PrintWriter(new File("MSConsumer.txt"));

        StringBuilder sb = new StringBuilder();

        int counter = 1;
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
            out.println("numbers length: " +numbers.length);
            out.println("customer length: "+ customer.length);
            out.println("events length: "+ events.length);
            out.println("Station length: "+ stations.length);
            out.println("times length: "+times.length);
            out.println();

            try {
                for (int i= 0; i < numbers.length; i++) {
                    if (customer[i].equals("consumer")) {
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
                }
                sb.append("\n");
            } catch (IndexOutOfBoundsException e) {
                writer.write(sb.toString());
                writer.close();
            }
        }
        sb.append("simulation end");

        writer.write(sb.toString());
        writer.close();

    }

    public static void generateCorporateFile(ArrayList<Sink> sinks) throws FileNotFoundException {
        out.println("writing consumer");
        PrintWriter writer = new PrintWriter(new File("MSCorporate.txt"));

        StringBuilder sb = new StringBuilder();

        int counter = 1;
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
            out.println("numbers length: " +numbers.length);
            out.println("customer length: "+ customer.length);
            out.println("events length: "+ events.length);
            out.println("Station length: "+ stations.length);
            out.println("times length: "+times.length);
            out.println();

            try {
                for (int i= 0; i < numbers.length; i++) {
                    if (customer[i].equals("Corporate")) {
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
                }
                sb.append("\n");

            } catch (IndexOutOfBoundsException e) {
                writer.write(sb.toString());
                writer.close();
            }
        }
        sb.append("simulation end");

        writer.write(sb.toString());
        writer.close();

    }
}
