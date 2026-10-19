package com.fluxit.project_multitasking.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fluxit.project_multitasking.model.Customer;

public class ConcurrentProcessor {

    private final int threadCount;

    public ConcurrentProcessor(int threadCount) {
        if (threadCount < 1) {
            throw new IllegalArgumentException("La cantidad de hilos debe ser mayor que cero");
        }

        this.threadCount = threadCount;
    }

    public Map<String, Long> groupByCountry(List<Customer> customers) throws InterruptedException, ExecutionException {

        if (customers.isEmpty()) {
            return Map.of();
        }

        int taskCount = Math.min(threadCount, customers.size());
        int chunkSize = (customers.size() + taskCount - 1) / taskCount;

        List<Future<Map<String, Long>>> futures = new ArrayList<>();

        try (ExecutorService executor = Executors.newFixedThreadPool(taskCount)) {

            for (int i = 0; i < taskCount; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, customers.size());

                if (start >= end) {
                    break;
                }

                List<Customer> chunk = customers.subList(start, end);

                Future<Map<String, Long>> future = executor.submit(() -> countCountries(chunk));

                futures.add(future);
            }

            Map<String, Long> result = new HashMap<>();

            for (Future<Map<String, Long>> future : futures) {
                Map<String, Long> partialResult = future.get();

                partialResult.forEach((country, count) -> result.merge(country, count, Long::sum));
            }

            return result;
        }
    }

    private Map<String, Long> countCountries(List<Customer> customers) {
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
