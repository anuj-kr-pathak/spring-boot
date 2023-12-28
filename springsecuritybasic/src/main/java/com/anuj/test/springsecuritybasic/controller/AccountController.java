package com.anuj.test.springsecuritybasic.controller;

import com.anuj.test.springsecuritybasic.model.Accounts;
import com.anuj.test.springsecuritybasic.model.Customer;
import com.anuj.test.springsecuritybasic.repository.AccountsRepository;
import com.anuj.test.springsecuritybasic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class AccountController {
    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    CustomerRepository customerRepository;
    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {
        //Accounts accounts = accountsRepository.findByCustomerId(customer.getId());
        List<Customer> customerList =  customerRepository.findByEmail(email);
        if(!CollectionUtils.isEmpty(customerList)) {
            Accounts accounts = accountsRepository.findByCustomerId(customerList.get(0).getId());
            if (accounts != null) {
                return accounts;
            }
        }
        return null;
    }
}
