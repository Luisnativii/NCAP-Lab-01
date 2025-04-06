package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {
    private static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";



    public static Date parseDateTime(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        return sdf.parse(dateStr);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        return sdf.format(date);
    }
}
