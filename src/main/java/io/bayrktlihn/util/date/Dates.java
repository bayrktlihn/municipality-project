package io.bayrktlihn.util.date;

import io.bayrktlihn.util.date.model.DayMonth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

public class Dates {

    public static final int COUNT_OF_DAY_IN_NORMAL_YEAR = 365;
    public static final int COUNT_OF_DAY_IN_LEAP_YEAR = COUNT_OF_DAY_IN_NORMAL_YEAR + 1;

    private Dates() throws InstantiationException {
        throw new InstantiationException();
    }

    public static Date now() {
        return new Date();
    }

    public static Date startOfToday() {
        Date now = now();
        return startOfDay(now);
    }

    public static Date endOfToday() {
        Date now = now();
        return endOfDay(now);
    }

    public static int currentYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now());
        return calendar.get(Calendar.YEAR);
    }

    public static Date startOfFirstDayOfYear(int year) {
        Date startOfToday = startOfToday();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startOfToday);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }

    public static Date endOfLastDayOfYear(int year) {
        Date endOfToday = endOfToday();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endOfToday);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);

        return calendar.getTime();
    }

    public static Date startOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date endOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    public static <T> List<T> createPeriodItems(Date from, Date to, long periodValue, TemporalUnit periodUnit, long delayValue, TemporalUnit delayUnit, BiFunction<Date, Date, T> toBeGenerateObject) {
        List<T> result = new ArrayList<>();

        Instant periodEndInstant = from.toInstant().plus(periodValue, periodUnit);

        Date periodStart = from;
        Date periodEnd = Date.from(periodEndInstant);

        while (periodEnd.compareTo(to) <= 0) {
            T t = toBeGenerateObject.apply(periodStart, periodEnd);
            result.add(t);

            Instant periodStartInstant = periodEnd.toInstant().plus(delayValue, delayUnit);
            periodEndInstant = periodStartInstant.plus(periodValue, periodUnit);

            periodStart = Date.from(periodStartInstant);
            periodEnd = Date.from(periodEndInstant);
        }

        return result;


    }

    public static List<Integer> years(int begin, int end) {
        return years(begin, end, true);
    }

    public static List<Integer> years(int begin, int end, boolean asc) {
        List<Integer> years = new ArrayList<>();
        for (int i = begin; i <= end; i++) {
            years.add(i);
        }

        if (!asc) {
            Collections.reverse(years);
        }

        return years;
    }

    public static int maxYear() {
        Calendar calendar = Calendar.getInstance();

        return calendar.getActualMaximum(Calendar.YEAR);
    }

    public static int minYear() {
        Calendar calendar = Calendar.getInstance();

        return calendar.getActualMinimum(Calendar.YEAR);
    }

    public static Date create(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        int minYear = minYear();

        int maximumYear = calendar.getActualMaximum(Calendar.YEAR);
        if (year < minYear - 1 || year > maximumYear) {
            throw new IllegalArgumentException(String.format("Year must be between %s and %s", minYear, maximumYear));
        }
        calendar.set(Calendar.YEAR, year);

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(String.format("Month must be between %s and %s", 1, 12));
        }
        calendar.set(Calendar.MONTH, month - 1);

        int maximumDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (dayOfMonth < 1 || dayOfMonth > maximumDayOfMonth) {
            throw new IllegalArgumentException(
                    String.format("Day of month must be between %s and %s", 1, maximumDayOfMonth));
        }
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return calendar.getTime();

    }

    public static Period period(Date from, Date to) {
        Instant fromInstant = from.toInstant();
        Instant toInstant = to.toInstant();

        LocalDateTime fromLocalDateTime = LocalDateTime.ofInstant(fromInstant, ZoneId.systemDefault());
        LocalDateTime toLocalDateTime = LocalDateTime.ofInstant(toInstant, ZoneId.systemDefault());

        return Period.between(fromLocalDateTime.toLocalDate(), toLocalDateTime.toLocalDate());
    }

    public static int numberOfDaysInMonth(int year, int month) {
        Date date = startOfFirstDayOfMonth(year, month);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int numberOfDaysInYear(int year) {

        if (isLeapYear(year)) {
            return COUNT_OF_DAY_IN_LEAP_YEAR;
        }

        return COUNT_OF_DAY_IN_NORMAL_YEAR;

    }

    public static Date startOfFirstDayOfMonth(int year, int month) {
        return createStartOfDay(year, month, 1);
    }

    public static Date endOfLastDayOfMonth(int year, int month) {
        return createStartOfDay(year, month, 1);
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

    private static List<Integer> thirtyOneDaysMonths() {
        return Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    }

    private static List<Integer> thirtyDaysMonths() {
        return Arrays.asList(4, 6, 9, 11);
    }


    public static Date currentDateOrNextWorkDate(Date date, List<DayMonth> holidaysInEveryYear) {
        return currentDateOrNextWorkDate(date, true, true, holidaysInEveryYear);
    }

    public static Date currentDateOrNextWorkDate(Date date, boolean weekendIsHoliday) {
        return currentDateOrNextWorkDate(date, true, true, new ArrayList<>());
    }

    public static Date currentDateOrNextWorkDate(Date date, boolean weekendIsHoliday, List<DayMonth> holidaysInEveryYear) {
        return currentDateOrNextWorkDate(date, weekendIsHoliday, weekendIsHoliday, holidaysInEveryYear);
    }

    public static Date currentDateOrNextWorkDate(Date date) {
        return currentDateOrNextWorkDate(date, true, true, new ArrayList<>());
    }

    public static Date currentDateOrNextWorkDate(Date date, boolean isSaturdayIsHoliday, boolean isSundayIsHoliday, List<DayMonth> holidaysInEveryYear) {
        if (isHoliday(date, isSaturdayIsHoliday, isSundayIsHoliday, holidaysInEveryYear)) {
            Instant addedOneDay = date.toInstant().plus(1, ChronoUnit.DAYS);
            return currentDateOrNextWorkDate(Date.from(addedOneDay), isSaturdayIsHoliday, isSundayIsHoliday, holidaysInEveryYear);
        }
        return date;
    }


    public static Date createStartOfDay(int year, int month, int dayOfMonth) {
        Date date = create(year, month, dayOfMonth);
        return startOfDay(date);
    }

    public static Date createEndOfDay(int year, int month, int dayOfMonth) {
        Date date = create(year, month, dayOfMonth);
        return endOfDay(date);
    }

    public static String toString(Date date, String pattern) {
        DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }


    public static boolean equalsWithoutTime(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(d1);
        c2.setTime(d2);

        int c1Year = c1.get(Calendar.YEAR);
        int c2Year = c2.get(Calendar.YEAR);
        if (c1Year != c2Year) {
            return false;
        }

        int c1Month = c1.get(Calendar.MONTH);
        int c2Month = c2.get(Calendar.MONTH);
        if (c1Month != c2Month) {
            return false;
        }

        int c1DayOfMonth = c1.get(Calendar.DAY_OF_MONTH);
        int c2DayOfMonth = c2.get(Calendar.DAY_OF_MONTH);
        return c1DayOfMonth == c2DayOfMonth;
    }

    private static boolean isHoliday(Date date, boolean isSaturdayIsHoliday, boolean isSundayIsHoliday, List<DayMonth> holidaysInYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (isSaturdayIsHoliday && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return true;
        }

        if (isSundayIsHoliday && calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }

        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return holidaysInYear.stream().anyMatch(holidaysInYearItem -> holidaysInYearItem.getDayOfMonth() == dayOfMonth && holidaysInYearItem.getMonth() == month);
    }

    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

}