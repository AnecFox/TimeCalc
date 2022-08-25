package com.anec;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeCalculating {

    private static boolean checkInvalidTimeFormat(String time) {
        Pattern patternFirstCheck = Pattern.compile("^\\d\\d:\\d\\d$");
        Matcher timeFirstCheck = patternFirstCheck.matcher(time);

        if (!timeFirstCheck.matches()) {
            Pattern patternSecondCheck = Pattern.compile("^\\d:\\d\\d$");
            Matcher timeSecondCheck = patternSecondCheck.matcher(time);

            if (!timeSecondCheck.matches()) {
                return true;
            }
        }

        String[] timeArray = time.split(":");

        int hours = Integer.parseInt(timeArray[0]);
        int minutes = Integer.parseInt(timeArray[1]);

        if (hours > 23) {
            return true;
        } else return minutes > 59;
    }

    public static int[] getTimeInterval(String firstTime, String secondTime) {
        if (checkInvalidTimeFormat(firstTime) || checkInvalidTimeFormat(secondTime)) {
            throw new DateTimeException("Invalid time format");
        }

        int firstTimeHours = Integer.parseInt(firstTime.split(":")[0]);
        int firstTimeMinutes = Integer.parseInt(firstTime.split(":")[1]);

        LocalDateTime t1 = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(), firstTimeHours, firstTimeMinutes);

        int secondTimeHours = Integer.parseInt(secondTime.split(":")[0]);
        int secondTimeMinutes = Integer.parseInt(secondTime.split(":")[1]);

        LocalDateTime t2 = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),
                (secondTimeHours < firstTimeHours ? LocalDateTime.now().getDayOfMonth() + 1 :
                        LocalDateTime.now().getDayOfMonth()), secondTimeHours, secondTimeMinutes);

        int[] result = new int[2];
        result[1] = (int) t1.until(t2, ChronoUnit.MINUTES);

        while (result[1] >= 60) {
            result[0]++;
            result[1] -= 60;
        }

        return result;
    }
}
