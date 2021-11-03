/**
 * ->Author:Christian Alberto Gomez and Victor Herrera.
 * ->Date: 11/01/21
 * ->Course: CS-3331
 * ->Instructor: Daniel Mejia
 * ->Programming Assignment 4.
 *
 * ->Lab Description:
 *   -You have recently been hired to work for the TicketMiner, a company that sells tickets for sporting events,
 *   concerts,special events,etc. You have a few customers thar are interested in creating their events using your
 *   system.
 *
 * ->By means of this communication I swear that all the program/code written here
 *   came from me and was not copied or stolen from any other student or internet user.
 *   Also, by this communication I confirm that I did not request or use
 *   any type of prohibited assistance for this assignment.
 */
public class Ticket {
    //Attributes
    private int eventID; //Aqui podemos obtener el nombre,fecha y tiempo.
    private String seatType;
    private double seatPrice;
    private int totalTickets;
    private double totalAmount;

    //Constructors
    public Ticket(){

    }
    /**
     * Constructor for Ticket.
     * @param eventIDIn ID event that was purchased.
     * @param seatTypeIn Type of seat that was purchased.
     * @param totalTicketsIn Total of tickets that were purchased.
     * @param totalAmountIn Total amount of the reservation.
     */
    public Ticket(int eventIDIn,String seatTypeIn,int totalTicketsIn,double totalAmountIn){
        this.eventID = eventIDIn;
        this.seatType = seatTypeIn;
        this.totalTickets = totalTicketsIn;
        this.totalAmount = totalAmountIn;
    }
    //Getters and Setters

    /**
     * Method provided by Christian A. Gomez.
     * Getter for the event ID.
     * @return event ID.
     */
    public int getEventID() {
        return eventID;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Setter for the event ID.
     * @param eventID set event ID.
     */
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Getter for the type of seat.
     * @return type of seat.
     */
    public String getSeatType() {
        return seatType;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Setter for the type of seat.
     * @param seatType set type of seat.
     */
    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Getter for the price of seat.
     * @return seat price.
     */
    public double getSeatPrice() {
        return seatPrice;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Setter for the price of seat.
     * @param seatPrice set price of seat.
     */
    public void setSeatPrice(double seatPrice) {
        this.seatPrice = seatPrice;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Getter for the total of tickets.
     * @return total of tickets.
     */
    public int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Setter for the total of tickets.
     * @param totalTickets set the total of tickets.
     */
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Getter for the total amount to be pay.
     * @return total amount.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Setter for the total amount to be pay.
     * @param totalAmount set the total amount.
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    //methods
    /**
     * Method provided by Christian A. Gomez.
     * Method that generates a transaction number for the ticket.
     * @return random number.
     */
    public int generateTransaction(){
        return (int)(Math.random()*(1000000));
    }

    /**
     * Method provided by Christian A. Gomez.
     * Display all the information from the customer's purchased as a ticket.
     * @return customer's ticket information.
     */
    @Override
    public String toString() {
        return
                "\t------------------------------------------------------------------------------"+"\n"+
                        "\t PLEASE SHOW THIS TICKET TO ENJOY THE EVENT:"+"\n" +
                        "\tEVENT ID:"+eventID+"\tSeat:"+seatType+
                        "\tTotal Tickets:"+totalTickets+"\tTotal Amount:"+totalAmount+
                        "\tTransaction number:"+generateTransaction()+"\n"+
                        "\t-------------------------------------------------------------------------------";
    }
}
