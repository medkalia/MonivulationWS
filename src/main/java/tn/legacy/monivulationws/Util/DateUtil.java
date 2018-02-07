package tn.legacy.monivulationws.Util;

import tn.legacy.monivulationws.enumerations.DurationType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {

    public static final String TIME_ZONE_DEFAULT = "Africa/Tunis";
    public static final String FORMAT_DEFAULT = "dd-MM-yyyy HH:mm:ss";
    public static final String FORMAT_DAY_ONLY = "dd-MM-yyyy";
    public static final String FORMAT_TIME_ONLY = "HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT_DEFAULT);

    //**** Return Current Time
    //Without Format
    public static LocalDateTime getCurrentDateTime(){
        return LocalDateTime.now();
    }
    //Default Format
    public static String getCurrentDateTimeFormatted(){
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DEFAULT);
        return time.format(formatter);
    }
    //Specific Format
    public static String getCurrentDateTimeFormatted(String format){
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DEFAULT);
        if (!format.equals("") && !format.equals(FORMAT_DEFAULT))
            formatter = DateTimeFormatter.ofPattern(format);
        return time.format(formatter);
    }

    //**** Return Current Time in specific timezone
    //Without Format
    public static LocalDateTime getCurrentDateTimeWithTimeZone(String timeZone) {
        ZoneId zoneId = ZoneId.of(TIME_ZONE_DEFAULT);
        if (!timeZone.equals("") && !timeZone.equals(TIME_ZONE_DEFAULT))
            zoneId = ZoneId.of(timeZone);
        return LocalDateTime.now(zoneId);
    }
    //Default Format
    public static String getCurrentDateTimeFormattedWithTimeZone(String timeZone) {
        ZoneId zoneId = ZoneId.of(TIME_ZONE_DEFAULT);
        if (!timeZone.equals("") && !timeZone.equals(TIME_ZONE_DEFAULT))
            zoneId = ZoneId.of(timeZone);
        LocalDateTime localTime = LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DEFAULT);
        String formattedTime = localTime.format(formatter);
        return formattedTime;
    }
    //Specific Format
    public static String getCurrentDateTimeFormattedWithTimeZone(String format, String timeZone) {
        ZoneId zoneId = ZoneId.of(TIME_ZONE_DEFAULT);
        if (!timeZone.equals("") && !timeZone.equals(TIME_ZONE_DEFAULT))
            zoneId = ZoneId.of(timeZone);
        LocalDateTime localTime = LocalDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DEFAULT);
        if (!format.equals("") && !format.equals(FORMAT_DEFAULT))
            formatter = DateTimeFormatter.ofPattern(format);
        String formattedTime = localTime.format(formatter);
        return formattedTime;
    }

    //**** Return Current Time with some offset
    public static LocalDateTime getCurrentDateTimeWithOffset(String offset) {
        ZoneId zoneId = ZoneId.of(TIME_ZONE_DEFAULT);
        if (offset.equals("")){
            System.err.println("Offset given epmty ! returning date without offset");
        }else{
            ZoneOffset zoneOffset = ZoneOffset.of(offset);
            zoneId=ZoneId.ofOffset("UTC", zoneOffset);
        }
        return LocalDateTime.now(zoneId);
    }

    //**** Return duration between dates according to the selected durationType
    public static float getDurationBetween (LocalDateTime date1, LocalDateTime date2, DurationType durationType){
        Duration duration = Duration.between(date1, date2);
        float durationInMinutes =  duration.toMinutes();
        float durationInHours =  durationInMinutes / 60;
        float durationInDays = durationInHours / 24 ;
        float durationInMonths = durationInDays / 30;//TODO: fix not accurate

        switch (durationType){
            case Minutes:
                return durationInMinutes;
            case Hours:
                return durationInHours;
            case Days:
                return durationInDays;
            case Months:
                return durationInMonths;
        }

        return 0;
    }

    //**** Parse date
    //Default format
    public static LocalDateTime parseDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DEFAULT);
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateString, formatter);
        return parsedDateTime;
    }
    //One specific format
    public static LocalDateTime parseDate(String dateString, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateString, formatter);
        return parsedDateTime;
    }
    //Multiple specific formats
    public static List<LocalDateTime> parseDate(String dateString, String[] formats){
        List<LocalDateTime> listOfParsedDates = new ArrayList<LocalDateTime>();
        for (String format : formats) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDateTime parsedDateTime = LocalDateTime.parse(dateString, formatter);
            listOfParsedDates.add(parsedDateTime);
        }

        return listOfParsedDates;
    }

    //*** Format Date
    //Default Format
    public static LocalDateTime formatDateTo(LocalDateTime dateToFormat){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DEFAULT);
        return LocalDateTime.parse(dateToFormat.format(formatter), formatter);
    }
    //Specific format
    public static LocalDateTime formatDateTo(LocalDateTime dateToFormat, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateToFormat.format(formatter), formatter);
    }

    //*** Add number of days
    public static LocalDateTime addNumberOfDaysTo (LocalDateTime dateToAddTo, float numberOfDaysToAdd){
        LocalDateTime finalDate = dateToAddTo ;

        int exactNumberOfDayToAdd = (int)numberOfDaysToAdd;
        finalDate = finalDate.plusDays(exactNumberOfDayToAdd);

        float numberOfHoursTodAdd = (numberOfDaysToAdd-exactNumberOfDayToAdd) * 24 ;
        int exactNumberOfHoursToAdd = (int)numberOfHoursTodAdd;
        finalDate = finalDate.plusHours(exactNumberOfHoursToAdd);

        float numberOfMinutesToAdd = (numberOfHoursTodAdd-exactNumberOfHoursToAdd) * 60 ;
        int exactNumberOfMinutesToAdd = (int)numberOfMinutesToAdd;
        finalDate = finalDate.plusMinutes(exactNumberOfMinutesToAdd);

        return finalDate ;
    }


}
