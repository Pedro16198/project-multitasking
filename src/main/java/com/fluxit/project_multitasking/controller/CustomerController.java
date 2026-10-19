package com.fluxit.project_multitasking.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}