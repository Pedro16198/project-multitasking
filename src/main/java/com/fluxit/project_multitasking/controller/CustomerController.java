package com.fluxit.project_multitasking.controller;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fluxit.project_multitasking.dto.ProcessingResult;
import com.fluxit.project_multitasking.model.Customer;
import com.fluxit.project_multitasking.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        List<Customer> customers = customerService.getCustomers();

        return Map.of("status", "OK", "totalCustomers", customers.size());
    }

    @GetMapping("/countries/sequential")
    public Map<String, Long> groupByCountrySequential() {
        return customerService.groupByCountrySequential();
    }

    @GetMapping("/countries/concurrent")
    public Map<String, Long> groupByCountryConcurrent() throws InterruptedException, ExecutionException {
        return customerService.groupByCountryConcurrent();
    }

    @GetMapping("/countries/sequential/metrics")
    public ProcessingResult<Map<String, Long>> groupByCountrySequentialWithMetrics() {

        return customerService.groupByCountrySequentialWithMetrics();
    }

    @GetMapping("/countries/concurrent/metrics")
    public ProcessingResult<Map<String, Long>> groupByCountryConcurrentWithMetrics() throws InterruptedException, ExecutionException {

        return customerService.groupByCountryConcurrentWithMetrics();
    }

    @GetMapping("/countries/top/sequential")
    public Map<String, Long> topCountriesSequential(@RequestParam(defaultValue = "5") int limit) {
        return customerService.topCountriesSequential(limit);
    }

    @GetMapping("/countries/top/concurrent")
    public Map<String, Long> topCountriesConcurrent(@RequestParam(defaultValue = "5") int limit) throws InterruptedException, ExecutionException {
        return customerService.topCountriesConcurrent(limit);
    }

    @GetMapping("/countries/top/sequential/metrics")
    public ProcessingResult<Map<String, Long>> topCountriesSequentialWithMetrics(@RequestParam(defaultValue = "5") int limit) {
        return customerService.topCountriesSequentialWithMetrics(limit);
    }

    @GetMapping("/countries/top/concurrent/metrics")
    public ProcessingResult<Map<String, Long>> topCountriesConcurrentWithMetrics(@RequestParam(defaultValue = "5") int limit)
        throws InterruptedException, ExecutionException {

        return customerService.topCountriesConcurrentWithMetrics(limit);
    }

    @GetMapping("/subscriptions/monthly/sequential")
    public Map<YearMonth, Long> groupBySubscriptionMonthSequential() {
        return customerService.groupBySubscriptionMonthSequential();
    }

    @GetMapping("/subscriptions/monthly/concurrent")
    public Map<YearMonth, Long> groupBySubscriptionMonthConcurrent() throws InterruptedException, ExecutionException {
        return customerService.groupBySubscriptionMonthConcurrent();
    }

    @GetMapping("/subscriptions/monthly/sequential/metrics")
    public ProcessingResult<Map<YearMonth, Long>> groupBySubscriptionMonthSequentialWithMetrics() {
        return customerService.groupBySubscriptionMonthSequentialWithMetrics();
    }

    @GetMapping("/subscriptions/monthly/concurrent/metrics")
    public ProcessingResult<Map<YearMonth, Long>> groupBySubscriptionMonthConcurrentWithMetrics() throws InterruptedException, ExecutionException {
        return customerService.groupBySubscriptionMonthConcurrentWithMetrics();
    }

}