package com.example.angularspring.controllers;

import com.example.angularspring.models.Customer;
import com.example.angularspring.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        System.out.println("get all customers....");

        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);

        return customers;
    }

    @PostMapping(value = "/customers/create")
    public Customer postCustomer(@RequestBody Customer customer) {
        Customer _customer = customerRepository.save(new Customer(customer.getName(), customer.getAge()));

        return _customer;
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {
        System.out.println("delete customer with id ="+id);

        customerRepository.deleteById(id);

        return new ResponseEntity<>("customer has beend deleted", HttpStatus.OK);
    }
}
