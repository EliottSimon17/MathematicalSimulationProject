package Simulation;

import java.util.ArrayList;

/**
 *	Queue that stores products until they can be handled on a machine machine
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Queue implements ProductAcceptor
{
	/** List in which the products are kept */
	private ArrayList<Product> row;
	/** Requests from machine that will be handling the products */
	private ArrayList<Machine> requests;
	
	/**
	*	Initializes the queue and introduces a dummy machine
	*	the machine has to be specified later
	*/
	public Queue() 	{
		row = new ArrayList<>();
		requests = new ArrayList<>();
	}
	
	/**
	*	Asks a queue to give a product to a machine
	*	True is returned if a product could be delivered; false if the request is queued
	*/
	public boolean askProduct(Machine machine) {
		// This is only possible with a non-empty queue
		if(row.size()>0) {
			// If the machine accepts the product
			if(machine.giveProduct(row.get(0))) {
				row.remove(0);// Remove it from the queue
				return true;
			}
			else
				return false; // Machine rejected; don't queue request
		}
		else {
			requests.add(machine);
			return false; // queue request
		}
	}
	
	/**
	*	Offer a product to the queue
	*	It is investigated whether a machine wants the product, otherwise it is stored
	*/
	@Override
	public boolean giveProduct(Product p) {
		//System.out.println("Gave a product");


		// Check if the machine accepts it
		if (requests.size() < 1) {
			//System.out.println("Was put to waiting");
			row.add(p); // Otherwise store it
		}
		else {
			//System.out.println("Free machines ! Yay !");
			boolean delivered = false;
			int index = 0;
			while(!delivered & (index < requests.size()))
			{
				delivered=requests.get(index).giveProduct(p);
				// remove the request regardless of whether or not the product has been accepted
				//requests.remove(0);
				index += 1;
			}
			if(!delivered) {
				//System.out.println("Didn't find an accepting machine");
				row.add(p); // Otherwise store it
			}
			else {
				//System.out.println("Found an accepting machine");
			}
		}
		return true;
	}
}