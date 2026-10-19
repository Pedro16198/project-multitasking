package com.fluxit.project_multitasking.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.fluxit.project_multitasking.model.Customer;

public interface CustomerService {

    void loadCustomers() throws IOException;

    Map<String, Long> groupByCountrySequential();

    Map<String, Long> groupByCountryConcurrent() throws InterruptedException, ExecutionException;

    List<Customer> getCustomers();

}
