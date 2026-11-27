package com.fluxit.project_multitasking.processing;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fluxit.project_multitasking.model.Customer;

public class SequentialProcessor {

    public Map<String, Long> groupByCountry(List<Customer> customers) {
        Map<String, Long> result = new HashMap<>();

        for (Customer customer : customers) {
            String country = customer.country();

            if (country == null || country.isBlank()) {
                country = "Desconocido";
            } else {
                country = country.trim();
            }

            result.merge(country, 1L, Long::sum);
        }

        return result;
    }

    public Map<String, Long> topCountries(Map<String, Long> countryCounts, int limit) {

        if (limit < 1) {
            throw new IllegalArgumentException("El límite debe ser mayor que cero");
        }

        return countryCounts.entrySet()
                            .stream()
                            .sorted(
                                Map.Entry.<String, Long>comparingByValue()
                                         .reversed()
                                         .thenComparing(Map.Entry.comparingByKey())
                            )
                            .limit(limit)
                            .collect(
                                java.util.stream.Collectors.toMap(
                                    Map.Entry::getKey,
                                    Map.Entry::getValue,
                                    (a, b) -> a,
                                    java.util.LinkedHashMap::new)
                            );
    }

    public Map<YearMonth, Long> groupBySubscriptionMonth(List<Customer> customers) {
        Map<YearMonth, Long> result = new HashMap<>();

        for (Customer customer : customers) {
            if (customer.subscriptionDate() == null) {
                continue;
            }

            YearMonth yearMonth = YearMonth.from(customer.subscriptionDate());

            result.merge(yearMonth, 1L, Long::sum);
        }

        return result;
    }

}
