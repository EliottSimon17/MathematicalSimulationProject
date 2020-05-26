package Simulation;

import java.util.ArrayList;
/**
 *	A sink
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Sink implements ProductAcceptor {
	/** All products are kept */
	private ArrayList<Product> products;
	/** All properties of products are kept */
	private ArrayList<Integer> numbers;
	private ArrayList<Time> times;
	private ArrayList<String> events;
	private ArrayList<String> stations;
	/** Counter to number products */
	private int number;
	/** Name of the sink */
	private String name;
	
	/**
	*	Constructor, creates objects
	*/
	public Sink(String n) {
		name = n;
		products = new ArrayList<>();
		numbers = new ArrayList<>();
		times = new ArrayList<>();
		events = new ArrayList<>();
		stations = new ArrayList<>();
		number = 0;
	}
	
        @Override
	public boolean giveProduct(Product p) {
		//System.out.println("nex product");
		number++;
		//System.out.println("\n		Finished processing product #" + number + "\n");
		// store stamps
		ArrayList<Time> t = p.getTimes();
		ArrayList<String> e = p.getEvents();
		ArrayList<String> s = p.getStations();
		//System.out.println("-----------------"+t.size()+"------------");
		for(int i=0;i<t.size();i++) {
			products.add(p);
			numbers.add(number);
			times.add(t.get(i));
			events.add(e.get(i));
			stations.add(s.get(i));
		}

		return true;
	}
	
	public int[] getNumbers() {
		numbers.trimToSize();
		int[] tmp = new int[numbers.size()];

		for (int i=0; i < numbers.size(); i++) {
			tmp[i] = numbers.get(i).intValue();
		}
		return tmp;
	}

	public Time[] getTimes() {
		times.trimToSize();
		Time[] tmp = new Time[times.size()];

		for (int i=0; i < times.size(); i++) {
			tmp[i] = times.get(i);
		}
		return tmp;
	}

	public String[] getEvents() {
		String[] tmp = new String[events.size()];
		tmp = events.toArray(tmp);
		return tmp;
	}

	public String[] getStations() {
		String[] tmp = new String[stations.size()];
		tmp = stations.toArray(tmp);
		return tmp;
	}

	public String[] getProduct() {
		ArrayList<String> name = new ArrayList<>();
		for (Product p : products) {
			if (p instanceof Consumer)
				name.add("consumer");
			else if (p instanceof Corporate)
				name.add("Corporate");
			else
				name.add("unknown");
		}

		String[] tmp = new String[name.size()];
		return name.toArray(tmp);
	}
}
