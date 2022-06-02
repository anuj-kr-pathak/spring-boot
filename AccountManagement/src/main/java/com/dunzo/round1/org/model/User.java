package com.dunzo.round1.org.model;

import java.util.*;
import java.util.Map;

public class User {
    String id;
    Map<String,Double> friendsAmount;
    Double creditAmount;
    Double debitAmount;

    public User(String id) {
        this.id = id;
        this.friendsAmount = new HashMap<>();
        this.transactionList = new ArrayList<>();
        creditAmount =0.0;
        debitAmount=0.0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Double> getFriendsAmount() {
        return friendsAmount;
    }

    public void setFriendsAmount(Map<String, Double> friendsAmount) {
        this.friendsAmount = friendsAmount;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    List<Transaction> transactionList;
}
