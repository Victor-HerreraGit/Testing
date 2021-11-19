import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

}