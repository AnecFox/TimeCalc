package com.anec;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeCalculator {

    public static int[] getTimeInterval(String firstTime, String secondTime) {
        if (checkInvalidTimeFormat(firstTime) || checkInvalidTimeFormat(secondTime)) {
            throw new DateTimeException("Invalid time format");
        }

        String[] firstTimeArray = firstTime.split(":");
        int firstTimeHours = Integer.parseInt(firstTimeArray[0]);
        int firstTimeMinutes = Integer.parseInt(firstTimeArray[1]);

        LocalDateTime firstDateTime = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(), firstTimeHours, firstTimeMinutes);

        String[] secondTimeArray = secondTime.split(":");
        int secondTimeHours = Integer.parseInt(secondTimeArray[0]);
        int secondTimeMinutes = Integer.parseInt(secondTimeArray[1]);

        LocalDateTime secondDateTime = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),
                (secondTimeHours < firstTimeHours ? LocalDateTime.now().getDayOfMonth() + 1 :
                        LocalDateTime.now().getDayOfMonth()), secondTimeHours, secondTimeMinutes);

        int[] result = new int[2];
        result[1] = (int) firstDateTime.until(secondDateTime, ChronoUnit.MINUTES);
        result = correctTime(result[0], result[1]);

        return result;
    }

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

    private static int[] correctTime(int hours, int minutes) {
        while (minutes >= 60) {
            hours++;
            minutes -= 60;
        }

        int[] result = new int[2];
        result[0] = hours;
        result[1] = minutes;

        return result;
    }

    // parameter words usage example: TimeCalculator.getHourWords() or TimeCalculator.getMinuteWords()
    public static String generateWord(int number, String[] words) {
        char[] numberSymbols = String.valueOf(number).toCharArray();
        int lastNumberSymbol = Integer.parseInt(String.valueOf(numberSymbols[numberSymbols.length - 1]));

        if (String.valueOf(number).endsWith("1") && number != 11) {
            return words[0];
        } else if (lastNumberSymbol <= 4 && lastNumberSymbol >= 2 && !(number > 11 && number < 15)) {
            return words[1];
        } else {
            return words[2];
        }
    }

    public static String[] getHourWords() {
        String[] words = new String[3];
        words[0] = "час";
        words[1] = "часа";
        words[2] = "часов";

        return words;
    }

    public static String[] getMinuteWords() {
        String[] words = new String[3];
        words[0] = "минута";
        words[1] = "минуты";
        words[2] = "минут";

        return words;
    }
}
