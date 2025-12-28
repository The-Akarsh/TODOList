package Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.SimpleDateFormat;

public class HandleDateTime {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("h:mm a dd/MM/yyyy");
    public static String rawTimeTOSring(Date date, String time){
        String newDate = dateFormat.format(date);
        return time + " " + newDate;
    }
    /**
     * Takes String data type as input and returns LocalDateTime data type in dateTimeFormat(self defined) format */
    public static LocalDateTime stringTOLocalDateTime(String stringTime){
        return LocalDateTime.parse(stringTime,dateTimeFormat);
    }
//    public static String timeToString() {}
}
