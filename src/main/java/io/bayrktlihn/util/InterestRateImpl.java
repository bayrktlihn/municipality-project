package io.bayrktlihn.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InterestRateImpl implements InterestRate {
    private final BigDecimal yearly;
    private final BigDecimal monthly;
    private final BigDecimal daily;

    public InterestRateImpl(BigDecimal yearly, BigDecimal monthly, BigDecimal daily) {
        this.yearly = yearly;
        this.monthly = monthly;
        this.daily = daily;
    }


    public static InterestRate createWithDailyInterestRate(BigDecimal dailyInterestRate) {
        dailyInterestRate = dailyInterestRate.setScale(15, RoundingMode.HALF_UP);
        BigDecimal yearlyInterestRate = dailyInterestRate.multiply(new BigDecimal("365"));
        return createWithYearlyInterestRate(yearlyInterestRate);
    }


    public static InterestRate createWithMonthlyInterestRate(BigDecimal monthlyInterestRate) {
        monthlyInterestRate = monthlyInterestRate.setScale(15, RoundingMode.HALF_UP);
        BigDecimal yearlyInterestRate = monthlyInterestRate.multiply(new BigDecimal("12"));
        return createWithYearlyInterestRate(yearlyInterestRate);
    }

    public static InterestRate createWithYearlyInterestRate(BigDecimal yearlyInterestRate) {
        yearlyInterestRate = yearlyInterestRate.setScale(15, RoundingMode.HALF_UP);
        BigDecimal monthlyInterestRate = yearlyInterestRate.divide(new BigDecimal("12"), 15, RoundingMode.HALF_UP);
        BigDecimal dailyInsterestRate = yearlyInterestRate.divide(new BigDecimal("365"), 15, RoundingMode.HALF_UP);

        InterestRateImpl interestRateImpl = new InterestRateImpl(yearlyInterestRate, monthlyInterestRate, dailyInsterestRate);
        return interestRateImpl;
    }

    @Override
    public BigDecimal getDaily() {
        return daily;
    }

    @Override
    public BigDecimal getYearly() {
        return yearly;
    }

    @Override
    public BigDecimal getMonthly() {
        return monthly;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof InterestRateImpl) {
            InterestRateImpl interestRateImpl = (InterestRateImpl) obj;
            boolean equalsDailyInterestRate = interestRateImpl.getDaily().setScale(2, RoundingMode.HALF_UP).equals(getDaily().setScale(2, RoundingMode.HALF_UP));
            boolean equalsYearlyInterestRate = interestRateImpl.getYearly().setScale(2, RoundingMode.HALF_UP).equals(getYearly().setScale(2, RoundingMode.HALF_UP));
            boolean equalsMonthlyInterestRate = interestRateImpl.getMonthly().setScale(2, RoundingMode.HALF_UP).equals(getMonthly().setScale(2, RoundingMode.HALF_UP));
            return equalsDailyInterestRate && equalsYearlyInterestRate && equalsMonthlyInterestRate;
        }
        return false;
    }


}