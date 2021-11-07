

/**
 * ->Author:Christian Alberto Gomez and Victor Herrera.
 * ->Date: 11/01/21
 * ->Course: CS-3331
 * ->Instructor: Daniel Mejia
 * ->Programming Assignment 4.
 *
 * ->Lab Description:
 *   -You have recently been hired to work for the TicketMiner, a company that sells tickets for sporting events,
 *   concerts,special events,etc. You have a few customer's thar are interested in creating their events using your
 *   system.
 *
 * ->By means of this communication I swear that all the program/code written here
 *   came from me and was not copied or stolen from any other student or internet user.
 *   Also, by this communication I confirm that I did not request or use
 *   any type of prohibited assistance for this assignment.
 */
        import java.io.*;
        import java.text.DateFormat;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.*;

public class RunTicket {
    public static void main(String[] args) {

        ArrayList<Event> eventList = generateEventList(); //ArrayList of Events
        printEvents(eventList);

        //AutoPurchase works. Just display its content using a for loop and you will see.
        ArrayList<AutoPurchase> autoPurchase = generateCustomerPurchase(); //ArrayList of AutoPurchase csv file info.

        //EN ESTA PARTE ESTAREMOS IMPLEMENTANDO EL INSTANCE DE DEL ITERATOR DESIGN PATTERN
        CustomerRepository customerRepository = generateCustomerRepository();
        startMenu();

    }
    /**
     * Method Provided by Victor Herrera
     * @param input user input
     * Method will only verify if user input exceeds integer value.
     * Calls will be made to this function to verify input quickly
     * **/
    public static void checkIncorrectInput(int input){
        if(input>3){
            System.out.println("You have entered a menu option that does not exist"+"\n"+"Try again!!!!!!!!!");
            startMenu();
        }

    }
    /**
     * Method provided by Christian A. Gomez.
     * Modifications made by Victor Herrera
     * This Method will serve as the Main Menu.
     * /-------------notes---------------------/
     * After code Review original code invalid input would cause to exit the program
     * To fix this problem the function was made to handle this situation.
     * **/
    public static void startMenu(){
        ArrayList<Event> eventList = generateEventList();
        ArrayList<AutoPurchase> autoPurchase = generateCustomerPurchase();
        CustomerRepository customerRepository = generateCustomerRepository();
        Scanner scr = new Scanner(System.in);
        try {
            int doItAgain;

            do {
                //Por el momento yo estare implementando la opcion de ADMINISTRADOR
                System.out.println("Good day! Please enter (1) for Administrator. (2) for Customer. (3) AutoPurchase:");
                int user = scr.nextInt();
                checkIncorrectInput(user);
                checkUserIdentity(user, eventList, customerRepository, autoPurchase);

                System.out.println("Enter: (1)Do it again (2) EXIT");
                doItAgain = scr.nextInt();

            } while (doItAgain == 1);

        }catch(InputMismatchException e){
            System.out.println("Expecting a numeric value invalid input was detected try again................");
            startMenu();
        }
        try {
            generateCsvCustomer(customerRepository);
            generateCsvEvent(eventList);
        } catch (IOException e) {
           System.out.println("The file was not found please ensure file exist");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //AQUI ESTAREMOS CALCULANDO EL TOTAL AMOUNT QUE SE DEBE PAGAR

    /**
     * Method provided by Christian A. Gomez.
     * This method will calculate the total amount that the user has to pay to get his tickets.
     * @param id Event ID.
     * @param ticket Type of ticket such as VIP,Gold,Silver, etc.
     * @param numTickets Number of tickets that will be purchased.
     * @param events An ArrayList of Event.
     * @param customerRepository An instance to the Customers.
     * @param customerPosition Customer position in customerRepository.
     * @return total amount to pay.
     */
    public static double totalAmount(int id,String ticket,int numTickets,ArrayList<Event> events,CustomerRepository customerRepository,int customerPosition){
        for (int i = 0; i < events.size(); i++){
            double subtotal,total;

            if (events.get(i).getEventID() == id && events.get(i).getEventType().equals("Sport")) {
                totalTicketsSport(i,ticket, numTickets, events);
                if(customerRepository.getMethod().isTicketMiner(customerPosition).equals("true")){
                    subtotal = totalAmountSport(i,ticket,numTickets,events);
                    total = (subtotal+(subtotal*0.0825))-(subtotal*0.1);
                    customerRepository.getMethod().collectTotalDiscounts(customerPosition,subtotal*0.1);
                    events.get(i).collectTotalDiscounts(subtotal*0.1);
                    events.get(i).collectTotalTaxEvent(subtotal*0.0825);
                    return total;
                }else {
                    subtotal = totalAmountSport(i,ticket,numTickets,events);
                    total = subtotal+(subtotal*0.0825);
                    events.get(i).collectTotalTaxEvent(subtotal*0.0825);
                    return total; //--> Including Taxes
                }
            } else if (events.get(i).getEventID() == id && events.get(i).getEventType().equals("Concert")) {
                totalTicketsConcert(i,ticket, numTickets, events);
                if(customerRepository.getMethod().isTicketMiner(customerPosition).equals("true")){
                    subtotal = totalAmountConcert(i,ticket,numTickets,events);
                    total = (subtotal+(subtotal*0.0825))-(subtotal*0.1);
                    customerRepository.getMethod().collectTotalDiscounts(customerPosition,subtotal*0.1);
                    events.get(i).collectTotalDiscounts(subtotal*0.1);
                    events.get(i).collectTotalTaxEvent(subtotal*0.0825);
                    return total;
                }else {
                    subtotal = totalAmountConcert(i,ticket,numTickets,events);
                    total = subtotal+(subtotal*0.0825); //--> (50 dlls * TAX) + 50 dlls
                    events.get(i).collectTotalTaxEvent(subtotal*0.0825);
                    return total; //--> Including Taxes
                }
            } else if (events.get(i).getEventID() == id && events.get(i).getEventType().equals("Special")) {
                totalTicketsSpecial(i,ticket, numTickets, events);
                if(customerRepository.getMethod().isTicketMiner(customerPosition).equals("true")){
                    subtotal = totalAmountSpecial(i,ticket,numTickets,events);
                    total = (subtotal+(subtotal*0.0825))-(subtotal*0.1);
                    customerRepository.getMethod().collectTotalDiscounts(customerPosition,subtotal*0.1);
                    events.get(i).collectTotalDiscounts(subtotal*0.1);
                    events.get(i).collectTotalTaxEvent(subtotal*0.0825);
                    return total;
                }else {
                    subtotal = totalAmountSpecial(i,ticket,numTickets,events);
                    total = subtotal+(subtotal*0.0825);
                    events.get(i).collectTotalTaxEvent(subtotal*0.0825);
                    return total; //--> Including Taxes
                }
            }
        } //first for loop
        return 0;
    } //NUEVO

    /**
     * Method provided by Christian A. Gomez.
     * This method will update event's information such as number of seats available, revenue, and total seats sold.
     * @param i Position of the event.
     * @param ticket Type of ticket such as VIP,Gold,etc.
     * @param numTickets Number of tickets in the order.
     * @param events An ArrayList of Event.
     */
    public static void totalTicketsSport(int i,String ticket, int numTickets, ArrayList<Event> events) {
        if (events.get(i).getVenueType().equals("Stadium")) {
            if (ticket.equals("VIP")|| ticket.equals("Vip")) {
                //Check if there are tickets
                if(events.get(i).getStadium().getVipPct()<numTickets){
                    System.out.println("No more VIP tickets at Stadium(1)");
                }else {
                    double total = events.get(i).getVipPrice();
                    events.get(i).getStadium().totalVipRevenue(numTickets, total);
                    events.get(i).getStadium().subVipPct(numTickets);
                    events.get(i).getStadium().totalSeatsVipSold(numTickets);
                }
            } else if (ticket.equals("Gold")) {
                if(events.get(i).getStadium().getGoldPct()<numTickets){
                    System.out.println("No more GOLD tickets at Stadium(1)");
                }else {
                    double total = events.get(i).getGoldPrice();
                    events.get(i).getStadium().totalGoldRevenue(numTickets, total);
                    events.get(i).getStadium().subGoldPct(numTickets);
                    events.get(i).getStadium().totalSeatsGoldSold(numTickets);
                }
            } else if (ticket.equals("Silver")) {
                if(events.get(i).getStadium().getSilverPct()<numTickets){
                    System.out.println("No more SILVER tickets at Stadium(1)");
                }else {
                    double total = events.get(i).getSilverPrice();
                    events.get(i).getStadium().totalSilverRevenue(numTickets, total);
                    events.get(i).getStadium().subSilverPct(numTickets);
                    events.get(i).getStadium().totalSeatsSilverSold(numTickets);
                }
            } else if (ticket.equals("Bronze")) {
                if(events.get(i).getStadium().getBronzePct()<numTickets){
                    System.out.println("No more BRONZE tickets at Stadium(1)");
                }else {
                    double total = events.get(i).getBronzePrice();
                    events.get(i).getStadium().totalBronzeRevenue(numTickets, total);
                    events.get(i).getStadium().subBronzePct(numTickets);
                    events.get(i).getStadium().totalSeatsBronzeSold(numTickets);
                }
            } else if (ticket.equals("General") || ticket.equals("General Admission")) {
                if(events.get(i).getStadium().getGeneralPct()<numTickets){
                    System.out.println("No more GENERAL tickets at Stadium(1)");
                }else {
                    double total = events.get(i).getGeneralPrice();
                    events.get(i).getStadium().totalGeneralRevenue(numTickets, total);
                    events.get(i).getStadium().subGeneralPct(numTickets);
                    events.get(i).getStadium().totalSeatsGeneralSold(numTickets);
                }
            } else {
                System.out.println("ERROR!");
            }
        } else if (events.get(i).getVenueType().equals("Arena")) {
            if (ticket.equals("VIP")|| ticket.equals("Vip")) {
                if(events.get(i).getArena().getVipPct()<numTickets){
                    System.out.println("No more VIP tickets at Arena(1)");
                }else {
                    double total = events.get(i).getVipPrice();
                    events.get(i).getArena().totalVipRevenue(numTickets, total);
                    events.get(i).getArena().subVipPct(numTickets);
                    events.get(i).getArena().totalSeatsVipSold(numTickets);
                }
            } else if (ticket.equals("Gold")) {
                if(events.get(i).getArena().getGoldPct()<numTickets){
                    System.out.println("No more GOLD tickets at Arena(1)");
                }else {
                    double total = events.get(i).getGoldPrice();
                    events.get(i).getArena().totalGoldRevenue(numTickets, total);
                    events.get(i).getArena().subGoldPct(numTickets);
                    events.get(i).getArena().totalSeatsGoldSold(numTickets);
                }
            } else if (ticket.equals("Silver")) {
                if(events.get(i).getArena().getSilverPct()<numTickets){
                    System.out.println("No more SILVER tickets at Arena(1)");
                }else {
                    double total = events.get(i).getSilverPrice();
                    events.get(i).getArena().totalSilverRevenue(numTickets, total);
                    events.get(i).getArena().subSilverPct(numTickets);
                    events.get(i).getArena().totalSeatsSilverSold(numTickets);
                }
            } else if (ticket.equals("Bronze")) {
                if(events.get(i).getArena().getBronzePct()<numTickets){
                    System.out.println("No more BRONZE tickets at Arena(1)");
                }else {
                    double total = events.get(i).getBronzePrice();
                    events.get(i).getArena().totalBronzeRevenue(numTickets, total);
                    events.get(i).getArena().subBronzePct(numTickets);
                    events.get(i).getArena().totalSeatsBronzeSold(numTickets);
                }
            } else if (ticket.equals("General") || ticket.equals("General Admission")) {
                if(events.get(i).getArena().getGeneralPct()<numTickets){
                    System.out.println("No more GENERAL tickets at Arena(1)");
                }else {
                    double total = events.get(i).getGeneralPrice();
                    events.get(i).getArena().totalGeneralRevenue(numTickets, total);
                    events.get(i).getArena().subGeneralPct(numTickets);
                    events.get(i).getArena().totalSeatsGeneralSold(numTickets);
                }
            } else {
                System.out.println("ERROR! --> totalTicketSport()!!!");
            }
        } else {
            System.out.println("ERROR EN totalTicketsSport()!!!!");
        }
    } //NUEVO

    /**
     * Method provided by Christian A. Gomez.
     * This method will update event's information such as number of seats available, revenue, and total seats sold.
     * @param i Position of the event.
     * @param ticket Type of ticket such as VIP,Gold,etc.
     * @param numTickets Number of tickets in the order.
     * @param events An ArrayList of Event.
     */
    public static void totalTicketsConcert(int i,String ticket, int numTickets, ArrayList<Event> events) {
        if (events.get(i).getVenueType().equals("Stadium")) {
            if (ticket.equals("VIP")|| ticket.equals("Vip")) {
                if(events.get(i).getStadium().getVipPct()<numTickets){
                    System.out.println("No more VIP tickets at Stadium(2)"); //(2) es para decir que esto es de concert.
                }else {
                    double total = events.get(i).getVipPrice();
                    events.get(i).getStadium().totalVipRevenue(numTickets, total);
                    events.get(i).getStadium().subVipPct(numTickets);
                    events.get(i).getStadium().totalSeatsVipSold(numTickets);
                }
            } else if (ticket.equals("Gold")) {
                if(events.get(i).getStadium().getGoldPct()<numTickets){
                    System.out.println("No more GOLD tickets at Stadium(2)");
                }else {
                    double total = events.get(i).getGoldPrice();
                    events.get(i).getStadium().totalGoldRevenue(numTickets, total); /////////////////////////////////
                    events.get(i).getStadium().subGoldPct(numTickets);
                    events.get(i).getStadium().totalSeatsGoldSold(numTickets);
                }
            } else if (ticket.equals("Silver")) {
                if(events.get(i).getStadium().getSilverPct()<numTickets){
                    System.out.println("No more SILVER tickets at Stadium(2)");
                }else {
                    double total = events.get(i).getSilverPrice();
                    events.get(i).getStadium().totalSilverRevenue(numTickets, total);
                    events.get(i).getStadium().subSilverPct(numTickets);
                    events.get(i).getStadium().totalSeatsSilverSold(numTickets);
                }
            } else if (ticket.equals("Bronze")) {
                if(events.get(i).getStadium().getBronzePct()<numTickets){
                    System.out.println("No more BRONZE tickets at Stadium(2)");
                }else {
                    double total = events.get(i).getBronzePrice();
                    events.get(i).getStadium().totalBronzeRevenue(numTickets, total);
                    events.get(i).getStadium().subBronzePct(numTickets);
                    events.get(i).getStadium().totalSeatsBronzeSold(numTickets);
                }
            } else if (ticket.equals("General") || ticket.equals("General Admission")) {
                if(events.get(i).getStadium().getGeneralPct()<numTickets){
                    System.out.println("No more GENERAL tickets at Stadium(2)");
                }else {
                    double total = events.get(i).getGeneralPrice();
                    events.get(i).getStadium().totalGeneralRevenue(numTickets, total);
                    events.get(i).getStadium().subGeneralPct(numTickets);
                    events.get(i).getStadium().totalSeatsGeneralSold(numTickets);
                }
            } else {
                System.out.println("ERROR!");
            }
        } else if (events.get(i).getVenueType().equals("Arena")) {////////////////////////////////////////
            if (ticket.equals("VIP")|| ticket.equals("Vip")) {
                if(events.get(i).getArena().getVipPct()<numTickets){
                    System.out.println("No more VIP tickets at Arena(2)");
                }else {
                    double total = events.get(i).getVipPrice();
                    events.get(i).getArena().totalVipRevenue(numTickets, total);
                    events.get(i).getArena().subVipPct(numTickets);
                    events.get(i).getArena().totalSeatsVipSold(numTickets);
                }
            } else if (ticket.equals("Gold")) {
                if(events.get(i).getArena().getGoldPct()<numTickets){
                    System.out.println("No more GOLD tickets at Arena(2)");
                }else {
                    double total = events.get(i).getGoldPrice();
                    events.get(i).getArena().totalGoldRevenue(numTickets, total);
                    events.get(i).getArena().subGoldPct(numTickets);
                    events.get(i).getArena().totalSeatsGoldSold(numTickets);
                }
            } else if (ticket.equals("Silver")) {
                if(events.get(i).getArena().getSilverPct()<numTickets){
                    System.out.println("No more SILVER tickets at Arena(2)");
                }else {
                    double total = events.get(i).getSilverPrice();
                    events.get(i).getArena().totalSilverRevenue(numTickets, total);
                    events.get(i).getArena().subSilverPct(numTickets);
                    events.get(i).getArena().totalSeatsSilverSold(numTickets);
                }
            } else if (ticket.equals("Bronze")) {
                if(events.get(i).getArena().getBronzePct()<numTickets){
                    System.out.println("No more BRONZE tickets at Arena(2)");
                }else {
                    double total = events.get(i).getBronzePrice();
                    events.get(i).getArena().totalBronzeRevenue(numTickets, total);
                    events.get(i).getArena().subBronzePct(numTickets);
                    events.get(i).getArena().totalSeatsBronzeSold(numTickets);
                }
            } else if (ticket.equals("General") || ticket.equals("General Admission")) {
                if(events.get(i).getArena().getGeneralPct()<numTickets){
                    System.out.println("No more GENERAL tickets at Arena(2)");
                }else {
                    double total = events.get(i).getGeneralPrice();
                    events.get(i).getArena().totalGeneralRevenue(numTickets, total);
                    events.get(i).getArena().subGeneralPct(numTickets);
                    events.get(i).getArena().totalSeatsGeneralSold(numTickets);
                }
            } else {
                System.out.println("ERROR!");
            }
        } else if (events.get(i).getVenueType().equals("Auditorium")) {
            if (ticket.equals("VIP")|| ticket.equals("Vip")) {
                if(events.get(i).getAuditorium().getVipPct()<numTickets){
                    System.out.println("No more VIP tickets at Auditorium(2)");
                }else {
                    double total = events.get(i).getVipPrice();
                    events.get(i).getAuditorium().totalVipRevenue(numTickets, total);
                    events.get(i).getAuditorium().subVipPct(numTickets);
                    events.get(i).getAuditorium().totalSeatsVipSold(numTickets);
                }
            } else if (ticket.equals("Gold")) {
                if(events.get(i).getAuditorium().getGoldPct()<numTickets){
                    System.out.println("No more GOLD tickets at Auditorium(2)");
                }else {
                    double total = events.get(i).getGoldPrice();
                    events.get(i).getAuditorium().totalGoldRevenue(numTickets, total);
                    events.get(i).getAuditorium().subGoldPct(numTickets);
                    events.get(i).getAuditorium().totalSeatsGoldSold(numTickets);
                }
            } else if (ticket.equals("Silver")) {
                if(events.get(i).getAuditorium().getSilverPct()<numTickets){
                    System.out.println("No more SILVER tickets at Auditorium(2)");
                }else {
                    double total = events.get(i).getSilverPrice();
                    events.get(i).getAuditorium().totalSilverRevenue(numTickets, total);
                    events.get(i).getAuditorium().subSilverPct(numTickets);
                    events.get(i).getAuditorium().totalSeatsSilverSold(numTickets);
                }
            } else if (ticket.equals("Bronze")) {
                if(events.get(i).getAuditorium().getBronzePct()<numTickets){
                    System.out.println("No more BRONZE tickets at Auditorium(2)");
                }else {
                    double total = events.get(i).getBronzePrice();
                    events.get(i).getAuditorium().totalBronzeRevenue(numTickets, total);
                    events.get(i).getAuditorium().subBronzePct(numTickets);
                    events.get(i).getAuditorium().totalSeatsBronzeSold(numTickets);
                }
            } else if (ticket.equals("General") || ticket.equals("General Admission")) {
                if(events.get(i).getAuditorium().getGeneralPct()<numTickets){
                    System.out.println("No more GENERAL tickets at Auditorium(2)");
                }else {
                    double total = events.get(i).getGeneralPrice();
                    events.get(i).getAuditorium().totalGeneralRevenue(numTickets, total);
                    events.get(i).getAuditorium().subGeneralPct(numTickets);
                    events.get(i).getAuditorium().totalSeatsGeneralSold(numTickets);
                }
            } else {
                System.out.println("ERROR!");
            }
        } else if (events.get(i).getVenueType().equals("Open Air")) {
            if (ticket.equals("VIP")|| ticket.equals("Vip")) {
                if(events.get(i).getOpenAir().getVipPct()<numTickets){
                    System.out.println("No more VIP tickets at Open Air(2)");
                }else {
                    double total = events.get(i).getVipPrice();
                    events.get(i).getOpenAir().totalVipRevenue(numTickets, total);
                    events.get(i).getOpenAir().subVipPct(numTickets);
                    events.get(i).getOpenAir().totalSeatsVipSold(numTickets);
                }
            } else if (ticket.equals("Gold")) {
                if(events.get(i).getOpenAir().getGoldPct()<numTickets){
                    System.out.println("No more GOLD tickets at Open Air(2)");
                }else {
                    double total = events.get(i).getGoldPrice();
                    events.get(i).getOpenAir().totalGoldRevenue(numTickets, total);
                    events.get(i).getOpenAir().subGoldPct(numTickets);
                    events.get(i).getOpenAir().totalSeatsGoldSold(numTickets);
                }
            } else if (ticket.equals("Silver")) {
                if(events.get(i).getOpenAir().getSilverPct()<numTickets){
                    System.out.println("No more SILVER tickets at Open Air(2)");
                }else {
                    double total = events.get(i).getSilverPrice();
                    events.get(i).getOpenAir().totalSilverRevenue(numTickets, total);
                    events.get(i).getOpenAir().subSilverPct(numTickets);
                    events.get(i).getOpenAir().totalSeatsSilverSold(numTickets);
                }
            } else if (ticket.equals("Bronze")) {
                if(events.get(i).getOpenAir().getBronzePct()<numTickets){
                    System.out.println("No more BRONZE tickets at Open Air(2)");
                }else {
                    double total = events.get(i).getBronzePrice();
                    events.get(i).getOpenAir().totalBronzeRevenue(numTickets, total);
                    events.get(i).getOpenAir().subBronzePct(numTickets);
                    events.get(i).getOpenAir().totalSeatsBronzeSold(numTickets);
                }
            } else if (ticket.equals("General") || ticket.equals("General Admission")) {
                if(events.get(i).getOpenAir().getGeneralPct()<numTickets){
                    System.out.println("No more GENERAL tickets at Open Air(2)");
                }else {
                    double total = events.get(i).getGeneralPrice();
                    events.get(i).getOpenAir().totalGeneralRevenue(numTickets, total);
                    events.get(i).getOpenAir().subGeneralPct(numTickets);
                    events.get(i).getOpenAir().totalSeatsGeneralSold(numTickets);
                }
            } else {
                System.out.println("ERROR!");
            }
        } else {
            System.out.println("ERROR EN totalTicketsConcert()!!!!");
        }
    } //NUEVO

    /**
     * Method provided by Christian A. Gomez.
     * This method will update event's information such as number of seats available, revenue, and total seats sold.
     * @param i Position of the event.
     * @param ticket Type of ticket such as VIP,Gold,etc.
     * @param numTickets Number of tickets in the order.
     * @param events An ArrayList of Event.
     */
    public static void totalTicketsSpecial(int i,String ticket, int numTickets, ArrayList<Event> events) {
        if(events.get(i).getVenueType().equals("Open Air")){
            if (ticket.equals("VIP")|| ticket.equals("Vip")) {
                if(events.get(i).getOpenAir().getVipPct()<numTickets){
                    System.out.println("No more VIP tickets at Open Air(3)");
                }else {
                    double total = events.get(i).getVipPrice();
                    events.get(i).getOpenAir().totalVipRevenue(numTickets, total);
                    events.get(i).getOpenAir().subVipPct(numTickets);
                    events.get(i).getOpenAir().totalSeatsVipSold(numTickets);
                }
            } else if (ticket.equals("Gold")) {
                if(events.get(i).getOpenAir().getGoldPct()<numTickets){
                    System.out.println("No more GOLD tickets at Open Air(3)");
                }else {
                    double total = events.get(i).getGoldPrice();
                    events.get(i).getOpenAir().totalGoldRevenue(numTickets, total);
                    events.get(i).getOpenAir().subGoldPct(numTickets);
                    events.get(i).getOpenAir().totalSeatsGoldSold(numTickets);
                }
            } else if (ticket.equals("Silver")) {
                if(events.get(i).getOpenAir().getSilverPct()<numTickets){
                    System.out.println("No more SILVER tickets at Open Air(3)");
                }else {
                    double total = events.get(i).getSilverPrice();
                    events.get(i).getOpenAir().totalSilverRevenue(numTickets, total);
                    events.get(i).getOpenAir().subSilverPct(numTickets);
                    events.get(i).getOpenAir().totalSeatsSilverSold(numTickets);
                }
            } else if (ticket.equals("Bronze")) {
                if(events.get(i).getOpenAir().getBronzePct()<numTickets){
                    System.out.println("No more BRONZE tickets at Open Air(3)");
                }else {
                    double total = events.get(i).getBronzePrice();
                    events.get(i).getOpenAir().totalBronzeRevenue(numTickets, total);
                    events.get(i).getOpenAir().subBronzePct(numTickets);
                    events.get(i).getOpenAir().totalSeatsBronzeSold(numTickets);
                }
            } else if (ticket.equals("General")|| ticket.equals("General Admission")) {
                if(events.get(i).getOpenAir().getGeneralPct()<numTickets){
                    System.out.println("No more GENERAL tickets at Open Air(3)");
                }else {
                    double total = events.get(i).getGeneralPrice();
                    events.get(i).getOpenAir().totalGeneralRevenue(numTickets, total);
                    events.get(i).getOpenAir().subGeneralPct(numTickets);
                    events.get(i).getOpenAir().totalSeatsGeneralSold(numTickets);
                }
            } else {
                System.out.println("ERROR en totalTicketSpecial()");
            }
        }else if(events.get(i).getVenueType().equals("Arena")){
            if(ticket.equals("VIP")|| ticket.equals("Vip")) {
                if(events.get(i).getArena().getVipPct()<numTickets){
                    System.out.println("No more VIP ticket at Arena(3)");
                }else {
                    double total = events.get(i).getVipPrice();
                    events.get(i).getArena().totalVipRevenue(numTickets, total);
                    events.get(i).getArena().subVipPct(numTickets);
                    events.get(i).getArena().totalSeatsVipSold(numTickets);
                }
            } else if (ticket.equals("Gold")) {
                if(events.get(i).getArena().getGoldPct()<numTickets){
                    System.out.println("No more GOLD ticket at Arena(3)");
                }else {
                    double total = events.get(i).getGoldPrice();
                    events.get(i).getArena().totalGoldRevenue(numTickets, total);
                    events.get(i).getArena().subGoldPct(numTickets);
                    events.get(i).getArena().totalSeatsGoldSold(numTickets);
                }
            } else if (ticket.equals("Silver")) {
                if(events.get(i).getArena().getSilverPct()<numTickets){
                    System.out.println("No more SILVER ticket at Arena(3)");
                }else {
                    double total = events.get(i).getSilverPrice();
                    events.get(i).getArena().totalSilverRevenue(numTickets, total);
                    events.get(i).getArena().subSilverPct(numTickets);
                    events.get(i).getArena().totalSeatsSilverSold(numTickets);
                }
            } else if (ticket.equals("Bronze")) {
                if(events.get(i).getArena().getBronzePct()<numTickets){
                    System.out.println("No more BRONZE ticket at Arena(3)");
                }else {
                    double total = events.get(i).getBronzePrice();
                    events.get(i).getArena().totalBronzeRevenue(numTickets, total);
                    events.get(i).getArena().subBronzePct(numTickets);
                    events.get(i).getArena().totalSeatsBronzeSold(numTickets);
                }
            } else if (ticket.equals("General") || ticket.equals("General Admission")) {
                if(events.get(i).getArena().getGeneralPct()<numTickets){
                    System.out.println("No more GENERAL ticket at Arena(3)");
                }else {
                    double total = events.get(i).getGeneralPrice();
                    events.get(i).getArena().totalGeneralRevenue(numTickets, total);
                    events.get(i).getArena().subGeneralPct(numTickets);
                    events.get(i).getArena().totalSeatsGeneralSold(numTickets);
                }
            } else {
                System.out.println("ERROR en totalTicketSpecial()");
            }
        }else{
            System.out.println("ERROR EN LINEA 692");
        }
    } //NUEVO

    /**
     * Method provided by Christian A. Gomez.
     * This method will provide the total amount to pay without Taxes. Also, the total amount will change by the type of event.
     * @param i Position of the event.
     * @param ticket Type of ticket such as VIP,Gold, etc.
     * @param numTickets Number of tickets in the order.
     * @param events An ArrayList of event.
     * @return total amount to pay without taxes.
     */
    public static double totalAmountSport(int i, String ticket, int numTickets, ArrayList<Event> events) {
        if (ticket.equals("VIP")|| ticket.equals("Vip")) {
            events.get(i).totalSumVip(numTickets);
            return events.get(i).getVipPrice() * numTickets;
        } else if (ticket.equals("Gold")) {
            events.get(i).totalSumGold(numTickets);
            return events.get(i).getGoldPrice() * numTickets;
        } else if (ticket.equals("Silver")) {
            events.get(i).totalSumSilver(numTickets);
            return events.get(i).getSilverPrice() * numTickets;
        } else if (ticket.equals("Bronze")) {
            events.get(i).totalSumBronze(numTickets);
            return events.get(i).getBronzePrice() * numTickets;
        } else if (ticket.equals("General") || ticket.equals("General Admission")) {
            events.get(i).totalSumGeneral(numTickets);
            return events.get(i).getGeneralPrice() * numTickets;
        }
        return 0;
    } //NUEVO

    /**
     * Method provided by Christian A. Gomez.
     * This method will provide the total amount to pay without Taxes. Also, the total amount will change by the type of event.
     * @param i Position of the event.
     * @param ticket Type of ticket such as VIP,Gold, etc.
     * @param numTickets Number of tickets in the order.
     * @param events An ArrayList of event.
     * @return total amount to pay without taxes.
     */
    public static double totalAmountConcert(int i, String ticket, int numTickets, ArrayList<Event> events) {
        if (ticket.equals("VIP")|| ticket.equals("Vip")) {
            events.get(i).totalSumVip(numTickets);
            return events.get(i).getVipPrice() * numTickets;
        } else if (ticket.equals("Gold")) {
            events.get(i).totalSumGold(numTickets);
            return events.get(i).getGoldPrice() * numTickets;
        } else if (ticket.equals("Silver")) {
            events.get(i).totalSumSilver(numTickets);
            return events.get(i).getSilverPrice() * numTickets;
        } else if (ticket.equals("Bronze")) {
            events.get(i).totalSumBronze(numTickets);
            return events.get(i).getBronzePrice() * numTickets;
        } else if (ticket.equals("General") || ticket.equals("General Admission")) {
            events.get(i).totalSumGeneral(numTickets);
            return events.get(i).getGeneralPrice() * numTickets;
        }
        return 0;
    } //NUEVO

    /**
     * Method provided by Christian A. Gomez.
     * This method will provide the total amount to pay without Taxes. Also, the total amount will change by the type of event.
     * @param i Position of the event.
     * @param ticket Type of ticket such as VIP,Gold, etc.
     * @param numTickets Number of tickets in the order.
     * @param events An ArrayList of event.
     * @return total amount to pay without taxes.
     */
    public static double totalAmountSpecial(int i, String ticket, int numTickets, ArrayList<Event> events) {
        if (ticket.equals("VIP")|| ticket.equals("Vip")) {
            events.get(i).totalSumVip(numTickets);
            return events.get(i).getVipPrice() * numTickets;
        } else if (ticket.equals("Gold")) {
            events.get(i).totalSumGold(numTickets);
            return events.get(i).getGoldPrice() * numTickets;
        } else if (ticket.equals("Silver")) {
            events.get(i).totalSumSilver(numTickets);
            return events.get(i).getSilverPrice() * numTickets;
        } else if (ticket.equals("Bronze")) {
            events.get(i).totalSumBronze(numTickets);
            return events.get(i).getBronzePrice() * numTickets;
        } else if (ticket.equals("General") || ticket.equals("General Admission")) {
            events.get(i).totalSumGeneral(numTickets);
            return events.get(i).getGeneralPrice() * numTickets;
        }
        return 0;
    } //NUEVO
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //Este metodo lo pongo al inicio. Este metodo solo se encargara de hacer un Log.txt. En cada metodo que uses
    //Trata de usar este metodo. Example: printLog("The admin has selected add new event.");

    /**
     * Method provided by Christian A. Gomez.
     * This method will be in charge to create a Log.txt with all the actions that were taken during the program execution.
     * @param whatHappen The action.
     * @throws IOException Throws if there is an IOException during the process.
     */
    public static void printLog(String whatHappen){
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("Log.txt", true));
            bw.write(whatHappen);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /////////////////////////////////////////////////////////////////////////////////
    //Todos los metodos que pronte aqui seran dedicados al MENU. Para que no te me confundas.

    /**
     * Method provided by Christian A. Gomez.
     * @param user User input to identify if the user is a Customer or an Administrator.
     * @param events An ArrayList of Events.
     * @param customerRepository An instance with all the customers.
     * @throws IOException Throw if there is any IOException during the process.
     */
    public static void checkUserIdentity(int user,ArrayList<Event> events,CustomerRepository customerRepository,ArrayList<AutoPurchase> autoPurchases){
        try {
            if (user == 1) {
                administratorMenu(events, customerRepository);
                printLog("An Admin is in the system.");
            } else if (user == 2) {
                createCustomerOrder(customerRepository, events);
                printLog("A customer is in the system.");
            } else if(user == 3) {
                doAutoPurchase(customerRepository,autoPurchases,events);
                printLog("Auto purchase is executing...loading...");
            }
        }catch(IOException exception){
            System.out.println("There was an error.");
        }
    } //NUEVO
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //Aqui tendremos doAutoPurchase() method

    /**
     * Method provided by Christian A. Gomez.
     * This method will be in charge to execute all the auto purchases.
     * @param customerRepository An object with all the customers.
     * @param autoPurchases An ArrayList of AutoPurchase.
     * @param events An ArrayList of Event.
     * @throws IOException Throw an error if there is an invalid input.
     */
    public static void doAutoPurchase(CustomerRepository customerRepository,ArrayList<AutoPurchase> autoPurchases,ArrayList<Event> events) throws IOException {
        //Instrucciones para que no te pierdas:
        //(1) Recuerda leer customers y autoPurchase juntos. Estos dos van agarrados de la mano.
        //(2) Recuerda usar los metodos que tienes escrito en tu cuaderno. Estos son los metodos usados para la compra.
        //(3) NO borres los otros metodos a menos que estes 100% seguro que este metodo ya funcione totalmente.
        printLog("AutoPurchase processing, please give a moment...");
        Ticket ticket; //--> create ticket
        String name; //--> User name
        String lastName; //--> User last name
        int eventID; //--> event id that the customer is interested
        String typeTicket; //-->Vip? Gold? Silver? Bronze? General?
        int numTickets; //--> How many tickets the customer wants
        String eventName; //--> Name of the event

        for(int i = 0; i<autoPurchases.size();i++){
            int notCustomersPosition = hereIsNotCustomer(i, customerRepository, autoPurchases);
            int eventID2 = autoPurchases.get(notCustomersPosition).getEventID();
            String typeTicket2 = autoPurchases.get(notCustomersPosition).getTicketType();
            int numTickets2 = autoPurchases.get(notCustomersPosition).getTicketQuantity();
            otherUsers(eventID2, typeTicket2, numTickets2, events);
            ////////////////////////////////////////////////////////////////////////////
            //First let's check the customers we can find in the autoPurchase ArrayList.
            int customerPosition = hereIsCustomer(i, customerRepository,autoPurchases);
            name = autoPurchases.get(customerPosition).getFirstName();
            lastName = autoPurchases.get(customerPosition).getLastName();
            eventID = autoPurchases.get(customerPosition).getEventID();
            typeTicket = autoPurchases.get(customerPosition).getTicketType();
            numTickets = autoPurchases.get(customerPosition).getTicketQuantity();
            eventName = autoPurchases.get(i).getEventName();

            //Check the num of tickets
            if (numTickets < 2 || numTickets > 6) {
                System.out.println("You can purchase 2-6 tickets per transaction! Go back again!");
            }

            int position = customerRepository.getMethod().findCustomer(name,lastName);

            //Now we calculate the total amount of money:
            double totalAmount = totalAmount(eventID, typeTicket, numTickets, events, customerRepository, position);

            //Check customer's balance money
            if (customerRepository.getMethod().checkBalance(position) < totalAmount) {
                break;
            }
            ticket = new Ticket(eventID,eventName, typeTicket, numTickets, totalAmount);
            customerOrder(name, lastName, typeTicket, totalAmount, ticket, events,  customerRepository);
        }
    }
    /**
     * Method provided by Christian A. Gomez.
     * This method will support doAutoPurchase() method by doing purchases from customers that are not part
     * the customer csv file.
     * @param eventID Event ID that the customer is interested.
     * @param typeTicket Tickets such as VIP, Gold, Silver,etc.
     * @param numTickets Number of tickets that the customer wants to purchase.
     * @param events An ArrayList of Event.
     */
    public static void otherUsers(int eventID,String typeTicket,int numTickets,ArrayList<Event> events){
        for(int i=0;i<events.size();i++){
            double total;
            if(events.get(i).getEventID() == eventID){
                if(events.get(i).getEventType().equals("Sport")){
                    totalTicketsSport(i, typeTicket, numTickets, events);
                    total = totalAmountSport(i, typeTicket, numTickets, events);
                    events.get(i).collectTotalTaxEvent(total * 0.0825);
                }else if(events.get(i).getEventType().equals("Concert")){
                    totalTicketsConcert(i, typeTicket, numTickets, events);
                    total = totalAmountConcert(i, typeTicket, numTickets, events);
                    events.get(i).collectTotalTaxEvent(total * 0.0825);
                }else if(events.get(i).getEventType().equals("Special")){
                    totalTicketsSpecial(i, typeTicket, numTickets, events);
                    total = totalAmountSpecial(i, typeTicket, numTickets, events);
                    events.get(i).collectTotalTaxEvent(total * 0.0825);
                }else{
                    System.out.println("Error en la linea 767");
                }
            }
        }
    }
    /**
     * Method provided by Christian A. Gomez.
     * This method is in charge to find the customers we already know inside the autoPurchase array list.
     * @param i Position of auto purchase.
     * @param customerRepository An object of all customers.
     * @param autoPurchases An ArrayList of Auto purchase
     * @return the position of the customer if he/she is there.
     */
    public static int hereIsCustomer(int i,CustomerRepository customerRepository,ArrayList<AutoPurchase> autoPurchases){
        int position;
        for(CustomerIterator iterator = customerRepository.getMethod(); iterator.hasNext();){
            Customer customer = iterator.next();
            String name = customer.getFirstName();
            String lastName = customer.getLastName();
            if(autoPurchases.get(i).getFirstName().equals(name) && autoPurchases.get(i).getLastName().equals(lastName)){
                position = i;
                return position;
            }
        }
        return -1;
    }
    /**
     * Method provided by Christian A. Gomez.
     * This method is in charge to know which customers are not in the Auto purchase array list.
     * @param i Position of auto purchase.
     * @param customerRepository An object of all customers.
     * @param autoPurchases An ArrayList of Auto purchase
     * @return The position where our customer wasn't found.
     */
    public static int hereIsNotCustomer(int i,CustomerRepository customerRepository,ArrayList<AutoPurchase> autoPurchases){
        int position;
        for(CustomerIterator iterator = customerRepository.getMethod(); iterator.hasNext();){
            Customer customer = iterator.next();
            String name = customer.getFirstName();
            String lastName = customer.getLastName();
            if(!autoPurchases.get(i).getFirstName().equals(name) && !autoPurchases.get(i).getLastName().equals(lastName)){
                position = i;
                return position;
            }
        }
        return -1;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //Aqui tendremos toda la seccion de customer

    /**
     * Method provided by Christian A. Gomez.
     * This method will be in charge to create the customer order.
     * @param customerRepository An instance of all customers.
     * @param events An ArrayList of Event.
     * @throws IOException Throw if there is any IOException during the process.
     */
    public static void createCustomerOrder(CustomerRepository customerRepository, ArrayList<Event> events){
        Scanner kb = new Scanner(System.in);
        printLog("User has initialize a process.");

        System.out.println("Are you an individual user? y/n");
        char user = kb.nextLine().charAt(0);

        try {

            boolean sentinel = false;
            while (!sentinel) {
                if (user == 'y') {
                    printLog("Single user is creating an order.");
                    individualOrder(customerRepository, events);
                    printLog("Single customer in the system.");
                    sentinel = true;
                } else if (user == 'n') {
                    printLog("Multiple user are creating orders.");
                    multipleOrders(customerRepository, events);
                    printLog("Multiple customers in the system.");
                    sentinel = true;
                } else {
                    System.out.println("Something went wrong. Please try again:");
                    user = kb.nextLine().charAt(0);
                }
            }
        }catch(IOException e){
            System.out.println("Something went wrong.");
        }
    } //NUEVO

    /**
     * Method implemented by Victor Herrera
     * To handle customer log in Errors
     * @param firstName user input
     * @param customerRepository provides instance of all customers
     * Method will take first name and compare it with
     * customer list to ensure input matches first name in list.
     *
     * /---------------------Notes ------------------------/
     * For Code Review Initially when user would enter first name program
     * Continues but after some review program takes any input and continues to function
     * This method was the first step to prevent this as it can lead to security issues.
     *
     * **/
    private static boolean firstNameCheck(String firstName,CustomerRepository customerRepository) {
        for(CustomerIterator iterator = customerRepository.getMethod(); iterator.hasNext();){
            Customer customer = iterator.next();
            if(customer.getFirstName().equals(firstName)){
                return customer.getFirstName().equals(firstName);
            }
        }
        return false;
    }
    /**
     * Method implemented by Victor Herrera
     * To handle customer log in Errors
     * @param firstName previos input that will be used in current function
     * @param lastName  user input
     * @param customerRepository provides instance of all customers
     * Method will take last name and compare it with
     * customer list to ensure input matches last name
     * first name will be carried over to ensure that last name is valid.
     *
     *
     * /---------------------Notes ------------------------/
     * For Code Review
     * Last Name Input when conducted Review would take any input and program would
     * Continue. This would be an issue when running further functions as program
     * Could resutl in error such as Ticket having bad names.
     *
     * **/
    private static boolean lastNameCheck(String firstName,String lastName,CustomerRepository customerRepository){
        for(CustomerIterator iterator = customerRepository.getMethod(); iterator.hasNext();){
            Customer customer = iterator.next();
            if(customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName)){
                return customer.getLastName().equals(lastName);
            }
        }

        return false;
    }
    /**
     * Method provided by Victor Herrera
     * @param firstName,lastName,userName user input.
     * method will take previous userInput and concatenate with userName input.
     * and compare that information pertains to the individual with previous input.
     * preventing a random user name that does not match to the individual.
     * **/
    private static boolean userNameCheck(String firstName,String lastName,String userName,CustomerRepository customerRepository){
        for(CustomerIterator iterator = customerRepository.getMethod(); iterator.hasNext();){
            Customer customer = iterator.next();
            if(customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName)&&customer.getUserName().equals(userName)){
                return customer.getUserName().equals(userName);
            }
        }
        return false;
    }
    /**
     * Method Provided by Victor Herrera
     * Method will take previous input from user up to current input
     * and compare information of user input to that of repository to
     * Ensure information is valid.
     * @param firstName previous user input
     * @param lastName  previous user input
     * @param userName  previous user input
     * @param password current user input.
     * @param customerRepository instance of customer repository.
     * **/
    private static boolean checkUserPassword(String firstName,String lastName,String userName,String password,CustomerRepository customerRepository){
        for(CustomerIterator iterator = customerRepository.getMethod(); iterator.hasNext();){
            Customer customer = iterator.next();
            if(customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName)&&customer.getUserName().equals(userName)&&customer.getPassword().equals(password)){
                return customer.getPassword().equals(password);
            }
        }
        return false;
    }
    public static boolean getEventId(int id){
        ArrayList<Event>listOfIds= generateEventList();

        for(int i =0;i<listOfIds.size();i++){
            if(listOfIds.get(i).getEventID()==id){
                return true;
            }
        }
        return false;
    }
    /**
     * Method Provided by Victor Herrera
     * Method is designed to take user input and check to ensure
     * Input values are correct
     * @return id returns correct Id.
     *
     *
     * **/
    public static int checkInvalidInput() {
        Scanner kb = new Scanner(System.in);
        int id = 0;
        System.out.println("Please enter the ID event you are interested:");
        while (true) {//Always receive user input
            try {
                String temp = kb.nextLine();
                boolean flag = temp.matches("[0-71]+");

                if (flag) {
                    id = Integer.parseInt(temp);
                    return id;
                }else {//If the match is not successful, it goes to the next loop
                    System.out.println("The values entered do not match");

                }

            } catch (InputMismatchException e) {
                System.out.println("The values entered do not match");
                kb.next();
            }

        }

    }
    /**
     * Method provided by Victor Herrera
     * Method will be used to verify that user input matches that of the options.
     * @param ticketType userInput
     *
     * **/
    public static boolean verifyTicketType(String ticketType){

        if(ticketType.equals("Vip")||ticketType.equals("VIP")){
            return true;
        }
        else if(ticketType.equals("Gold")||ticketType.equals("gold")){
            return true;
        }
        else if(ticketType.equals("Silver")||ticketType.equals("silver")){
            return true;
        }
        else if(ticketType.equals("Bronze")||ticketType.equals("bronze")){

            return true;
        }
        else if(ticketType.equals("General")||ticketType.equals("general")){
            return true;
        }
        return false;
    }
    /**
     * Method provided by Victor Herrera
     * @return numTickets
     * returns the correct amount of tickets
     * that a user can purchase.
     * **/
    public static int checkNumberOfTickets() {
        Scanner kb = new Scanner(System.in);
        int numTicket = 0;
        System.out.println("Please enter the number of tickets you want:");
        while (true) {
            try {
                String temp = kb.nextLine();
                boolean flag = temp.matches("[2-6]+");

                if (flag) {
                    numTicket = Integer.parseInt(temp);
                    return numTicket;
                }else {
                    System.out.println("Invalid try again.");

                }

            } catch (InputMismatchException e) {
                System.out.println("Expecting Integer detected invalid input");
                kb.next();
            }

        }
    }

