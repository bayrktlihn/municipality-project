package io.bayrktlihn.util.date.model;

import io.bayrktlihn.util.date.LocalDates;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
public class LocalDateFromTo {
    private LocalDate from;
    private LocalDate to;

    public LocalDateFromTo(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public static LocalDateFromTo createWithFrom(LocalDate from) {
        return create(from, LocalDate.MAX);
    }

    public static LocalDateFromTo createWithTo(LocalDate to) {
        return create(LocalDate.MIN, to);
    }


    public static LocalDateFromTo create(LocalDate from, LocalDate to) {
        from = from == null ? LocalDate.MIN : from;
        to = to == null ? LocalDate.MAX : to;
        Period period = Period.between(from, to);
        int total = period.getMonths() + period.getYears() + period.getDays();
        if (total < 1) {
            throw new IllegalArgumentException();
        }
        return new LocalDateFromTo(from, to);
    }


    public static LocalDateFromTo create(int fromYear, LocalDate to) {
        LocalDate from = LocalDates.create(fromYear, 1, 1);
        to = to == null ? LocalDate.MAX : to;
        return create(from, to);
    }

    public static LocalDateFromTo create(LocalDate from, int toYear) {
        LocalDate to = LocalDates.create(toYear, 12, 31);
        from = from == null ? LocalDate.MIN : from;
        return create(from, to);
    }

    public static LocalDateFromTo create(int fromYear, int toYear) {
        LocalDate from = LocalDates.create(fromYear, 1, 1);
        LocalDate to = LocalDates.create(toYear, 12, 31);
        return create(from, to);
    }

    @Override
    public String toString() {
        return from.toString() + "->" + to.toString();
    }
}