package com.anuj.test.springsecuritybasic.controller;

import com.anuj.test.springsecuritybasic.model.Customer;
import com.anuj.test.springsecuritybasic.model.Loans;
import com.anuj.test.springsecuritybasic.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @PostMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestBody Customer customer) {
        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId());
        if (loans != null ) {
            return loans;
        }else {
            return null;
        }
    }
}
