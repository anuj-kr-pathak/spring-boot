package com.anuj.test.springsecuritybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @GetMapping("myBalance")
    public String getAccountBalance(String input){
        return "Here are the account balance from the DB";
    }
}
