import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RunTicket {
    public static void main(String[] args){
        ArrayList event = readEventList();
        for(int i =0;i< event.size();i++){
            System.out.println(event);
        }

    }
    public static ArrayList<Event> readEventList() {
        String fileName = "src/EventListPA4FINAL.csv";
        ArrayList<Event> eventsList = new ArrayList<Event>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = "";
            line = br.readLine();
            String[] firstLine = line.split(",");
            HashMap<String, Integer> MapHeader = new HashMap<String, Integer>();// String -> String 0
            for (int i = 0; i < firstLine.length; i++) {
                MapHeader.put(firstLine[i], i);
                System.out.println(i);
            }

            while((line =br.readLine())!=null){
                String [] index = line.split(",");
                String eventTypeIn = index[MapHeader.get("Event Type")];
                int pctSeatsUnIn = Integer.parseInt(index[MapHeader.get("Pct Seats Unavailable")]);
                double goldPriceIn = Double.parseDouble(index[MapHeader.get("Gold Price")]);
                String nameIn = index[MapHeader.get("Name")];
                int capacityIn = Integer.parseInt(index[MapHeader.get("Capacity")]);
                String timeIn = index[MapHeader.get("Time")];
                int resExtPctIn = Integer.parseInt(index[MapHeader.get("Reserved Extra Pct")]);
                String venTypeIn = index[MapHeader.get("Venue Type")];
                String dateIn = index[MapHeader.get("Date")];
                double vipPriceIn = Double.parseDouble(index[MapHeader.get("VIP Price")]);
                int genAdPctIn = Integer.parseInt(index[MapHeader.get("General Admission Pct")]);
                int brzPctIn = Integer.parseInt(index[MapHeader.get("Bronze Pct")]);
                double brzPriceIn = Double.parseDouble(index[MapHeader.get("Bronze Price")]);
                int silverPctIn = Integer.parseInt(index[MapHeader.get("Silver Pct")]);
                double genAdmPriceIn = Double.parseDouble(index[MapHeader.get("General Admission Price")]);
                double silverPriceIn = Double.parseDouble(index[MapHeader.get("Silver Price")]);
                String fireworkPlanedIn = index[MapHeader.get("Fireworks Planned")];
                String venNameIn = index[MapHeader.get("Venue Name")];
                int evenIdIn = Integer.parseInt(index[MapHeader.get("Event ID")]);
                int costIn = Integer.parseInt(index[MapHeader.get("Cost")]);
                int vipPctIn = Integer.parseInt(index[MapHeader.get("VIP Pct")]);
                int goldPctIn = Integer.parseInt(index[MapHeader.get("Gold Pct")]);
                int fireworkCost = Integer.parseInt(index[MapHeader.get("Fireworks Cost")]);
              //  Stadium sunBowl= new Stadium (venNameIn,venTypeIn,pctSeatsUnIn,capacityIn,costIn,vipPctIn,goldPctIn,silverPctIn,brzPctIn,genAdPctIn,resExtPctIn,fireworkPlanedIn,fireworkCost);
                //Event football = new Sport(evenIdIn,eventTypeIn,nameIn,dateIn,timeIn,vipPriceIn,goldPriceIn,silverPriceIn,brzPriceIn,genAdmPriceIn,sunBowl);
                Event football = new Sport(goldPctIn,eventTypeIn,goldPriceIn,nameIn,capacityIn,timeIn,resExtPctIn,venTypeIn,vipPriceIn,brzPriceIn,silverPctIn,genAdmPriceIn,fireworkPlanedIn,venNameIn,evenIdIn,pctSeatsUnIn,costIn,vipPctIn,dateIn,goldPctIn,fireworkCost,silverPriceIn,brzPctIn);
                //        super(generalPct,evenType,goldPrice,name,capacity,time,reservedExtraPct,venueType,vipPrice,bronzePrice,silverPct,generalAdmission,fireworksPlanned,venueName,eventId,pctSeats,cost,vipPct,date,goldPct,fireCost,silverPrice,bronzePct);
                eventsList.add(football);

            }
        }catch(IOException e){
            System.out.println("The file entered does not exist pelase try again.");
            System.exit(0);
        }
        return eventsList;
    }


}