package com.anuj.test.springsecuritybasic.controller;
import java.security.Principal;
import java.util.List;

import com.anuj.test.springsecuritybasic.model.Customer;
import com.anuj.test.springsecuritybasic.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public Customer getUserDetailsAfterLogin(Principal user) {
        List<Customer> customers = customerRepository.findByEmail(user.getName());
        if (customers.size() > 0) {
            return customers.get(0);
        }else {
            return null;
        }

    }

    @PostMapping("/register")
    public ResponseEntity<String> getUserDetailsAfterLogin(@RequestBody  Customer customer) {
        Customer savedCustomer = null;
        ResponseEntity response=null;
        try{
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            savedCustomer = customerRepository.save(customer);
            if(savedCustomer.getId()>0){
                response = ResponseEntity.status(HttpStatus.CREATED).body("Given user details are successfully registered");
            }
        }catch (Exception ext){
           response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception due to :: "+ ext.getMessage());
        }
        return response;
    }

}
