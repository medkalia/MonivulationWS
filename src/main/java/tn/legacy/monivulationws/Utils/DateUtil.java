package tn.legacy.monivulationws.Utils;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    static final String TIME_ZONE_DEFAULT = "Africa/Tunis";
    static final String FORMAT_DEFAULT = "HH:mm:ss";

    public static LocalTime getCurrentTime(){
        return LocalTime.now();
    }

    public static String getCurrentTimeFormatted(String format){
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DEFAULT);
        if (!format.equals("") && !format.equals(FORMAT_DEFAULT))
            formatter = DateTimeFormatter.ofPattern(format);
        return time.format(formatter);
    }

    public static LocalTime getCurrentTimeWithTimeZone(String timeZone) {
        ZoneId zoneId = ZoneId.of(TIME_ZONE_DEFAULT);
        if (!timeZone.equals("") && !timeZone.equals(TIME_ZONE_DEFAULT))
            zoneId = ZoneId.of(timeZone);
        return LocalTime.now(zoneId);
    }

    public static String getCurrentTimeFormattedWithTimeZone(String format, String timeZone) {
        ZoneId zoneId = ZoneId.of(TIME_ZONE_DEFAULT);
        if (!timeZone.equals("") && !timeZone.equals(TIME_ZONE_DEFAULT))
            zoneId = ZoneId.of(timeZone);
        LocalTime localTime = LocalTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DEFAULT);
        if (!format.equals("") && !format.equals(FORMAT_DEFAULT))
            formatter = DateTimeFormatter.ofPattern(format);
        String formattedTime = localTime.format(formatter);
        return formattedTime;
    }

    public static LocalTime getCurrentTimeWithOffset(String offset) {
        ZoneId zoneId = ZoneId.of(TIME_ZONE_DEFAULT);
        if (offset.equals("")){
            System.err.println("Offset given epmty ! returning date without offset");
        }else{
            ZoneOffset zoneOffset = ZoneOffset.of(offset);
            zoneId=ZoneId.ofOffset("UTC", zoneOffset);
        }
        return LocalTime.now(zoneId);
    }

     /*public static String getCurrentTimeFormatted(String format) {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        if (!format.equals(""))
            dateFormat = new SimpleDateFormat(format);
        String formattedDate=dateFormat.format(date);
        return formattedDate ;
    }

    public static Date getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        return date;
    }*/
}
