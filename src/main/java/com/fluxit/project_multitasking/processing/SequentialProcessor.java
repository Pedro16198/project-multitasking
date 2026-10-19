package com.fluxit.project_multitasking.processing;

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

}
