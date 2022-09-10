package com.anuj.test.springsecuritybasic.controller;

import com.anuj.test.springsecuritybasic.model.Cards;
import com.anuj.test.springsecuritybasic.model.Customer;
import com.anuj.test.springsecuritybasic.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @PostMapping("/myCards")
    public List<Cards> getCardDetails(@RequestBody Customer customer) {
        List<Cards> cards = cardsRepository.findByCustomerId(customer.getId());
        return cards;
    }

}
