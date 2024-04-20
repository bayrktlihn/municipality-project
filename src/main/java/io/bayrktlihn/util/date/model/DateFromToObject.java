package io.bayrktlihn.util.date.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DateFromToObject<T> {
    private Date from;
    private Date to;
    private T object;

    public DateFromToObject(Date from, Date to, T object) {
        this.from = from;
        this.to = to;
        this.object = object;
    }
}