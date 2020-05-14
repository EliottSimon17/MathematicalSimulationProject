package Simulation;

public class CSAConsumer extends CSA{          
    public CSAConsumer(Queue q, ProductAcceptor s, CEventList e, String n, int shiftN) {
        super(q,s,e,n, shiftN);
        costHour = 35;
    }            
}
