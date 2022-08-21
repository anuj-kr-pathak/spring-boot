package com.anuj.test.springsecuritybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {
    @GetMapping("/myLoans")
    public String getLoanDetails(String input){
        return "here are the loan details from the DB";
    }
}
