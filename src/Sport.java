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
public class Sport extends Event{
    //Constructors

    /**
     * Empty Constructor.
     */
    public Sport(){

    }
    /**
     * Constructor of Special.
     * @param eventIDIn Event ID.
     * @param eventTypeIn Event Type.
     * @param nameIn Name of the event.
     * @param dateIn Date of the event.
     * @param timeIn Time of the event.
     * @param vipIn Vip price.
     * @param goldIn Gold price.
     * @param silverIn Silver price.
     * @param bronzeIn Bronze price.
     * @param generalIn General price.
     * @param stadiumIn Venue
     */
    public Sport(int eventIDIn,String eventTypeIn,String nameIn,String dateIn,String timeIn,double vipIn,double goldIn,
                 double silverIn,double bronzeIn,double generalIn,Stadium stadiumIn){
        super(eventIDIn,eventTypeIn,nameIn,dateIn,timeIn,vipIn,goldIn,silverIn,bronzeIn,generalIn,stadiumIn);
    }
    /**
     * Constructor of Special.
     * @param eventIDIn Event ID.
     * @param eventTypeIn Event Type.
     * @param nameIn Name of the event.
     * @param dateIn Date of the event.
     * @param timeIn Time of the event.
     * @param vipIn Vip price.
     * @param goldIn Gold price.
     * @param silverIn Silver price.
     * @param bronzeIn Bronze price.
     * @param generalIn General price.
     * @param arenaIn Venue
     */
    public Sport(int eventIDIn,String eventTypeIn,String nameIn,String dateIn,String timeIn,double vipIn,double goldIn,
                 double silverIn,double bronzeIn,double generalIn,Arena arenaIn){
        super(eventIDIn,eventTypeIn,nameIn,dateIn,timeIn,vipIn,goldIn,silverIn,bronzeIn,generalIn,arenaIn);
    }
    //    public Event(int eventID, String eventType, String name, String date, String time, double vipPrice, double goldPrice, double silverPrice, double bronzePrice, double generalPrice,Stadium stadiumIn) {


}