    /**
     * Method provided by Christian A. Gomez. Modifications Made by Victor Herrera
     * This method will be in charge to create an individual order.
     * @param customerRepository An instance of all customers.
     * @param events An ArrayList of Event.
     * @throws IOException Throw if the there is any IOException during the process.
     * Modifications will check to ensure first,last,username,password match correct individual.
     */
    public static void individualOrder(CustomerRepository customerRepository, ArrayList<Event> events) throws IOException {

        Scanner kb = new Scanner(System.in);

        Ticket ticket;//Create a ticket for the order

        System.out.println("Individual user! Got it!");
        System.out.println("Please enter your first name:");


        // while loop checks user input to ensure first name is correct
        // countFirst will keep track amount of times input is correct the return to main menu.
        //
        int countFirst =0;
        String firstName = kb.nextLine();
        while(!(firstNameCheck(firstName,customerRepository))){
            if(!(firstNameCheck(firstName,customerRepository))){
                System.out.println("Try again");
                System.out.println("Enter Main Menu to return to Main Menu");
            }
            firstName = kb.nextLine();
            if(firstName.toLowerCase().equals("main menu")){
                System.out.println("Returning to Main Menu.......");
                startMenu();
            }
            countFirst++;
            if(countFirst==4){
                System.out.println("You have reached maximum attempts returning to main Menu......");
                startMenu();
            }

        }
        int countLast =0;
        System.out.println("Please enter your last name:");
        String lastName = kb.nextLine();
        while(!(lastNameCheck(firstName,lastName,customerRepository))){
            if(!(lastNameCheck(firstName,lastName,customerRepository))){
                System.out.println("Try again");
                System.out.println("Enter Main Menu to return to Main Menu");

            }
            lastName = kb.nextLine();
            if(lastName.toLowerCase().equals("main menu")){
                System.out.println("Returning to Main Menu.......");
                startMenu();
            }
            countLast++;
            if(countLast==4){
                System.out.println("You have reached maximum attempts returning to main Menu......");
                startMenu();
            }

        }
        int countUserName =0;
        System.out.println("Please enter user name:");
        String userName = kb.nextLine();

        while(!(userNameCheck(firstName,lastName,userName,customerRepository))){
            if(!(userNameCheck(firstName,lastName,userName,customerRepository))){
                System.out.println("Try again");
                System.out.println("Enter Main Menu to return to Main Menu");
            }
            userName = kb.nextLine();
            if(userName.toLowerCase().equals("main menu")){
                System.out.println("Returning to Main Menu.......");
                startMenu();
            }
            countUserName++;
            if(countUserName==4){
                System.out.println("You have reached maximum attempts returning to main Menu......");
                startMenu();
            }

        }

        int countPassword =4;
        System.out.println("Please enter the password:");
        String password = kb.nextLine();
        while(!(checkUserPassword(firstName,lastName,userName,password,customerRepository))){
            if(!(checkUserPassword(firstName,lastName,userName,password,customerRepository))){
                System.out.println("What password did you say? That was a wrong password");
                System.out.println("Try again");
                System.out.println("You have "+(countPassword--) + " Attempts left");
                System.out.println("Enter Main Menu to return to Main Menu");
            }
            password = kb.nextLine();
            if(password.toLowerCase().equals("main menu")){
                System.out.println("Returning to Main Menu.......");
                startMenu();
            }

            if(countPassword==0){
                System.out.println("You have reached maximum attempts returning to main Menu......");
                startMenu();
            }

        }

        checkIfCustomerExists(firstName, lastName, customerRepository);

        int customerPosition = customerRepository.getMethod().findCustomer(firstName, lastName);
        int id =0;
        id= checkInvalidInput();

        printUserMenu(id, events);

        System.out.println("Select type of ticket: [Vip] [Gold] [Silver] [Bronze] [General]:");
        String typeTicket = kb.nextLine();
        while(!verifyTicketType(typeTicket)){
            System.out.println("Incorrect input try again");
            typeTicket =kb.nextLine();
        }




        int numTickets =0;
        numTickets = checkNumberOfTickets();
        printLog("User ordered " + numTickets + " " + typeTicket + " ticket(s)");

        if (numTickets < 2 || numTickets > 6) {
            System.out.println("You can purchase 2-6 tickets per transaction! Go back again!");
        }

        double totalAmount = totalAmount(id, typeTicket, numTickets, events, customerRepository, customerPosition);
        ticket = new Ticket(id, typeTicket, numTickets, totalAmount);

        customerOrder(firstName, lastName, typeTicket, totalAmount, ticket, events, customerRepository);
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will be in charge to create multiple orders.
     * @param customerRepository An instance of all customers.
     * @param events An ArrayList of Event.
     * @throws IOException Throw if the there is any IOException during the process.
     */
    public static void multipleOrders(CustomerRepository customerRepository, ArrayList<Event> events){
        Scanner kb = new Scanner(System.in);
        Ticket ticket;

        System.out.println("Multiple users! Please enter the total number of users:");
        int numUsers = kb.nextInt();
        kb.nextLine();
        printLog("A total of " + numUsers + " will use the system.");

        try {
            int counter = 0;
            while (counter < numUsers) {
                System.out.println("Please enter your first name:");
                String name = kb.nextLine();
                System.out.println("Please enter your last name:");
                String lastName = kb.nextLine();
                int doItAgain = 0;
                do {
                    System.out.println("Please enter the password:");
                    String password = kb.nextLine();

                    if (!password(name, lastName, password, customerRepository)) {
                        System.out.println("What password did you say? That was a wrong password");
                    } else {
                        doItAgain = 1;
                    }
                } while (doItAgain == 0);

                //Check if the customer exists
                checkIfCustomerExists(name, lastName, customerRepository);
                int customerPosition = customerRepository.getMethod().findCustomer(name, lastName);

                System.out.println("Please enter the ID event you are interested:");
                int id = kb.nextInt();
                kb.nextLine();
                printUserMenu(id, events);

                System.out.println("Select type of ticket: [Vip] [Gold] [Silver] [Bronze] [General]:");
                String typeTicket = kb.nextLine();

                System.out.println("Enter the number of tickets you want:");
                int numTickets = kb.nextInt();
                kb.nextLine();
                printLog("User ordered " + numTickets + " " + typeTicket + " ticket(s)");

                if (numTickets < 2 || numTickets > 6) {
                    System.out.println("You can purchase 2-6 tickets per transaction! Go back again!");
                }
                double totalAmount = totalAmount(id, typeTicket, numTickets, events, customerRepository, customerPosition);
                ticket = new Ticket(id, typeTicket, numTickets, totalAmount);

                customerOrder(name, lastName, typeTicket, totalAmount, ticket, events, customerRepository);
                counter++;
            }//while loop
        }catch(IOException ioException){
            System.out.println("Something went wrong.");
        }
    } //NUEVO

    /**
     * Method provided by Christian A. Gomez.
     * This method will check if the password provided matches with the customer's password.
     * @param name Customer's first name.
     * @param lastName Customer's Last name.
     * @param password Password provided by the user.
     * @param customerRepository An instance of all customers.
     * @return True or False if the password matches.
     */
    private static boolean password(String name,String lastName,String password,CustomerRepository customerRepository){
        for(CustomerIterator iterator = customerRepository.getMethod(); iterator.hasNext();){
            Customer customer = iterator.next();
            if(customer.getFirstName().equals(name) && customer.getLastName().equals(lastName)){
                return customer.getPassword().equals(password);
            }
        }
        return false;
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will check if the customer exist or not.
     * @param name First name provided.
     * @param lastName Last name provided.
     * @param customerRepository An instance with all customers.
     * @throws IOException Throw an IOException.
     */
    public static void checkIfCustomerExists(String name, String lastName,CustomerRepository customerRepository) throws IOException {
        for(CustomerIterator iterator = customerRepository.getMethod(); iterator.hasNext();){
            Customer customer = iterator.next();
            if(customer.getFirstName().equals(name) && customer.getLastName().equals(lastName)){
                System.out.println("Welcome " + name + " " + lastName + "!!!");
                printLog("User:" + name + " " + lastName + " is in the system.");
                break;
            }
        }
    } //NUEVO

    /**
     * Method provided by Christian A. Gomez.
     * This method will print a menu to the customer.
     * @param id Event ID.
     * @param eventList An ArrayList of Event.
     * @throws IOException Throw an IOException.
     */
    public static void printUserMenu(int id, ArrayList<Event> eventList) throws IOException {
        for(int i=0;i<eventList.size();i++){
            if(eventList.get(i).getEventID() == id){
                System.out.println(eventList.get(i).toStringCustomer());
            }
        }
        printLog("Event ID:" + id + " info was displayed for the user.");
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will take the final step to create the customer's order.
     * @param name Customer's first name.
     * @param lastName Customer's Last name.
     * @param typeTicket Type of ticket such as VIP,Gold,etc.
     * @param totalAmount Total amount to pay.
     * @param ticket An instance of Ticket.
     * @param events An ArrayList of Event.
     * @param customerRepository An instance of all customers.
     * @throws IOException Throw an IOException.
     */
    public static void customerOrder(String name, String lastName, String typeTicket, double totalAmount, Ticket ticket,
                                     ArrayList<Event> events, CustomerRepository customerRepository) throws IOException {

        int customerPosition = customerRepository.getMethod().findCustomer(name,lastName);
        customerRepository.getMethod().withdraw(customerPosition,totalAmount);
        customerRepository.getMethod().addTicket(customerPosition,ticket);
        customerRepository.getMethod().totalConcerts(customerPosition);
        customerRepository.getMethod().displayInfo(customerPosition);
        printLog("User:" + name + " has a ticket now.");
    } //NUEVO
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Method provided by Christian A. Gomez.
     * This method will display administrator options.
     * @param events An ArrayList of Event.
     * @param customerRepository An instance of all customers.
     * @throws IOException Throw an IOException.
     */
    public static void administratorMenu(ArrayList<Event> events,CustomerRepository customerRepository) throws IOException {
        Scanner kb = new Scanner(System.in);

        printLog("Administrator Menu executed.");
        System.out.println("Welcome Administrator!");
        System.out.println("Enter: (A) Inquire event by ID. (B) Inquire event by name. (C) Add new event. (D) Electronic Summary." +
                " (E) Display Customer Information:");
        char enter = kb.nextLine().charAt(0);

        if (enter == 'A') {
            printLog("Admin selected Inquire event by ID.");
            System.out.println("What is the ID of the event? Enter the ID event:");
            int eventID = kb.nextInt();
            printEventId(eventID, events);
        } else if (enter == 'B') {
            printLog("Admin selected inquire event by name.");
            System.out.println("What is the name of the event? Enter the type event:");
            String eventName = kb.nextLine();
            printEventName(eventName, events);
        } else if(enter == 'C'){
            printLog("Admin selected add a new event.");
            System.out.println("You selected Add new event:");
            //Here we will read the Venue List provided and then use a method to add new events.
            readVenueCsv(events);
            for(int i=0;i<events.size();i++){
                System.out.println(events.get(i));
            }

        }else if(enter == 'D'){
            printLog("Admin selected display electronic tickets from customers.");
            System.out.println("You selected display electronic tickets :D");
            displayElectronicTicket(customerRepository);
        }else if(enter == 'E'){
            printLog("Admin selected display customer's information.");
            System.out.println("You selected display customer's information");
            displayCustomerInfo(customerRepository);
        } else{
            System.out.println("Error! Input invalid!");
        }
    } //NUEVO
    //////////////////////////////////////////////////////////////////////////////////
    //Are para hacer Electronic Ticket Summary de algun customer

    /**
     * Method provided by Christian A. Gomez.
     * Modifications Made by Victor Herrera
     * Added checks to verify first and last name are correc and match customers.
     * This method will generate an Electronic Ticket Summary with all the customer's tickets.
     * @param customerRepository An instance of all customers.
     * @throws IOException Throw an IOException.
     */
    public static void displayElectronicTicket(CustomerRepository customerRepository) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("ElectronicTicketSummary.txt"));
        Scanner kb = new Scanner(System.in);
        int doItAgain=0;
        do{
            System.out.println("Please enter the name:");
            String name = kb.nextLine();

            while(!(firstNameCheck(name,customerRepository))){
                System.out.println("Name does not exist try again");
                name =kb.nextLine();
            }
            System.out.println("Please enter the last name:");

            String lastName = kb.nextLine();
            while(!(lastNameCheck(name,lastName,customerRepository))){
                System.out.println("Last name does not match with first name try again");
                lastName = kb.nextLine();

            }

            int customerPosition = customerRepository.getMethod().findCustomer(name,lastName);

            bw.write(customerRepository.getMethod().electronicTicket(customerPosition));
            bw.newLine();
            printLog("An electronic ticket was displayed.");

            System.out.println("Wanna try with another customer? yes/no");
            String ans = kb.nextLine();

            if(ans.toLowerCase().equals("no")){
                doItAgain=1;
            }
        }while(doItAgain==0);
        bw.close();
    }
    ////////////////////////////////////////////////////////////////////////////////
    //Esta parte sera exclusivamente para readVenueCsv

    /**
     * Method provided by Christian A. Gomez.
     * This method will read and store all the information from the Venue csv file.
     * @param events An ArrayList of Event.
     * @throws FileNotFoundException Throw an error if the is no file.
     */
    public static void readVenueCsv(ArrayList<Event> events) throws FileNotFoundException {
        Venue venue; //-> Esto lo usare luego para guardar los datos del csv file.
        Stadium stadium;
        Arena arena;
        Auditorium auditorium;
        OpenAir openAir;

        ArrayList<Venue> venueList = new ArrayList<>();
        String line = "";
        int csvLine = 0;

        //Primero leamos el documento:
        try{
            BufferedReader br = new BufferedReader(new FileReader("Update CSV file/hi"));

            while((line = br.readLine()) != null){
                if(csvLine == 0){
                    csvLine++;
                    continue;
                }
                printLog("Processing venueList.csv file");
                String[] space = line.split(",");
                String venueName = space[1]; //--> listo
                String venueType = space[2]; //--> Listo
                int pctSeatsUnavailable = 0; //-->Listo
                int capacity = Integer.parseInt(space[3]);
                int vipPct = Integer.parseInt(space[6]);
                int goldPct = Integer.parseInt(space[7]);
                int silverPct = Integer.parseInt(space[8]);
                int bronzePct = Integer.parseInt(space[9]);
                int generalPct = Integer.parseInt(space[10]);
                int reservedPct = Integer.parseInt(space[11]);
                double cost = Double.parseDouble(space[5]);

                if(venueType.equals("Stadium")){
                    stadium = new Stadium(venueName,venueType,pctSeatsUnavailable,capacity,cost,vipPct,goldPct,silverPct,bronzePct,generalPct,reservedPct);
                    venueList.add(stadium);
                }else if(venueType.equals("Arena")){
                    arena = new Arena(venueName,venueType,pctSeatsUnavailable,capacity,cost,vipPct,goldPct,silverPct,bronzePct,generalPct,reservedPct);
                    venueList.add(arena);
                }else if(venueType.equals("Auditorium")){
                    auditorium = new Auditorium(venueName,venueType,pctSeatsUnavailable,capacity,cost,vipPct,goldPct,silverPct,bronzePct,generalPct,reservedPct);
                    venueList.add(auditorium);
                }else if(venueType.equals("Open Air")){
                    openAir = new OpenAir(venueName,venueType,pctSeatsUnavailable,capacity,cost,vipPct,goldPct,silverPct,bronzePct,generalPct,reservedPct);
                    venueList.add(openAir);
                }else{
                    System.out.println("ERROR EN LINEA 1338");
                }
            }
            addNewEvents(venueList,events);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static String verifyVenueInput(String venueInput){
        Scanner kb = new Scanner(System.in);
        if((venueInput.equals("Sun Bowl Stadium")))
            return venueInput;

        else if(venueInput.equals("Don Haskins Center"))
            return venueInput;
        else if(venueInput.equals("Magoffin Auditorium"))
            return venueInput;
        else if(venueInput.equals("San Jacinto Plaza"))
            return venueInput;
        else if(venueInput.equals("Centennial Plaza"))
            return venueInput;
        else
            System.out.println("You have entered  a venue that doe not exist try agani");
           venueInput = kb.nextLine();
           verifyVenueInput(venueInput);

        return null;
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will be in charge to add new events according the administrator information.
     * @param venueList An ArrayList of Venue.
     * @param events An ArrayList of Event.
     * @throws IOException Throw an IOException.
     */
    public static void addNewEvents(ArrayList<Venue> venueList,ArrayList<Event> events) throws IOException {
        Scanner kb = new Scanner(System.in);
        printLog("Adding events in process.");
        Event event;
        //Vamos a crear un nuevo evento! Aqui si usaremos lo del Object x = filterVenue();
        System.out.println("Welcome! You are going to add a new event! Please follow the next steps to do it!");
        String eventType = askEventType();
        String eventName = askEventName();
        String eventDate = askEventDate();
        String eventTime = askEventTime();
        String fireworks = askFireworks();
        double fireworksCost = askFireworksCost(fireworks);

        //Aqui empezaremos a crear el evento:
        System.out.println("Please select a venue(once you select a venue, the program will automatically compute the rest):");
        System.out.println("[Sun Bowl Stadium],[Don Haskins Center],[Magoffin Auditorium],[San Jacinto Plaza]" +
                " [Centennial Plaza]");
        // Add method to catch this.
        String venueInput = kb.nextLine();
        venueInput = verifyVenueInput(venueInput);

        for(int i=0;i<venueList.size();i++){
            if(venueList.get(i).getNameAvenue().equals(venueInput)){
                int eventID = (int)(Math.random()*1000);
                String venueName = venueList.get(i).getNameAvenue();//-->Nos servira para los if
                String venueType = venueList.get(i).getNameType();
                int pctSeatsUnavailable = venueList.get(i).getPctSeats();
                double generalPrice = askGeneralPrice();
                double vipPrice = (generalPrice*5.0);
                double goldPrice = (generalPrice*3.0);
                double silverPrice = (generalPrice*2.5);
                double bronzePrice = (generalPrice*1.5);
                if(venueName.equals("Sun Bowl Stadium")){
                    int capacity = venueList.get(i).getCapacity();
                    int vipPct = calculatePct(capacity,venueList.get(i).getVipPct());
                    int goldPct = calculatePct(capacity,venueList.get(i).getGoldPct());
                    int silverPct = calculatePct(capacity,venueList.get(i).getSilverPct());
                    int bronzePct = calculatePct(capacity,venueList.get(i).getBronzePct());
                    int generalPct = calculatePct(capacity,venueList.get(i).getGeneralPct());
                    int reservedPct = calculatePct(capacity,venueList.get(i).getReservedPct());
                    double cost = venueList.get(i).getCost()+fireworksCost;
                    event = filterEvent(eventID,eventType,eventName,eventDate,eventTime,vipPrice,goldPrice,silverPrice,bronzePrice,
                            generalPrice,venueName,pctSeatsUnavailable,venueType,capacity,cost,vipPct,goldPct,silverPct,
                            bronzePct,generalPct,reservedPct,fireworks,fireworksCost);

                    events.add(event);
                    printLog("A new event has been added.");
                }else if(venueName.equals("Don Haskins Center")){
                    int capacity = venueList.get(i).getCapacity();
                    int vipPct = calculatePct(capacity,venueList.get(i).getVipPct());
                    int goldPct = calculatePct(capacity,venueList.get(i).getGoldPct());
                    int silverPct = calculatePct(capacity,venueList.get(i).getSilverPct());
                    int bronzePct = calculatePct(capacity,venueList.get(i).getBronzePct());
                    int generalPct = calculatePct(capacity,venueList.get(i).getGeneralPct());
                    int reservedPct = calculatePct(capacity,venueList.get(i).getReservedPct());
                    double cost = venueList.get(i).getCost()+fireworksCost;
                    event = filterEvent(eventID,eventType,eventName,eventDate,eventTime,vipPrice,goldPrice,silverPrice,bronzePrice,
                            generalPrice,venueName,pctSeatsUnavailable,venueType,capacity,cost,vipPct,goldPct,silverPct,
                            bronzePct,generalPct,reservedPct,fireworks,fireworksCost);

                    events.add(event);
                    printLog("A new event has been added.");
                }else if(venueName.equals("Magoffin Auditorium")){
                    int capacity = venueList.get(i).getCapacity();
                    int vipPct = calculatePct(capacity,venueList.get(i).getVipPct());
                    int goldPct = calculatePct(capacity,venueList.get(i).getGoldPct());
                    int silverPct = calculatePct(capacity,venueList.get(i).getSilverPct());
                    int bronzePct = calculatePct(capacity,venueList.get(i).getBronzePct());
                    int generalPct = calculatePct(capacity,venueList.get(i).getGeneralPct());
                    int reservedPct = calculatePct(capacity,venueList.get(i).getReservedPct());
                    double cost = venueList.get(i).getCost()+fireworksCost;
                    event = filterEvent(eventID,eventType,eventName,eventDate,eventTime,vipPrice,goldPrice,silverPrice,bronzePrice,
                            generalPrice,venueName,pctSeatsUnavailable,venueType,capacity,cost,vipPct,goldPct,silverPct,
                            bronzePct,generalPct,reservedPct,fireworks,fireworksCost);

                    events.add(event);
                    printLog("A new event has been added.");
                }else if(venueName.equals("San Jacinto Plaza") || venueName.equals("Centenial Plaza")){
                    int capacity = venueList.get(i).getCapacity();
                    int vipPct = calculatePct(capacity,venueList.get(i).getVipPct());
                    int goldPct = calculatePct(capacity,venueList.get(i).getGoldPct());
                    int silverPct = calculatePct(capacity,venueList.get(i).getSilverPct());
                    int bronzePct = calculatePct(capacity,venueList.get(i).getBronzePct());
                    int generalPct = calculatePct(capacity,venueList.get(i).getGeneralPct());
                    int reservedPct = calculatePct(capacity,venueList.get(i).getReservedPct());
                    double cost = venueList.get(i).getCost()+fireworksCost;
                    event = filterEvent(eventID,eventType,eventName,eventDate,eventTime,vipPrice,goldPrice,silverPrice,bronzePrice,
                            generalPrice,venueName,pctSeatsUnavailable,venueType,capacity,cost,vipPct,goldPct,silverPct,
                            bronzePct,generalPct,reservedPct,fireworks,fireworksCost);

                    events.add(event);
                    printLog("A new event has been added.");
                }
            }
        }
    }

    /**
     * Method provided by Christian A. Gomez.
     * Modifications made Victor Herrera
     * Added modification that input is correct and function does not just take any input.
     * This method will ask for the type of Event that wants to add.
     * @return The event type.
     */
    public static String askEventType(){
        Scanner kb = new Scanner(System.in);

        String eventType;
        System.out.println("Please enter the type of event: [Sport],[Concert],[Special]:");
        eventType = kb.nextLine();
        while(!(eventType.equals("Sport")||eventType.equals("Concert")||eventType.equals("Special")))
        {
            System.out.println("You have entered invalid type of event event try again. ");
            eventType = kb.nextLine();
        }
        return eventType;
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will ask for the name of the event that wants to add.
     * @return Event name.
     */
    public static String askEventName(){
        Scanner kb = new Scanner(System.in);

        String eventName;
        System.out.println("Please enter the event name:");
        eventName = kb.nextLine();

        return eventName;
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will ask for the date of the event that wants to add.
     * @return Event date.
     */
    public static String askEventDate(){

            Scanner kb = new Scanner(System.in);

            String eventDate ="";
        try {

            System.out.println("Please enter the event's date:  HINT->Use the format:MM/DD/YY");
            eventDate = kb.nextLine();

            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(eventDate);

        }catch ( ParseException e){
            System.out.println("invalid format please enter correct format");
            askEventDate();

        }
       return eventDate;
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will ask for the time of the event that wants to add.
     * @return Event time.
     */
    public static String askEventTime(){
        Scanner kb = new Scanner(System.in);

        String eventTime ="";
        System.out.println("Please enter the event's time:");
        try {
            eventTime = kb.nextLine();
            DateFormat sdf = new SimpleDateFormat("hh:mm aa");
            Date d1 = sdf.parse(eventTime);
        }catch (ParseException e){
            System.out.println("Bad input try again");
            askEventTime();
        }

        return eventTime;
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will ask for the general price of the event that wants to add.
     * @return General price.
     */
    public static double askGeneralPrice(){
        Scanner kb = new Scanner(System.in);
        double generalPrice;

        System.out.println("NOTE: -->Vip,gold,silver and bronze prices will be computed automatically "+
                "using the total amount you enter for the general price <--");

        System.out.println("Please enter the general price: HINT:price must be between 0-500 dollars");
        generalPrice = kb.nextDouble();

        while(generalPrice<0 || generalPrice>500){
            System.out.println("Please enter a valid price:");
            generalPrice = kb.nextDouble();
        }
        return generalPrice;
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will ask for the fireworks of the event that wants to add.
     * @return If there will be fireworks.
     */
    public static String askFireworks(){
        Scanner kb = new Scanner(System.in);

        System.out.println("Your event will have fireworks? yes/no");
        String userInput = kb.nextLine().toLowerCase();

        while(!userInput.equals("yes") && !userInput.equals("no")){
            System.out.println("Try it again!:");
            userInput = kb.nextLine().toLowerCase();
        }
        return userInput;
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will ask for the fireworks cost, if any, of the event that wants to add.
     * @param fireworks If there will be fireworks or not.
     * @return Fireworks cost.
     */
    public static double askFireworksCost(String fireworks){
        Scanner kb = new Scanner(System.in);

        double cost;
        if(fireworks.equals("yes")){
            System.out.println("Please enter the cost of the fireworks:");
            cost = kb.nextDouble();
        }else{
            System.out.println("No fireworks. Then cost is 0.");
            cost = 0;
        }
        return cost;
    }

    /**
     * Method provided by Christian A. Gomez.
     * This method will compute the pct seats of the event by the venue capacity.
     * @param capacity Venue capacity.
     * @param pct percentage. Example: VIP -> %5 of the venue capacity.
     * @return total pct.
     */
    public static int calculatePct(int capacity,int pct){
        double x = pct;
        double percentage = x/100;
        return (int) (capacity*percentage);
    }
    ///////////////////////////////////////////////////////////////////////////////

    /**
     * Method provided by Christian A. Gomez.
     * This method will print the event information by its ID.
     * @param eventID Event ID.
     * @param events An ArrayList of Event.
     * @throws IOException Throw an IOException.
     */
    public static void printEventId(int eventID, ArrayList<Event> events) throws IOException {
        printLog("An event was displayed by its event ID.");
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventID() == eventID) {
                System.out.println(events.get(i).displayAdmin2());
            }
        }//for loop
    } //NUEVO

    /**
     * Method provided by Christian A. Gomez.
     * This method will display the event's information by its name.
     * @param eventName Event name.
     * @param events An ArrayList of Event.
     * @throws IOException Throw an IOException.
     */
    public static void printEventName(String eventName, ArrayList<Event> events) throws IOException {
        printLog("An event was displayed by its name.");
        for (int i = 0; i < events.size(); i++) {
            if(events.get(i).getName().equals(eventName)){
                System.out.println(events.get(i).displayAdmin2());
            }
        }//for loop
    } //NUEVO

    /**
     * Method provided by Christian A. Gomez.
     * This method will dispplay all the customer's information to the administrator.
     * @param customerRepository An instance of all customers.
     */
    public static void displayCustomerInfo(CustomerRepository customerRepository){
        Scanner kb = new Scanner(System.in);

        System.out.println("Enter a name:");
        String name = kb.nextLine();
        System.out.println("Enter a last name:");
        String lastName = kb.nextLine();

        int customerPosition = customerRepository.getMethod().findCustomer(name,lastName);
        customerRepository.getMethod().adminAccess(customerPosition);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Method provided by Christian A. Gomez.
     * This method will display all the events.
     * @param eventList An ArrayList of Event.
     */
    public static void printEvents(ArrayList<Event> eventList){
        for(int i=0;i<eventList.size();i++){
            System.out.println(eventList.get(i));
        }
    }

    /**
     * Method provided by Christian A. Gomez.
     * Modifications implemented by Victor Herrera.
     * This method will read an Event csv file and generate an ArrayList of Event with all the information from the
     * csv file.
     * @return An ArrayList of Event.
     */
    public static ArrayList<Event> generateEventList(){

        Event event;
        ArrayList<Event> eventListFinal = new ArrayList<>();

        String fileName = "src/EventListPA4FINAL (1).csv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = "";
            line = br.readLine();
            String[] firstLine = line.split(",");
            HashMap<String, Integer> MapHeader = new HashMap<String, Integer>();// String -> String 0
            for (int i = 0; i < firstLine.length; i++) {
                MapHeader.put(firstLine[i], i);
            }
            while ((line = br.readLine()) != null) {
                String[] index = line.split(",");
                if(index.length< MapHeader.size()) {
                    index = resize(index, MapHeader);
                }
                if(index[index.length-1]==null){
                    index[index.length-1]=String.valueOf(0);
                }
                if(index[MapHeader.get("Fireworks Planned")]==null){
                    index[MapHeader.get("Fireworks Planned")]="No";
                }
                String eventType = index[MapHeader.get("Event Type")];
                int pctSeatsUnavailable = Integer.parseInt(index[MapHeader.get("Pct Seats Unavailable")]);
                double goldPrice = Double.parseDouble(index[MapHeader.get("Gold Price")]);
                String eventName = index[MapHeader.get("Name")];
                int capacity = Integer.parseInt(index[MapHeader.get("Capacity")]);
                String eventTime = index[MapHeader.get("Time")];
                int reservedPct = Integer.parseInt(index[MapHeader.get("Reserved Extra Pct")]);
                String venueType = index[MapHeader.get("Venue Type")];
                String eventDate = index[MapHeader.get("Date")];
                double vipPrice = Double.parseDouble(index[MapHeader.get("VIP Price")]);
                int generalPct = Integer.parseInt(index[MapHeader.get("General Admission Pct")]);
                int bronzePct = Integer.parseInt(index[MapHeader.get("Bronze Pct")]);
                double bronzePrice = Double.parseDouble(index[MapHeader.get("Bronze Price")]);
                int silverPct = Integer.parseInt(index[MapHeader.get("Silver Pct")]);
                double generalPrice = Double.parseDouble(index[MapHeader.get("General Admission Price")]);
                double silverPrice = Double.parseDouble(index[MapHeader.get("Silver Price")]);
                String fireworks = index[MapHeader.get("Fireworks Planned")];
                String venueName = index[MapHeader.get("Venue Name")];
                int eventID = Integer.parseInt(index[MapHeader.get("Event ID")]);
                int cost = Integer.parseInt(index[MapHeader.get("Cost")]);
                int vipPct = Integer.parseInt(index[MapHeader.get("VIP Pct")]);
                int goldPct = Integer.parseInt(index[MapHeader.get("Gold Pct")]);
                double fireworksCost = Double.parseDouble(index[MapHeader.get("Fireworks Cost")]);

                event = filterEvent(eventID,eventType,eventName,eventDate,eventTime,vipPrice,goldPrice,silverPrice,bronzePrice,
                        generalPrice,venueName,pctSeatsUnavailable,venueType,capacity,cost,vipPct,goldPct,silverPct,
                        bronzePct,generalPct,reservedPct,fireworks,fireworksCost);

                eventListFinal.add(event);
                //Aqui haces lo del object y le haces add al arraylist
            }//while loop
        } catch (IOException z) {
            z.printStackTrace();
        }//catch
        return eventListFinal;
    }
    public static String [] resize(String index[],HashMap hashMap){
        String arr [] = new String[hashMap.size()];
        for(int i =0;i<index.length;i++){
            arr[i]=index[i];
        }

        return arr;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Method that create a filter depending the type of event such as Sport,Concert,or Special.
     * @param type Type of event.
     * @param name Name of the event.
     * @param date Date of the event.
     * @param time Time of the event.
     * @param vip VIP price.
     * @param gold Gold price.
     * @param silver Silver price.
     * @param bronze Bronze price.
     * @param general General price.
     * @param avenueName Name of the Venue
     * @param pctSeats Number of pct seats.
     * @param venueType If the venue is in Stadium,Arena,etc.
     * @param capacity Total capacity of the venue.
     * @param cost Cost of the venue.
     * @param vipPct Vip seats.
     * @param goldPct Gold seats.
     * @param silverPct Silver seats.
     * @param bronzePct Bronze seats.
     * @param generalPct General seats.
     * @param reservedPct Reserved seats.
     * @param fireWorks If there are fireworks.
     * @param fireCost Total cost of implementing fireworks.
     * @return An object of Sport,Concert, or Special.
     */
    public static Event filterEvent(int eventID,String type, String name, String date, String time, double vip, double gold,
                                    double silver, double bronze, double general, String avenueName, int pctSeats,
                                    String venueType, int capacity, double cost, int vipPct, int goldPct, int silverPct,
                                    int bronzePct, int generalPct, int reservedPct, String fireWorks, double fireCost) {
        //Declare Sport,Concert,and Special Objects
        Event event;
        Stadium stadium;
        Arena arena;
        Auditorium auditorium;
        OpenAir openAir;
        //Checks type and return an Object
        if (type.equals("Sport")) {
            if(avenueName.equals("Sun Bowl Stadium")){
                stadium = new Stadium(avenueName,venueType,pctSeats,capacity,cost,vipPct,goldPct,silverPct,bronzePct,
                        generalPct,reservedPct,fireWorks,fireCost);
                event = new Sport(eventID,type,venueType,name,date,time,vip,gold,silver,bronze,general,stadium);
                return event;
            }else if(avenueName.equals("Don Haskins Center")){
                arena = new Arena(avenueName,venueType,pctSeats,capacity,cost,vipPct,goldPct,silverPct,bronzePct,
                        generalPct,reservedPct,fireWorks,fireCost);
                event = new Sport(eventID,type,venueType,name,date,time,vip,gold,silver,bronze,general,arena);
                return event;
            }else{
                System.out.println("ERROR EN LINEA 136");
            }
        }else if (type.equals("Concert")) {
            if(avenueName.equals("Sun Bowl Stadium")){
                stadium = new Stadium(avenueName,venueType,pctSeats,capacity,cost,vipPct,goldPct,silverPct,bronzePct,
                        generalPct,reservedPct,fireWorks,fireCost);
                event = new Concert(eventID,type,venueType,name,date,time,vip,gold,silver,bronze,general,stadium);
                return event;
            }else if(avenueName.equals("Don Haskins Center")){
                arena = new Arena(avenueName,venueType,pctSeats,capacity,cost,vipPct,goldPct,silverPct,bronzePct,
                        generalPct,reservedPct,fireWorks,fireCost);
                event = new Concert(eventID,type,venueType,name,date,time,vip,gold,silver,bronze,general,arena);
                return event;
            }else if(avenueName.equals("Magoffin Auditorium")){
                auditorium = new Auditorium(avenueName,venueType,pctSeats,capacity,cost,vipPct,goldPct,silverPct,bronzePct,
                        generalPct,reservedPct,fireWorks,fireCost);
                event = new Concert(eventID,type,venueType,name,date,time,vip,gold,silver,bronze,general,auditorium);
                return event;
            }else if(avenueName.equals("San Jacinto Plaza") || avenueName.equals("Centennial Plaza")){
                openAir = new OpenAir(avenueName,venueType,pctSeats,capacity,cost,vipPct,goldPct,silverPct,bronzePct,
                        generalPct,reservedPct,fireWorks,fireCost);
                event = new Concert(eventID,type,venueType,name,date,time,vip,gold,silver,bronze,general,openAir);
                return event;
            }else{
                System.out.println("ERROR EN LINEA 160");
            }
        } else if (type.equals("Special")) {
            if(avenueName.equals("Don Haskins Center")){
                arena = new Arena(avenueName,venueType,pctSeats,capacity,cost,vipPct,goldPct,silverPct,bronzePct,
                        generalPct,reservedPct,fireWorks,fireCost);
                event = new Special(eventID,type,venueType,name,date,time,vip,gold,silver,bronze,general,arena);
                return event;
            }else if(avenueName.equals("San Jacinto Plaza") || avenueName.equals("Centennial Plaza")){
                openAir = new OpenAir(avenueName,venueType,pctSeats,capacity,cost,vipPct,goldPct,silverPct,bronzePct,
                        generalPct,reservedPct,fireWorks,fireCost);
                event = new Special(eventID,type,venueType,name,date,time,vip,gold,silver,bronze,general,openAir);
                return event;
            }
        } else {
            return null;
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //Area donde leo el AutoPurchase

    /**
     * Method provided by Christian A. Gomez.
     * This method will read and store all the information from the purchase csv files.
     * @return An ArrayList of Purchase.
     */
    public static ArrayList<AutoPurchase> generateCustomerPurchase(){
        AutoPurchase autoPurchase;
        ArrayList<AutoPurchase> customerPurchase = new ArrayList<>();

        String line = "";
        int csvLine = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/AutoPurchase.csv"));
            while((line = br.readLine()) != null){
                if(csvLine==0){
                    csvLine++;
                    continue;
                }
                String[] space = line.split(",");
                String firstName = space[0];
                String lastName = space[1];
                String action = space[2];
                int eventID = Integer.parseInt(space[3]);
                String eventName = space[4];
                int ticketQuantity = Integer.parseInt(space[5]);
                String ticketType = space[6];

                autoPurchase = new AutoPurchase(firstName,lastName,action,eventID,eventName,ticketQuantity,ticketType);
                customerPurchase.add(autoPurchase);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return customerPurchase;
    }

    /**
     * Method provided by Christian A. Gomez.
     * Modifications implemented by Victor Herrera.
     * This method will read and store all the information from the customer csv file into an instance of customers.
     * @return CustomerRepository with all the customer's information.
     */
    public static CustomerRepository generateCustomerRepository(){
        Customer customer;
        ArrayList<Customer> customerListFinal = new ArrayList<>();
        CustomerRepository customerRepository = new CustomerRepository();

        String file = "src/CustomerListPA4FINAL.csv";
        try{
            BufferedReader br  = new BufferedReader(new FileReader(file));
            String line = "";
            line = br.readLine();
            String[] firstLine = line.split(",");
            HashMap<String, Integer> MapHeader = new HashMap<String, Integer>();
            for (int i = 0; i < firstLine.length; i++) {
                MapHeader.put(firstLine[i], i);
            }
            while((line=br.readLine())!=null){
                String[]index = line.split(",");
                String password = index[MapHeader.get("Password")];
                double balance = Double.parseDouble(index[MapHeader.get("Money Available")]);
                String lastName = index[MapHeader.get("Last Name")];
                int customerID = Integer.parseInt(index[MapHeader.get("ID")]);
                int concertPurchase = Integer.parseInt(index[MapHeader.get("Concerts Purchased")]);
                String firstName = index[MapHeader.get("First Name")];
                String isTicketMiner = (index[MapHeader.get("TicketMiner Membership")]);
                String userName = index[MapHeader.get("Username")];

                customer = new Customer(customerID,firstName,lastName,balance,concertPurchase,isTicketMiner,userName,password);
                customerListFinal.add(customer);
                customerRepository = new CustomerRepository(customerListFinal);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return customerRepository;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    Here we are going to update the csv files with all the information collected.
     */
    /**
     * Method provided by Christian A. Gomez.
     * Method that generates a new CustomerList csv file with all the information updated.
     * @param customerRepository A list of customers from the CustomerListPA4.csv.
     * @throws IOException Throw an error if something goes wrong.
     */
    public static void generateCsvCustomer(CustomerRepository customerRepository) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("CustomerListFinalPA4_Updated.csv",true));
        bw.write("ID"+","+"Name"+","+"Last Name"+","+"Money Available"+","+"Concerts purchased"+","+"Ticket Miner"+
                ","+"Usarname"+","+"Password");
        bw.newLine();
        for (CustomerIterator iterator = customerRepository.getMethod(); iterator.hasNext();){
            Customer customer = iterator.next();
            bw.write(customer.getCustomerID()+","+customer.getFirstName()+","+customer.getLastName()+
                    ","+customer.getBalance()+","+customer.getTotalConcertsPurchased()+
                    ","+customer.getTicketMiner()+","+customer.getUserName()+","+customer.getPassword());
            bw.newLine();
        }
        bw.close();
    }
    /**
     * Method provided by Christian A. Gomez.
     * Method that generates a new EventList csv file with all the information updated.
     * @param events A list of events from the EventListPA2.csv.
     * @throws IOException Throw an error if something goes wrong.
     */
    public static void generateCsvEvent(ArrayList<Event> events) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("EventListPA4FINAL_Updated.csv",true));
        bw.write("Event ID" + "," + "Event Type" + "," + "Name" + "," + "Date" + "," + "Time" + "," + "Vip price" +
                "," + "Gold price" + "," + "Silver price" + "," + "Bronze price" + "," + "General Price" + "," + "Venue name" +
                "," + "Pct seats" + "," + "Venue Type" + "," + "Capacity" + "," + "Cost" + "," + "Vip pct" + "," + "Gold pct" +
                "," + "Silver pct" + "," + "Bronze pct" + "," + "General pct" + "," + "Reserved" + "," + "Fireworks" +
                "," + "Fireworks Cost"+","+"Vip seats sold"+","+"Gold seats sold"+","+"Silver seats sold"+
                "," + "Bronze seats sold"+","+"General seats sold"+","+"Total vip revenue"+","+"Total gold revenue"+
                "," + "Total silver revenue "+","+"Total bronze revenue"+","+"Total general revenue");
        bw.newLine();
        for (int i = 0; i < events.size(); i++) {
            //Sport
            if (events.get(i).getEventType().equals("Sport")) {

                bw.write(events.get(i).getEventID()+
                        ","+events.get(i).getEventType()+
                        ","+events.get(i).getName() +
                        ","+ events.get(i).getDate()+
                        ","+events.get(i).getTime()+
                        ","+events.get(i).getVipPrice()+
                        ","+events.get(i).getGoldPrice()+
                        ","+events.get(i).getSilverPrice()+
                        ","+events.get(i).getBronzePrice()+
                        ","+events.get(i).getGeneralPrice());
                if(events.get(i).getVenueType().equals("Stadium")){
                    bw.write(","+events.get(i).getStadium().getNameAvenue()+
                            ","+events.get(i).getStadium().getPctSeats()+
                            ","+events.get(i).getStadium().getNameType()+
                            ","+events.get(i).getStadium().getCapacity()+
                            ","+events.get(i).getStadium().getCost()+
                            ","+events.get(i).getStadium().getVipPct()+
                            ","+events.get(i).getStadium().getGoldPct()+
                            ","+events.get(i).getStadium().getSilverPct()+
                            ","+events.get(i).getStadium().getBronzePct()+
                            ","+events.get(i).getStadium().getGeneralPct()+
                            ","+events.get(i).getStadium().getReservedPct()+
                            ","+events.get(i).getStadium().getFireWorks()+
                            ","+events.get(i).getStadium().getFireWorksCost()+
                            ","+events.get(i).getStadium().getTotalSeatsVip()+
                            ","+events.get(i).getStadium().getTotalSeatsGold()+
                            ","+events.get(i).getStadium().getTotalSeatsSilver()+
                            ","+events.get(i).getStadium().getTotalSeatsBronze()+
                            ","+events.get(i).getStadium().getTotalSeatsGeneral()+
                            ","+events.get(i).getStadium().getTotalVipRevenue()+ /////
                            ","+events.get(i).getStadium().getTotalGoldRevenue()+
                            ","+events.get(i).getStadium().getTotalSilverRevenue()+
                            ","+events.get(i).getStadium().getTotalBronzeRevenue()+
                            ","+events.get(i).getStadium().getTotalGeneralRevenue());
                }else if(events.get(i).getVenueType().equals("Arena")){
                    bw.write(","+events.get(i).getArena().getNameAvenue()+
                            ","+events.get(i).getArena().getPctSeats()+
                            ","+events.get(i).getArena().getNameType()+
                            ","+events.get(i).getArena().getCapacity()+
                            ","+events.get(i).getArena().getCost()+
                            ","+events.get(i).getArena().getVipPct()+
                            ","+events.get(i).getArena().getGoldPct()+
                            ","+events.get(i).getArena().getSilverPct()+
                            ","+events.get(i).getArena().getBronzePct()+
                            ","+events.get(i).getArena().getGeneralPct()+
                            ","+events.get(i).getArena().getReservedPct()+
                            ","+events.get(i).getArena().getFireWorks()+
                            ","+events.get(i).getArena().getFireWorksCost()+
                            ","+events.get(i).getArena().getTotalSeatsVip()+
                            ","+events.get(i).getArena().getTotalSeatsGold()+
                            ","+events.get(i).getArena().getTotalSeatsSilver()+
                            ","+events.get(i).getArena().getTotalSeatsBronze()+
                            ","+events.get(i).getArena().getTotalSeatsGeneral()+
                            ","+events.get(i).getArena().getTotalVipRevenue()+ /////
                            ","+events.get(i).getArena().getTotalGoldRevenue()+
                            ","+events.get(i).getArena().getTotalSilverRevenue()+
                            ","+events.get(i).getArena().getTotalBronzeRevenue()+
                            ","+events.get(i).getArena().getTotalGeneralRevenue());
                }
                bw.newLine();
            }// if sport
            if(events.get(i).getEventType().equals("Concert")){
                bw.write(events.get(i).getEventID()+
                        ","+events.get(i).getEventType()+
                        ","+events.get(i).getName() +
                        ","+ events.get(i).getDate()+
                        ","+events.get(i).getTime()+
                        ","+events.get(i).getVipPrice()+
                        ","+events.get(i).getGoldPrice()+
                        ","+events.get(i).getSilverPrice()+
                        ","+events.get(i).getBronzePrice()+
                        ","+events.get(i).getGeneralPrice());
                if(events.get(i).getVenueType().equals("Stadium")){
                    bw.write(","+events.get(i).getStadium().getNameAvenue()+
                            ","+events.get(i).getStadium().getPctSeats()+
                            ","+events.get(i).getStadium().getNameType()+
                            ","+events.get(i).getStadium().getCapacity()+
                            ","+events.get(i).getStadium().getCost()+
                            ","+events.get(i).getStadium().getVipPct()+
                            ","+events.get(i).getStadium().getGoldPct()+
                            ","+events.get(i).getStadium().getSilverPct()+
                            ","+events.get(i).getStadium().getBronzePct()+
                            ","+events.get(i).getStadium().getGeneralPct()+
                            ","+events.get(i).getStadium().getReservedPct()+
                            ","+events.get(i).getStadium().getFireWorks()+
                            ","+events.get(i).getStadium().getFireWorksCost()+
                            ","+events.get(i).getStadium().getTotalSeatsVip()+
                            ","+events.get(i).getStadium().getTotalSeatsGold()+
                            ","+events.get(i).getStadium().getTotalSeatsSilver()+
                            ","+events.get(i).getStadium().getTotalSeatsBronze()+
                            ","+events.get(i).getStadium().getTotalSeatsGeneral()+
                            ","+events.get(i).getStadium().getTotalVipRevenue()+ /////
                            ","+events.get(i).getStadium().getTotalGoldRevenue()+
                            ","+events.get(i).getStadium().getTotalSilverRevenue()+
                            ","+events.get(i).getStadium().getTotalBronzeRevenue()+
                            ","+events.get(i).getStadium().getTotalGeneralRevenue());
                }else if(events.get(i).getVenueType().equals("Auditorium")){
                    bw.write(","+events.get(i).getAuditorium().getNameAvenue()+
                            ","+events.get(i).getAuditorium().getPctSeats()+
                            ","+events.get(i).getAuditorium().getNameType()+
                            ","+events.get(i).getAuditorium().getCapacity()+
                            ","+events.get(i).getAuditorium().getCost()+
                            ","+events.get(i).getAuditorium().getVipPct()+
                            ","+events.get(i).getAuditorium().getGoldPct()+
                            ","+events.get(i).getAuditorium().getSilverPct()+
                            ","+events.get(i).getAuditorium().getBronzePct()+
                            ","+events.get(i).getAuditorium().getGeneralPct()+
                            ","+events.get(i).getAuditorium().getReservedPct()+
                            ","+events.get(i).getAuditorium().getFireWorks()+
                            ","+events.get(i).getAuditorium().getFireWorksCost()+
                            ","+events.get(i).getAuditorium().getTotalSeatsVip()+
                            ","+events.get(i).getAuditorium().getTotalSeatsGold()+
                            ","+events.get(i).getAuditorium().getTotalSeatsSilver()+
                            ","+events.get(i).getAuditorium().getTotalSeatsBronze()+
                            ","+events.get(i).getAuditorium().getTotalSeatsGeneral()+
                            ","+events.get(i).getAuditorium().getTotalVipRevenue()+ /////
                            ","+events.get(i).getAuditorium().getTotalGoldRevenue()+
                            ","+events.get(i).getAuditorium().getTotalSilverRevenue()+
                            ","+events.get(i).getAuditorium().getTotalBronzeRevenue()+
                            ","+events.get(i).getAuditorium().getTotalGeneralRevenue());
                }else if(events.get(i).getVenueType().equals("Arena")){
                    bw.write(","+events.get(i).getArena().getNameAvenue()+
                            ","+events.get(i).getArena().getPctSeats()+
                            ","+events.get(i).getArena().getNameType()+
                            ","+events.get(i).getArena().getCapacity()+
                            ","+events.get(i).getArena().getCost()+
                            ","+events.get(i).getArena().getVipPct()+
                            ","+events.get(i).getArena().getGoldPct()+
                            ","+events.get(i).getArena().getSilverPct()+
                            ","+events.get(i).getArena().getBronzePct()+
                            ","+events.get(i).getArena().getGeneralPct()+
                            ","+events.get(i).getArena().getReservedPct()+
                            ","+events.get(i).getArena().getFireWorks()+
                            ","+events.get(i).getArena().getFireWorksCost()+
                            ","+events.get(i).getArena().getTotalSeatsVip()+
                            ","+events.get(i).getArena().getTotalSeatsGold()+
                            ","+events.get(i).getArena().getTotalSeatsSilver()+
                            ","+events.get(i).getArena().getTotalSeatsBronze()+
                            ","+events.get(i).getArena().getTotalSeatsGeneral()+
                            ","+events.get(i).getArena().getTotalVipRevenue()+ /////
                            ","+events.get(i).getArena().getTotalGoldRevenue()+
                            ","+events.get(i).getArena().getTotalSilverRevenue()+
                            ","+events.get(i).getArena().getTotalBronzeRevenue()+
                            ","+events.get(i).getArena().getTotalGeneralRevenue());
                }else if(events.get(i).getVenueType().equals("Open Air")){
                    bw.write(","+events.get(i).getOpenAir().getNameAvenue()+
                            ","+events.get(i).getOpenAir().getPctSeats()+
                            ","+events.get(i).getOpenAir().getNameType()+
                            ","+events.get(i).getOpenAir().getCapacity()+
                            ","+events.get(i).getOpenAir().getCost()+
                            ","+events.get(i).getOpenAir().getVipPct()+
                            ","+events.get(i).getOpenAir().getGoldPct()+
                            ","+events.get(i).getOpenAir().getSilverPct()+
                            ","+events.get(i).getOpenAir().getBronzePct()+
                            ","+events.get(i).getOpenAir().getGeneralPct()+
                            ","+events.get(i).getOpenAir().getReservedPct()+
                            ","+events.get(i).getOpenAir().getFireWorks()+
                            ","+events.get(i).getOpenAir().getFireWorksCost()+
                            ","+events.get(i).getOpenAir().getTotalSeatsVip()+
                            ","+events.get(i).getOpenAir().getTotalSeatsGold()+
                            ","+events.get(i).getOpenAir().getTotalSeatsSilver()+
                            ","+events.get(i).getOpenAir().getTotalSeatsBronze()+
                            ","+events.get(i).getOpenAir().getTotalSeatsGeneral()+
                            ","+events.get(i).getOpenAir().getTotalVipRevenue()+ /////
                            ","+events.get(i).getOpenAir().getTotalGoldRevenue()+
                            ","+events.get(i).getOpenAir().getTotalSilverRevenue()+
                            ","+events.get(i).getOpenAir().getTotalBronzeRevenue()+
                            ","+events.get(i).getOpenAir().getTotalGeneralRevenue());
                }
                bw.newLine();
            }//concert
            //
            if(events.get(i).getEventType().equals("Special")){
                bw.write(events.get(i).getEventID()+
                        ","+events.get(i).getEventType()+
                        ","+events.get(i).getName() +
                        ","+ events.get(i).getDate()+
                        ","+events.get(i).getTime()+
                        ","+events.get(i).getVipPrice()+
                        ","+events.get(i).getGoldPrice()+
                        ","+events.get(i).getSilverPrice()+
                        ","+events.get(i).getBronzePrice()+
                        ","+events.get(i).getGeneralPrice());
                if(events.get(i).getVenueType().equals("Arena")){
                    bw.write(","+events.get(i).getArena().getNameAvenue()+
                            ","+events.get(i).getArena().getPctSeats()+
                            ","+events.get(i).getArena().getNameType()+
                            ","+events.get(i).getArena().getCapacity()+
                            ","+events.get(i).getArena().getCost()+
                            ","+events.get(i).getArena().getVipPct()+
                            ","+events.get(i).getArena().getGoldPct()+
                            ","+events.get(i).getArena().getSilverPct()+
                            ","+events.get(i).getArena().getBronzePct()+
                            ","+events.get(i).getArena().getGeneralPct()+
                            ","+events.get(i).getArena().getReservedPct()+
                            ","+events.get(i).getArena().getFireWorks()+
                            ","+events.get(i).getArena().getFireWorksCost()+
                            ","+events.get(i).getArena().getTotalSeatsVip()+
                            ","+events.get(i).getArena().getTotalSeatsGold()+
                            ","+events.get(i).getArena().getTotalSeatsSilver()+
                            ","+events.get(i).getArena().getTotalSeatsBronze()+
                            ","+events.get(i).getArena().getTotalSeatsGeneral()+
                            ","+events.get(i).getArena().getTotalVipRevenue()+ /////
                            ","+events.get(i).getArena().getTotalGoldRevenue()+
                            ","+events.get(i).getArena().getTotalSilverRevenue()+
                            ","+events.get(i).getArena().getTotalBronzeRevenue()+
                            ","+events.get(i).getArena().getTotalGeneralRevenue());
                }
                bw.newLine();
            }//special
        }
        bw.close();
    }//generateCSV method
}