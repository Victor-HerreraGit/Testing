/**
 * ->Author:Christian Alberto Gomez and Victor Herrera.
 * ->Date: 11/01/21
 * ->Course: CS-3331
 * ->Instructor: Daniel Mejia
 * ->Programming Assignment 4.
 *
 * ->Lab Description:
 *   -You have recently been hired to work for the TicketMiner, a company that sells tickets for sporting events,
 *   concerts,special events,etc. You have a few customers' thar are interested in creating their events using your
 *   system.
 *
 * ->By means of this communication I swear that all the program/code written here
 *   came from me and was not copied or stolen from any other student or internet user.
 *   Also, by this communication I confirm that I did not request or use
 *   any type of prohibited assistance for this assignment.
 */
public interface CustomerInterface {
    /**
     * Method provided by Christian A. Gomez.
     * Method provided by the customer interface. This method will collect all the discounts from each customer.
     * @param discount total discount from each event purchase.
     */
    public void collectTotalDiscounts(double discount);
}
