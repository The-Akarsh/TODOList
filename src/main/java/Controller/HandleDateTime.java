package Controller;

import java.util.Date;
import java.text.SimpleDateFormat;

public class HandleDateTime {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("h:mm a dd/MM/yyyy");
    public static String rawTimeTOSring(Date date, String time){
        String newDate = dateFormat.format(date);
        return time + " " + newDate;
    }
//    public static String timeToString() {}
}
