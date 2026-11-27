package com.fluxit.project_multitasking.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.fluxit.project_multitasking.dto.ProcessingResult;
import com.fluxit.project_multitasking.model.Customer;
import com.fluxit.project_multitasking.processing.ConcurrentProcessor;
import com.fluxit.project_multitasking.processing.SequentialProcessor;
import com.fluxit.project_multitasking.reader.CustomerCsvReader;
import com.fluxit.project_multitasking.service.CustomerService;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerCsvReader customerCsvReader;

    private final SequentialProcessor sequentialProcessor = new SequentialProcessor();

    //availableProcessors(): Devuelve el número de procesadores disponibles para la máquina virtual Java.
    private final ConcurrentProcessor concurrentProcessor = new ConcurrentProcessor(Runtime.getRuntime().availableProcessors());

    @Getter
    private List<Customer> customers;

    public CustomerServiceImpl(CustomerCsvReader customerCsvReader) {
        this.customerCsvReader = customerCsvReader;
    }

    @Override
    public Map<String, Long> groupByCountrySequential() {
        return sequentialProcessor.groupByCountry(customers);
    }

    @PostConstruct
    @Override
    public void loadCustomers() throws IOException {
        customers = List.copyOf(customerCsvReader.readCustomers());
    }

    public Map<String, Long> groupByCountryConcurrent() throws InterruptedException, ExecutionException {
        return concurrentProcessor.groupByCountry(customers);
    }

    @Override
    public ProcessingResult<Map<String, Long>> groupByCountrySequentialWithMetrics() {

        long start = System.nanoTime();

        Map<String, Long> result = sequentialProcessor.groupByCountry(customers);

        long elapsed = System.nanoTime() - start;

        return new ProcessingResult<>("SEQUENTIAL", elapsed / 1_000_000, customers.size(), result);
    }

    @Override
    public ProcessingResult<Map<String, Long>> groupByCountryConcurrentWithMetrics() throws InterruptedException, ExecutionException {

        long start = System.nanoTime();

        Map<String, Long> result = concurrentProcessor.groupByCountry(customers);

        long elapsed = System.nanoTime() - start;

        return new ProcessingResult<>("CONCURRENT", elapsed / 1_000_000, customers.size(), result);
    }

    @Override
    public Map<String, Long> topCountriesSequential(int limit) {
        Map<String, Long> counts = sequentialProcessor.groupByCountry(customers);

        return sequentialProcessor.topCountries(counts, limit);
    }

    @Override
    public Map<String, Long> topCountriesConcurrent(int limit) throws InterruptedException, ExecutionException {

        Map<String, Long> counts = concurrentProcessor.groupByCountry(customers);

        return concurrentProcessor.topCountries(counts, limit);
    }

    @Override
    public ProcessingResult<Map<String, Long>> topCountriesSequentialWithMetrics(int limit) {

        long start = System.nanoTime();

        Map<String, Long> counts = sequentialProcessor.groupByCountry(customers);

        Map<String, Long> result = sequentialProcessor.topCountries(counts, limit);

        long elapsed = System.nanoTime() - start;

        return new ProcessingResult<>("SEQUENTIAL", elapsed / 1_000_000, customers.size(), result);
    }

    @Override
    public ProcessingResult<Map<String, Long>> topCountriesConcurrentWithMetrics(int limit) throws InterruptedException, ExecutionException {

        long start = System.nanoTime();

        Map<String, Long> counts = concurrentProcessor.groupByCountry(customers);

        Map<String, Long> result = concurrentProcessor.topCountries(counts, limit);

        long elapsed = System.nanoTime() - start;

        return new ProcessingResult<>("CONCURRENT", elapsed / 1_000_000, customers.size(), result);
    }


}
