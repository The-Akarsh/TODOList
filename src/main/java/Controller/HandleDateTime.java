package Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.SimpleDateFormat;
/** Created in <code>Model.HandleDateTime</code>. Class contains tools for handling date & time of data type LocalDateTime and String.
 *  dateTimeFormatter variable is used to define pattern */
public class HandleDateTime {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("h:mm a dd/MM/yyyy");
    /** Created in <code>Model.HandleDateTime</code> class.Takes Date data type and String time as input and
     *  returns String in dateTimeFormat(self defined) format */
    public static String rawTimeTOSring(Date date, String time){
        String newDate = dateFormat.format(date);
        return time + " " + newDate;
    }
    /**Takes String data type as input and returns LocalDateTime data type in dateTimeFormat(self defined) format */
    public static LocalDateTime stringTOLocalDateTime(String stringTime){
        return LocalDateTime.parse(stringTime,dateTimeFormat);
    }
//    public static String timeToString() {}
}
