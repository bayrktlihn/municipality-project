package io.bayrktlihn.util.date;

import io.bayrktlihn.util.date.model.DayMonth;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LocalDates {

    private LocalDates() throws InstantiationException {
        throw new InstantiationException();
    }


    public static LocalDate now() {
        return LocalDate.now();
    }

    public static LocalDate firstDayOfYear(int year) {
        return LocalDate.of(year, 1, 1);
    }

    public static LocalDate lastDayOfYear(int year) {
        return LocalDate.of(year, 12, 31);
    }

    public static int minYear() {
        return Year.MIN_VALUE;
    }

    public static int maxYear() {
        return Year.MAX_VALUE;
    }

    public static boolean afterOrIsEqual(LocalDate localDate, LocalDate otherLocalDate) {
        return localDate.isEqual(otherLocalDate) || localDate.isAfter(otherLocalDate);
    }

    public static boolean beforeOrIsEqual(LocalDate localDate, LocalDate otherLocalDate) {
        return localDate.isEqual(otherLocalDate) || localDate.isBefore(otherLocalDate);
    }

    public static LocalDate create(int year, int month, int dayOfMonth) {
        int minYear = minYear();
        if (maxYear() < year || minYear - 1 > year) {
            throw new IllegalArgumentException(String.format("Year must be between %s and %s", minYear, Year.MAX_VALUE));
        }

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(String.format("Month must be between %s and %s", 1, 12));
        }

        int maximumDayOfMonth = maximumDayOfMonth(year, month);

        if (dayOfMonth < 1 || dayOfMonth > maximumDayOfMonth) {
            throw new IllegalArgumentException(
                    String.format("Day of month must be between %s and %s", 1, maximumDayOfMonth));
        }
        return LocalDate.of(year, month, dayOfMonth);
    }

    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    private static List<Integer> thirtyOneDaysMonths() {
        return Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    }

    private static List<Integer> thirtyDaysMonths() {
        return Arrays.asList(4, 6, 9, 11);
    }

    public static int maximumDayOfMonth(int year, int month) {
        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        }


        List<Integer> thirtyOneDaysMonths = thirtyOneDaysMonths();
        if (thirtyOneDaysMonths.stream().anyMatch(item -> item.equals(month))) {
            return 31;
        }

        List<Integer> thirtyDaysMonths = thirtyDaysMonths();
        if (thirtyDaysMonths.stream().anyMatch(item -> item.equals(month))) {
            return 30;
        }

        throw new RuntimeException();

    }

    public static Period period(LocalDate from, LocalDate to) {
        return Period.between(from, to);
    }

    public static LocalDate firstDayOfMonth(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate firstDayOfMonth(LocalDate localDate) {
        return firstDayOfMonth(localDate.getYear(), localDate.getMonthValue());
//        return localDate.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate lastDayOfMonth(int year, int month) {
        int maximumDayOfMonth = maximumDayOfMonth(year, month);
        return LocalDate.of(year, month, maximumDayOfMonth);
//        LocalDate firstDayOfMonth = firstDayOfMonth(year, month);
//        return firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDate lastDayOfMonth(LocalDate localDate) {
        return lastDayOfMonth(localDate.getYear(), localDate.getMonthValue());
//        return localDate.with(TemporalAdjusters.lastDayOfMonth());
    }


    public static LocalDate nextSaturday(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
            return localDate.plus(7, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            return localDate.plus(6, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.MONDAY)) {
            return localDate.plus(5, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.TUESDAY)) {
            return localDate.plus(4, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.WEDNESDAY)) {
            return localDate.plus(3, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.THURSDAY)) {
            return localDate.plus(2, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
            return localDate.plus(1, ChronoUnit.DAYS);
        }


        throw new IllegalArgumentException();
    }


    public static LocalDate previousSaturday(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();


        if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            return localDate.minus(1, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.MONDAY)) {
            return localDate.minus(2, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.TUESDAY)) {
            return localDate.minus(3, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.WEDNESDAY)) {
            return localDate.minus(4, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.THURSDAY)) {
            return localDate.minus(5, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
            return localDate.minus(6, ChronoUnit.DAYS);
        }

        if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
            return localDate.minus(7, ChronoUnit.DAYS);
        }


        throw new IllegalArgumentException();
    }

    public static long numberOfDays(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new RuntimeException();
        }
        return ChronoUnit.DAYS.between(from, to);
    }

    public static boolean isWeekend(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfWeek.equals(DayOfWeek.MONDAY);
    }

    public static long workDays(LocalDate from, LocalDate to) {
        return workDays(from, to, true, true, new ArrayList<>(), new ArrayList<>());
    }

    public static long workDays(LocalDate from, LocalDate to, List<LocalDate> holidays) {
        return workDays(from, to, true, true, new ArrayList<>(), holidays);
    }

    public static long workDays(LocalDate from, LocalDate to, List<DayMonth> holidayInEveryYear, List<LocalDate> holidays) {
        return workDays(from, to, true, true, holidayInEveryYear, holidays);
    }

    public static long workDays(LocalDate from, LocalDate to, boolean weekendIsHoliday, List<DayMonth> holidayInEveryYear, List<LocalDate> holidays) {
        return workDays(from, to, weekendIsHoliday, weekendIsHoliday, holidayInEveryYear, holidays);
    }

    public static long workDays(LocalDate from, LocalDate to, boolean weekendIsHoliday) {
        return workDays(from, to, weekendIsHoliday, weekendIsHoliday, new ArrayList<>(), new ArrayList<>());
    }

    public static long workDays(LocalDate from, LocalDate to, boolean isSaturdayIsHoliday, boolean isSundayIsHoliday, List<DayMonth> holidaysInEveryYear, List<LocalDate> holidays) {

        int days = 0;

        LocalDate current = from;
        do {
            current = nextWorkDate(current, isSaturdayIsHoliday, isSundayIsHoliday, holidaysInEveryYear, holidays);
            if (current.compareTo(to) <= 0) {
                days++;
            }
        } while (current.isBefore(to));

        return days;
    }

    public static LocalDate nextWorkDate(LocalDate localDate, boolean isSaturdayIsHoliday, boolean isSundayIsHoliday, List<DayMonth> holidaysInEveryYear) {
        return nextWorkDate(localDate, isSaturdayIsHoliday, isSundayIsHoliday, holidaysInEveryYear, new ArrayList<>());
    }

    public static LocalDate nextWorkDate(LocalDate localDate, boolean isSaturdayIsHoliday, boolean isSundayIsHoliday, List<DayMonth> holidaysInEveryYear, List<LocalDate> holidays) {
        LocalDate oneAddedDays = localDate.plus(1, ChronoUnit.DAYS);
        return currentDateOrNextWorkDate(oneAddedDays, isSaturdayIsHoliday, isSundayIsHoliday, holidaysInEveryYear, holidays);
    }

    public static LocalDate currentDateOrNextWorkDate(LocalDate localDate, boolean weekendIsHoliday, List<DayMonth> holidaysInEveryYear) {
        return currentDateOrNextWorkDate(localDate, weekendIsHoliday, weekendIsHoliday, holidaysInEveryYear, new ArrayList<>());
    }

    public static LocalDate currentDateOrNextWorkDate(LocalDate localDate, boolean weekendIsHoliday, List<DayMonth> holidaysInEveryYear, List<LocalDate> holidays) {
        return currentDateOrNextWorkDate(localDate, weekendIsHoliday, weekendIsHoliday, holidaysInEveryYear, holidays);
    }

    public static LocalDate currentDateOrNextWorkDate(LocalDate localDate, List<DayMonth> holidaysInEveryYear) {
        return currentDateOrNextWorkDate(localDate, true, true, holidaysInEveryYear, new ArrayList<>());
    }

    public static LocalDate currentDateOrNextWorkDate(LocalDate localDate) {
        return currentDateOrNextWorkDate(localDate, true, true, new ArrayList<>(), new ArrayList<>());
    }

    public static LocalDate currentDateOrNextWorkDate(LocalDate localDate, boolean weekendIsHoliday) {
        return currentDateOrNextWorkDate(localDate, weekendIsHoliday, weekendIsHoliday, new ArrayList<>(), new ArrayList<>());
    }

    public static LocalDate currentDateOrNextWorkDate(LocalDate localDate, boolean isSaturdayIsHoliday, boolean isSundayIsHoliday, List<DayMonth> holidaysInEveryYear) {
        return currentDateOrNextWorkDate(localDate, isSaturdayIsHoliday, isSundayIsHoliday, holidaysInEveryYear, new ArrayList<>());
    }

    public static LocalDate currentDateOrNextWorkDate(LocalDate localDate, boolean isSaturdayIsHoliday, boolean isSundayIsHoliday, List<DayMonth> holidaysInEveryYear, List<LocalDate> holidays) {
        if (isHoliday(localDate, isSaturdayIsHoliday, isSundayIsHoliday, holidaysInEveryYear, holidays)) {
            LocalDate addedOneDay = localDate.plus(1, ChronoUnit.DAYS);
            return currentDateOrNextWorkDate(addedOneDay, isSaturdayIsHoliday, isSundayIsHoliday, holidaysInEveryYear, holidays);
        }
        return localDate;
    }

    public static LocalDate from(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


    private static boolean isHoliday(LocalDate localDate, boolean isSaturdayIsHoliday, boolean isSundayIsHoliday, List<DayMonth> holidaysInYear, List<LocalDate> holidays) {

        DayOfWeek dayOfWeek = DayOfWeek.of(localDate.get(ChronoField.DAY_OF_WEEK));

        if (isSaturdayIsHoliday && dayOfWeek.equals(DayOfWeek.SATURDAY)) {
            return true;
        }

        if (isSundayIsHoliday && dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            return true;
        }


        int month = localDate.get(ChronoField.MONTH_OF_YEAR);
        int dayOfMonth = localDate.get(ChronoField.DAY_OF_MONTH);

        if (holidaysInYear.stream().anyMatch(holidaysInYearItem -> holidaysInYearItem.getDayOfMonth() == dayOfMonth && holidaysInYearItem.getMonth() == month)) {
            return true;
        }

        return holidays.stream().anyMatch(holiday -> holiday != null && holiday.compareTo(localDate) == 0);
    }

    public static List<DayMonth> turkishHolidays() {
        List<DayMonth> holidays = new ArrayList<>();
        holidays.add(new DayMonth(1, 1));
        holidays.add(new DayMonth(15, 7));
        holidays.add(new DayMonth(23, 4));
        holidays.add(new DayMonth(19, 5));
        holidays.add(new DayMonth(1, 5));
        holidays.add(new DayMonth(29, 10));
        holidays.add(new DayMonth(30, 8));
        return holidays;
    }


}