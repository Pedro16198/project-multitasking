package com.fluxit.project_multitasking.reader;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fluxit.project_multitasking.model.Customer;

@Component
public class CustomerCsvReader {

    public List<Customer> readCustomers() throws IOException {
        ClassPathResource resource =
            new ClassPathResource("data/customers.csv");

        CSVFormat format = CSVFormat.DEFAULT.builder()
                                            .setHeader()
                                            .setSkipHeaderRecord(true)
                                            .get();

        List<Customer> customers = new ArrayList<>();

        try (Reader reader = new java.io.InputStreamReader(
            resource.getInputStream(), StandardCharsets.UTF_8);
             CSVParser parser = format.parse(reader)) {

            for (CSVRecord row : parser) {
                Customer customer = new Customer(
                    Integer.parseInt(row.get("Index")),
                    row.get("Customer Id"),
                    row.get("First Name"),
                    row.get("Last Name"),
                    row.get("Company"),
                    row.get("City"),
                    row.get("Country"),
                    row.get("Phone 1"),
                    row.get("Phone 2"),
                    row.get("Email"),
                    LocalDate.parse(row.get("Subscription Date")),
                    row.get("Website")
                );

                customers.add(customer);
            }
        }

        return customers;
    }
}
