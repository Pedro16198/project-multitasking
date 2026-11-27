package com.fluxit.project_multitasking.service;

import java.io.IOException;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.fluxit.project_multitasking.dto.ProcessingResult;
import com.fluxit.project_multitasking.model.Customer;

public interface CustomerService {

    void loadCustomers() throws IOException;

    Map<String, Long> groupByCountrySequential();

    Map<String, Long> groupByCountryConcurrent() throws InterruptedException, ExecutionException;

    List<Customer> getCustomers();

    ProcessingResult<Map<String, Long>> groupByCountrySequentialWithMetrics();

    ProcessingResult<Map<String, Long>> groupByCountryConcurrentWithMetrics() throws InterruptedException, ExecutionException;

    Map<String, Long> topCountriesSequential(int limit);

    Map<String, Long> topCountriesConcurrent(int limit) throws InterruptedException, ExecutionException;

    ProcessingResult<Map<String, Long>> topCountriesSequentialWithMetrics(int limit);

    ProcessingResult<Map<String, Long>> topCountriesConcurrentWithMetrics(int limit) throws InterruptedException, ExecutionException;

    Map<YearMonth, Long> groupBySubscriptionMonthSequential();

    Map<YearMonth, Long> groupBySubscriptionMonthConcurrent() throws InterruptedException, ExecutionException;

    ProcessingResult<Map<YearMonth, Long>> groupBySubscriptionMonthSequentialWithMetrics();

    ProcessingResult<Map<YearMonth, Long>> groupBySubscriptionMonthConcurrentWithMetrics() throws InterruptedException, ExecutionException;
}
