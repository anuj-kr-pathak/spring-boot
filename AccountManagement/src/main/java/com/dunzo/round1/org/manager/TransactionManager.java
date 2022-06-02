package com.dunzo.round1.org.manager;

import com.dunzo.round1.org.model.Transaction;

import java.util.*;

public class TransactionManager {
    List<Transaction> transactionList;

    public TransactionManager() {
        this.transactionList = new ArrayList<>();
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
