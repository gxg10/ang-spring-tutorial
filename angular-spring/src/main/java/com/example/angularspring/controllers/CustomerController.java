package com.example.angularspring.controllers;

import com.example.angularspring.models.Customer;
import com.example.angularspring.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "customers/age/{age}")
    public List<Customer> findByAge(@PathVariable int age) {

        List<Customer> customers = customerRepository.findByAge(age);
        return customers;
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id,
                                                   @RequestBody Customer customer) {
        System.out.println("update custoemr with id "+ id);
        Optional<Customer> customerData = customerRepository.findById(id);

        if (customerData.isPresent()) {
            Customer _customer = customerData.get();
            _customer.setName(customer.getName());
            _customer.setAge(customer.getAge());
            _customer.setActive(customer.isActive());

            return new ResponseEntity<>(customerRepository.save(_customer),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
