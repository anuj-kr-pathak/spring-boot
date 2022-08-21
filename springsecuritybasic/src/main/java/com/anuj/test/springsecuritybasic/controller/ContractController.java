package com.anuj.test.springsecuritybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContractController {

    @GetMapping("/contact")
    public String saveContactInquiryDetails(String input){
        return "Inquiry details are saved to the DB";
    }
}
