package io.bayrktlihn.service;

import io.bayrktlihn.entity.Accrual;
import io.bayrktlihn.util.InterestRate;
import io.bayrktlihn.util.InterestRateV2Impl;
import io.bayrktlihn.util.LocalDateFromToObject;
import io.bayrktlihn.util.date.LocalDates;
import io.bayrktlihn.util.date.model.LocalDateFromTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AccrualService {


    public BigDecimal calculateTotalAmount(Accrual accrual) {
        LocalDate start = LocalDates.from(accrual.getPaymentDueDate());
        LocalDate today = LocalDates.now();

        List<LocalDateFromToObject<InterestRate>> allInterestRate = getLocalDateFromToObjects();
        List<LocalDateFromToObject<InterestRate>> interestRateInRange = interestRateInRange(allInterestRate, start, today);


        BigDecimal total = new BigDecimal("0");
        for (int i = 0; i < interestRateInRange.size(); i++) {
            LocalDateFromToObject<InterestRate> localDateFromToObject = interestRateInRange.get(i);
            LocalDateFromTo fromTo = localDateFromToObject.getFromTo();

            boolean firstItem = i == 0;
            LocalDate from = !firstItem ? fromTo.getFrom().minusDays(1) : fromTo.getFrom();

            InterestCalculator interestCalculator = new InterestCalculator(localDateFromToObject.getObject(), from, fromTo.getTo());
            BigDecimal calculatedInsterestValue = interestCalculator.calculate(accrual.getAmount());

            log.info(String.format("%s %s %s", interestCalculator.getFrom(), interestCalculator.getTo(), calculatedInsterestValue));

            total = total.add(calculatedInsterestValue);
        }


        return total;


    }

    private static List<LocalDateFromToObject<InterestRate>> interestRateInRange(List<LocalDateFromToObject<InterestRate>> allInterestRate, LocalDate start, LocalDate end) {
        List<LocalDateFromToObject<InterestRate>> collect = allInterestRate.stream().filter(item -> {
            LocalDateFromTo fromTo = item.getFromTo();
            LocalDate to = fromTo.getTo() == null ? LocalDate.MAX : fromTo.getTo();
            LocalDate from = fromTo.getFrom() == null ? LocalDate.MIN : fromTo.getFrom();

            if (LocalDates.beforeOrIsEqual(from, start) && LocalDates.afterOrIsEqual(to, start)) {
                return true;
            }

            if (from.isAfter(start) && LocalDates.beforeOrIsEqual(to, end)) {
                return true;
            }


            if (LocalDates.beforeOrIsEqual(from, end) && (to.isAfter(end))) {
                return true;
            }
            return false;
        }).sorted(LocalDateFromToObject::compareWithLocalDateTo).collect(Collectors.toList());

        int firstIndex = 0;
        int lastIndex = collect.size() - 1;

        collect.get(firstIndex).getFromTo().setFrom(start);
        collect.get(lastIndex).getFromTo().setTo(end);

        return collect;
    }

    private static List<LocalDateFromToObject<InterestRate>> getLocalDateFromToObjects() {
        List<LocalDateFromToObject<InterestRate>> fromToList = new ArrayList<>();

        fromToList.add(LocalDateFromToObject.createWithTo(LocalDates.create(1980, 12, 31), InterestRateV2Impl.createWithMonthlyInterestRate("0")));

        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1981, 1, 1), LocalDates.create(1981, 1, 31), InterestRateV2Impl.createWithMonthlyInterestRate("10")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1981, 2, 1), LocalDates.create(1984, 2, 29), InterestRateV2Impl.createWithMonthlyInterestRate("3")));

        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1984, 3, 1), LocalDates.create(1984, 3, 31), InterestRateV2Impl.createWithMonthlyInterestRate("10")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1984, 4, 1), LocalDates.create(1985, 8, 31), InterestRateV2Impl.createWithMonthlyInterestRate("4")));

        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1985, 9, 1), LocalDates.create(1985, 9, 30), InterestRateV2Impl.createWithMonthlyInterestRate("10")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1985, 10, 1), LocalDates.create(1986, 2, 28), InterestRateV2Impl.createWithMonthlyInterestRate("7")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1986, 3, 1), LocalDates.create(1988, 5, 31), InterestRateV2Impl.createWithMonthlyInterestRate("5")));

        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1988, 6, 1), LocalDates.create(1988, 8, 31), InterestRateV2Impl.createWithMonthlyInterestRate("10")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1988, 9, 1), LocalDates.create(1988, 11, 30), InterestRateV2Impl.createWithMonthlyInterestRate("8")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1988, 12, 1), LocalDates.create(1988, 12, 31), InterestRateV2Impl.createWithMonthlyInterestRate("6")));

        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1989, 1, 1), LocalDates.create(1989, 4, 30), InterestRateV2Impl.createWithMonthlyInterestRate("10")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1989, 5, 1), LocalDates.create(1989, 12, 31), InterestRateV2Impl.createWithMonthlyInterestRate("7")));


        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1990, 1, 1), LocalDates.create(1993, 12, 29), InterestRateV2Impl.createWithMonthlyInterestRate("7")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1993, 12, 30), LocalDates.create(1994, 3, 7), InterestRateV2Impl.createWithMonthlyInterestRate("9")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1994, 3, 8), LocalDates.create(1995, 8, 30), InterestRateV2Impl.createWithMonthlyInterestRate("12")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1995, 8, 31), LocalDates.create(1996, 1, 31), InterestRateV2Impl.createWithMonthlyInterestRate("10")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1996, 2, 1), LocalDates.create(1998, 7, 8), InterestRateV2Impl.createWithMonthlyInterestRate("15")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(1998, 7, 9), LocalDates.create(2000, 1, 19), InterestRateV2Impl.createWithMonthlyInterestRate("12")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2000, 1, 20), LocalDates.create(2000, 12, 1), InterestRateV2Impl.createWithMonthlyInterestRate("6")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2000, 12, 2), LocalDates.create(2001, 3, 28), InterestRateV2Impl.createWithMonthlyInterestRate("5")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2001, 3, 29), LocalDates.create(2002, 1, 30), InterestRateV2Impl.createWithMonthlyInterestRate("10")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2002, 1, 31), LocalDates.create(2003, 11, 11), InterestRateV2Impl.createWithMonthlyInterestRate("7")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2003, 11, 12), LocalDates.create(2004, 1, 1), InterestRateV2Impl.createWithMonthlyInterestRate("4")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2004, 1, 2), LocalDates.create(2005, 3, 1), InterestRateV2Impl.createWithMonthlyInterestRate("4")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2005, 3, 2), LocalDates.create(2006, 4, 20), InterestRateV2Impl.createWithMonthlyInterestRate("3")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2006, 4, 21), LocalDates.create(2009, 11, 18), InterestRateV2Impl.createWithMonthlyInterestRate("2.5")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2009, 11, 19), LocalDates.create(2010, 10, 18), InterestRateV2Impl.createWithMonthlyInterestRate("1.95")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2010, 10, 19), LocalDates.create(2018, 9, 4), InterestRateV2Impl.createWithMonthlyInterestRate("1.4")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2018, 9, 5), LocalDates.create(2019, 6, 30), InterestRateV2Impl.createWithMonthlyInterestRate("2")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2019, 7, 1), LocalDates.create(2019, 10, 1), InterestRateV2Impl.createWithMonthlyInterestRate("2.5")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2019, 10, 2), LocalDates.create(2019, 12, 29), InterestRateV2Impl.createWithMonthlyInterestRate("2")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2019, 12, 30), LocalDates.create(2022, 7, 20), InterestRateV2Impl.createWithMonthlyInterestRate("1.6")));
        fromToList.add(LocalDateFromToObject.create(LocalDates.create(2022, 7, 21), LocalDates.create(2023, 11, 13), InterestRateV2Impl.createWithMonthlyInterestRate("2.5")));
        fromToList.add(LocalDateFromToObject.createWithFrom(LocalDates.create(2023, 11, 14), InterestRateV2Impl.createWithMonthlyInterestRate("3.5")));
        return fromToList;
    }

}
