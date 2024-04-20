package io.bayrktlihn.util;

import io.bayrktlihn.util.date.LocalDates;
import io.bayrktlihn.util.date.model.LocalDateFromTo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalDateFromToObject<T> {
    private LocalDateFromTo fromTo;
    private T object;


    public static int compareWithLocalDateTo(LocalDateFromToObject o1, LocalDateFromToObject o2) {
        LocalDate to1 = o1.getFromTo().getTo() == null ? LocalDate.MAX : o1.getFromTo().getTo();
        LocalDate to2 = o2.getFromTo().getTo() == null ? LocalDate.MAX : o2.getFromTo().getTo();
        return to1.compareTo(to2);

    }

    public static <T> LocalDateFromToObject<T> createWithFrom(LocalDate from, T t){
        LocalDateFromTo withTo = LocalDateFromTo.createWithFrom(from);
        return new LocalDateFromToObject<>(withTo, t);
    }

    public static <T> LocalDateFromToObject<T> createWithTo(LocalDate to, T t){
        LocalDateFromTo withTo = LocalDateFromTo.createWithTo(to);
        return new LocalDateFromToObject<>(withTo, t);
    }


    public static <T> LocalDateFromToObject<T> create(LocalDate start, LocalDate end, T t){
        LocalDateFromTo localDateFromTo = LocalDateFromTo.create(start, end);
        LocalDateFromToObject<T> localDateFromToObject = new LocalDateFromToObject<>(localDateFromTo, t);
        return localDateFromToObject;
    }


}