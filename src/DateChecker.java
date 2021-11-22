import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public interface DateChecker {
    boolean isValid(String dateStr);

    boolean isDatePast(final String date, final String dateFormat);

    }


