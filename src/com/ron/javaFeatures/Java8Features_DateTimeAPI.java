package com.ron.javaFeatures;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class Java8Features_DateTimeAPI {

    public static void main(String[] args) {
        Utils.print("Features Introduced in Java 8: DateTime API");
        Java8Features_DateTimeAPI j8 = new Java8Features_DateTimeAPI();
        j8.introduction();
        j8.localDate();
        j8.localTime();
        j8.localDateTime();
        j8.instantDurationPeriod();
        j8.utilities();
        j8.formatting();
        j8.parsing();
    }

    public void introduction() {
        Utils.newMethod("introduction");

        // Prior to Java 8 dealing with dates, times and datetimes was not painless:
        // Inconsistency: classes in java.util, java.sql, java.text
        // 2 Date classes: java.util.Date and java.sql.Date  [also Calendar class]
        // All classes mutable and not threadsafe
        // Originally no support for internationalisation and timezones. Added later but still problematic.
        // Single class for Date, Time, DateTime; No classes for duration, period, instant.

        // Java 8 Data Time API
        // based on JodaTime
        // Intuitive: very easy to learn and use
        // Threadsafe: all classes are immutable
        // Clarity: separate classes for Date, Time, DateTime, instant, Period, Duration, etc
        // Consistency: methods perform the same action in all the classes (eg now(), parse(), format())
        // Utility operations for common tasks (eg plus, minus, format, parse, etc)

        // Package: java.time
        //
        // Main Classes
        //
        // LocalTime, LocalDate, LocalDateTime
        //          date/time clock representation (including timezones for humans)
        // Instant
        //          'technical' timestamp representation (nanoseconds) [always UTC]
        // Duration
        //          measured in seconds/nanoseconds, can be -ve
        // Period
        //          for defining an amount of time with years/months/days,etc.
        //          usually inexact in terms of milliseconds
    }

    public void localDate() {
        Utils.newMethod("localDate");

        // LocalDate is an immutable class that represents a Date (yyyy-MM-dd).

        // now()
        LocalDate today = LocalDate.now();
        Utils.print("today =", today);

        // Creating LocalDate by providing input arguments
        LocalDate independenceDay = LocalDate.of(1776, Month.JULY, 4);
        Utils.print("independenceDay =", independenceDay);

        // bad data causes a RuntimeException
        try {
            LocalDate badDate = LocalDate.of(2000, Month.FEBRUARY, 30);
        } catch (DateTimeException e) {
            Utils.printerr("No bad dates with Java 8:", e.getMessage());  // unless you date a software engineer
        }

        // Timezones
        LocalDate todayInOz = LocalDate.now(ZoneId.of("Australia/Sydney"));
        Utils.print("today In Oz =", todayInOz);
        Utils.print("today In Tokyo =", LocalDate.now(ZoneId.of("Asia/Tokyo")));
        Utils.print("today In NY=", LocalDate.now(ZoneId.of("America/New_York")));
        Utils.print("today In Anchorage =", LocalDate.now(ZoneId.of("America/Anchorage")));

        // Misc
        LocalDate day0 = LocalDate.ofEpochDay(0);
        Utils.print("beginning of epoch =", day0);

        int day = 100;
        int year = today.getYear();
        LocalDate hundredDayOfThisYear = LocalDate.ofYearDay(year, day);
        Utils.print("day", day, "of", year, "is", hundredDayOfThisYear);

        Utils.print("today is", today.getDayOfWeek(), ": day", today.getDayOfYear(),
                "of the year", today.getYear(), "and day", today.getDayOfMonth(),
                "of the month of", today.getMonth());
        Utils.print("today is", today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
    }

    public void localTime() {
        Utils.newMethod("localTime");

        // LocalTime is an immutable class that represents a Time (hh:mm:ss.zzz).

        // now()
        LocalTime timeNow = LocalTime.now();
        Utils.print("timeNow =", timeNow);

        // Creating LocalTime by providing input arguments
        LocalTime specificTime = LocalTime.of(11, 59, 59, 987654321);
        Utils.print("Specific Time =", specificTime);
        LocalTime noon = LocalTime.of(12, 0);
        Utils.print("noon =", noon);

        // bad data causes a RuntimeException
        try {
            LocalTime badTime = LocalTime.of(24, 60);
        } catch (DateTimeException e) {
            Utils.printerr("No bad times with Java 8:", e.getMessage());  // except for Monday mornings
        }

        // Timezones
        LocalTime timeInOz = LocalTime.now(ZoneId.of("Australia/Sydney"));
        Utils.print("timeInOz =", timeInOz);
        Utils.print("timeInTokyo =", LocalTime.now(ZoneId.of("Asia/Tokyo")));
        Utils.print("timeInNYC =", LocalTime.now(ZoneId.of("America/New_York")));
        Utils.print("timeInLA =", LocalTime.now(ZoneId.of("America/Los_Angeles")));

        int second = 50_000;
        LocalTime specificSecondTime = LocalTime.ofSecondOfDay(second);
        Utils.print("second", second, "of the day =", specificSecondTime);
    }

    public void localDateTime() {
        Utils.newMethod("localDateTime");

        // LocalTime is an immutable class that represents a Date and Time (yyyy-MM-dd-hh:mm:ss.zzz).

        // now()
        LocalDateTime now = LocalDateTime.now();
        Utils.print("now =", now);

        // Creating LocalDateTime by providing LocalDate and LocalTime objects
        LocalDateTime localDT = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        Utils.print("localDT =", localDT);

        // Creating LocalDateTime by providing input arguments
        LocalDateTime specificDateTime = LocalDateTime.of(2017, Month.DECEMBER, 25, 11, 59, 30);
        Utils.print("specificDateTime =", specificDateTime);

        // bad data causes a RuntimeException
        try {
            LocalDateTime badDateTime = LocalDateTime.of(2017, Month.FEBRUARY, 29, 24, -1, 99);
        } catch (DateTimeException e) {
            Utils.printerr("Invalid LocalDateTime:", e.getMessage());
        }

        // TimeZones
        LocalDateTime dateTimeInOz = LocalDateTime.now(ZoneId.of("Australia/Sydney"));
        Utils.print("dateTime In Oz =", dateTimeInOz);
        Utils.print("dateTime In Tokyo =", LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
        Utils.print("dateTime In NYC =", LocalDateTime.now(ZoneId.of("America/New_York")));
        Utils.print("dateTime In LA =", LocalDateTime.now(ZoneId.of("America/Los_Angeles")));

        // misc
        long second = 1_000_000_000_000L;
        LocalDateTime dateFromBase = LocalDateTime.ofEpochSecond(second, 0, ZoneOffset.UTC);
        Utils.print("Second", second, "from start of epoch =", dateFromBase);

        Utils.print("now is", now.getMinute(), "past", now.getHour(), "on", now.getDayOfWeek(),
                ", day", now.getDayOfYear(), "of the year", now.getYear(),
                "and day", now.getDayOfMonth(), "of the month of", now.getMonth(),
                "or", now.toLocalDate(), now.toLocalTime(), "or", now.toString());
    }

    public void instantDurationPeriod() {
        Utils.newMethod("instantDurationPeriod");

        // now()
        Instant now = Instant.now();
        Utils.print("now =", now);

        // Instant - always in UTC (unlike LocalDateTime). This is handy for most business logic.
        long secondsLater = 10_000_000L;
        long totalSinceEpoch = now.toEpochMilli() / 1000 + secondsLater;
        Instant laterInstant = Instant.ofEpochSecond(totalSinceEpoch);
        Utils.print("laterInstant =", laterInstant);
        Utils.print("laterInstant =", now.plusSeconds(secondsLater));

        // Duration - these are exact and are measured in milliseconds
        Duration duration = Duration.ofDays(7);
        Utils.print("7 day duration =", duration);
        Utils.print("14 day duration =", duration.plus(duration));
        Utils.print("1.5 day duration =", duration.minusDays(6).plusHours(12));
        Utils.print("6 hour duration =", duration.minusDays(6).minusHours(18));
        Utils.print("5 mins 30 secs duration =", duration.ofMinutes(5).plusSeconds(30));

        // Period - these are inexact in terms of milliseconds
        // eg 5 years; 2 hours and 4 minutes; 17 weeks
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfYear = today.with(TemporalAdjusters.lastDayOfYear());

        Period period = today.until(lastDayOfYear);
        Utils.print("period =", period);
        Utils.print("Remaining in this year:", period.getMonths(), "month(s) and", period.getDays(), "days");

        Period randomPeriod = period
                .minusMonths(Utils.getRandomInt(2, 5))
                .minusDays(Utils.getRandomInt(7, 28))
                .plusYears(Utils.getRandomInt(0, 12));
        Utils.print("random period =", randomPeriod, ": remaining months =", randomPeriod.toTotalMonths());
    }

    public void utilities() {
        Utils.newMethod("utilities");

        LocalDate today = LocalDate.now();

        Utils.print("Year", today.getYear(), "is a leap year:", today.isLeapYear());

        // isBefore() and isAfter()
        LocalDate java9ReleaseDay = LocalDate.of(2017, 9, 21);
        LocalDate java11ReleaseDay = LocalDate.of(2018, 9, 25);
        Utils.print("java9ReleaseDay is", java9ReleaseDay.isBefore(java11ReleaseDay) ? "before" : "after", "java11ReleaseDay");
        Utils.print("Today is", today.isBefore(java11ReleaseDay) ? "before" : "after",
                "and not", today.isAfter(java11ReleaseDay) ? "before" : "after", java11ReleaseDay);

        // Create LocalDateTime from LocalDate
        LocalDateTime noonToday = today.atTime(12, 0);
        Utils.print("noonToday =", noonToday);

        // plus and minus
        Utils.print("21 days after today:", today.plusDays(21));
        Utils.print("13 weeks after today:", today.plusWeeks(13));
        Utils.print("18 months after today:", today.plusMonths(18));
        Utils.print("21 days before today:", today.minusDays(21));
        Utils.print("13 weeks before today:", today.minusWeeks(13));
        Utils.print("21 months before today:", today.minusMonths(21));

        // Temporal adjusters
        LocalDate lastDayOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        Utils.print("Last day of this year =", lastDayOfYear);
        Utils.print("First day of this month =", today.with(TemporalAdjusters.firstDayOfMonth()));
        Utils.print("First Friday of this month =", today.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY)));
        Utils.print("Next Friday =", today.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)));
        Utils.print("Previous/Same Friday =", today.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY)));
    }

    public void formatting() {
        Utils.newMethod("formatting");

        // Time format examples
        LocalTime time = LocalTime.now();
        Utils.print("LocalTime format 0:", time); // default format
        Utils.print("LocalTime format 1:", time.format(DateTimeFormatter.ofPattern("hh,mm,ss")));
        Utils.print("LocalTime format 2:", time.format(DateTimeFormatter.ISO_LOCAL_TIME));

        // Date format examples
        LocalDate date = LocalDate.now();
        Utils.print("LocalDate format 0:", date); // default format
        Utils.print("LocalDate format 1:", date.format(DateTimeFormatter.ofPattern("d / MMM / uuuu")));
        Utils.print("LocalDate format 2:", date.format(DateTimeFormatter.BASIC_ISO_DATE));
        Utils.print("LocalDate format 3:", date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
        Utils.print("LocalDate format 4:", date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        Utils.print("LocalDate format 5:", date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        Utils.print("LocalDate format 6:", date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));

        // DateTime format examples
        LocalDateTime dateTime = LocalDateTime.now();
        Utils.print("LocalDateTime format 0:", dateTime); // default format
        Utils.print("LocalDateTime format 1:", dateTime.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy HH::mm::ss")));
        Utils.print("LocalDateTime format 2:", dateTime.format(DateTimeFormatter.BASIC_ISO_DATE));
        Utils.print("LocalDateTime format 3:", dateTime.format(DateTimeFormatter.ISO_DATE_TIME));
        Utils.print("LocalDateTime format 4:", dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        Utils.print("LocalDateTime format 5:", dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
    }

    public void parsing() {
        Utils.newMethod("parsing");

        String timeStr = "11/59/30";
        String dateStr = "29:Feb:2000";
        String dateTimeStr = dateStr + "-" + timeStr;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH/mm/ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd:MMM:yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd:MMM:yyyy-HH/mm/ss");

        Utils.print("parsed time:", LocalTime.parse(timeStr, timeFormatter));
        Utils.print("parsed date:", LocalDate.parse(dateStr, dateFormatter));
        Utils.print("parsed dateTime:", LocalDateTime.parse(dateTimeStr, dateTimeFormatter));
    }
}
