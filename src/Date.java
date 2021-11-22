import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Date implements DateChecker {
    private String date;


    public Date(String dateFormat) {
        this.date = dateFormat;
    }



    @Override
    public boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat(this.date);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    /**
     * Method provided by Victor Herrera
     * Method designed to check in user input of date is in the past
     * @return
     *
     * **/

    public  boolean isDatePast(final String date, final String dateFormat) {

        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate inputDate = LocalDate.parse(date, dtf);

        return inputDate.isBefore(localDate);
    }

}