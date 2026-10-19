package com.fluxit.project_multitasking.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

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

}
