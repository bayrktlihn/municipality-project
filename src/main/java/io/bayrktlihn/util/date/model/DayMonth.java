package io.bayrktlihn.util.date.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DayMonth {
    private int dayOfMonth;
    private int month;

    public DayMonth(int dayOfMonth, int month) {
        this.dayOfMonth = dayOfMonth;
        this.month = month;
    }
}