package io.bayrktlihn.util;

import java.math.BigDecimal;

public interface InterestRate {
    BigDecimal getDaily();

    BigDecimal getYearly();

    BigDecimal getMonthly();
}