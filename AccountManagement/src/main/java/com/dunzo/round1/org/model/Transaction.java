package com.dunzo.round1.org.model;

public class Transaction {
    String ownedUserId;
    String debitUserId;
    Double amount;

    public Transaction(String ownedUserId, String debitUserId, Double amount) {
        this.ownedUserId = ownedUserId;
        this.debitUserId = debitUserId;
        this.amount = amount;
    }

    public String getOwnedUserId() {
        return ownedUserId;
    }

    public void setOwnedUserId(String ownedUserId) {
        this.ownedUserId = ownedUserId;
    }

    public String getDebitUserId() {
        return debitUserId;
    }

    public void setDebitUserId(String debitUserId) {
        this.debitUserId = debitUserId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
