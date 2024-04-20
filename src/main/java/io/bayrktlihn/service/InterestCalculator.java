package io.bayrktlihn.service;

import io.bayrktlihn.util.InterestRate;
import io.bayrktlihn.util.date.LocalDates;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Getter
public class InterestCalculator {

    private final InterestRate interestRate;
    private final LocalDate from;
    private final LocalDate to;

    public InterestCalculator(InterestRate interestRate, LocalDate from, LocalDate to) {
        this.interestRate = interestRate;
        this.from = from;
        this.to = to;
    }

    public BigDecimal calculate(BigDecimal amount) {
        Period period = LocalDates.period(from, to);
        int totalYear = period.getYears();
        int totalMonth = period.getMonths();
        int totalDay = period.getDays();

        BigDecimal totalYearlyRate = interestRate.getYearly().multiply(new BigDecimal(String.valueOf(totalYear)));
        BigDecimal totalMonthlyRate = interestRate.getMonthly().multiply(new BigDecimal(String.valueOf(totalMonth)));
        BigDecimal totalDailyRate = interestRate.getDaily().multiply(new BigDecimal(String.valueOf(totalDay)));

        BigDecimal totalMonthlyInterest = amount.multiply(totalMonthlyRate.divide(new BigDecimal("100"), 15, BigDecimal.ROUND_UP));
        BigDecimal totalYearlyInterest = amount.multiply(totalYearlyRate.divide(new BigDecimal("100"), 15, BigDecimal.ROUND_UP));
        BigDecimal totalDailyInterest = amount.multiply(totalDailyRate.divide(new BigDecimal("100"), 15, BigDecimal.ROUND_UP));

        return totalMonthlyInterest.add(totalDailyInterest).add(totalYearlyInterest);
    }
}