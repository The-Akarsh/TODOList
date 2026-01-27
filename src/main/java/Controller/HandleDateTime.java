package Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
/** <b>May soon be decapricated. date time spinner using legacy <code>Date</code> datatype may be replaced by external dependancy</b><br>
 * Created in <code>Model.HandleDateTime</code>. Class contains tools for handling date & time of data type LocalDateTime and String.
 *  dateTimeFormatter variable is used to define pattern */
public class HandleDateTime {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("h:mm a dd/MM/yyyy");
    /** Created in <code>Model.HandleDateTime</code> class.Takes Date data type and String time as input and
     *  returns String in dateTimeFormat(self defined) format */
    @Deprecated
    public static String rawTimeTOSring(Date date, String time){
        String newDate = dateFormat.format(date);
        return time + " " + newDate;
    }
/** Accept LocatDateTime and return String*/
    public static String LocalDateTimeToString(LocalDateTime localDateTime){
        return localDateTime.format(dateTimeFormat);
    }
    /**Takes String data type as input and returns LocalDateTime data type in dateTimeFormat(self defined) format */
    @Deprecated
    public static LocalDateTime stringTOLocalDateTime(String stringTime){
        return LocalDateTime.parse(stringTime,dateTimeFormat);
    }

    /**This acceptes a variable of data type LocalDateTime (<code>java.time.LocalDateTime</code>) and
     * returns a Legacy Date (<code>java.util.Date</code>) */
    @Deprecated
    public static Date LocalTOLegacyDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }
}
