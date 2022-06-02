package com.dunzo.round1.org;

import com.dunzo.round1.org.manager.TransactionManager;
import com.dunzo.round1.org.manager.UserManager;
import com.dunzo.round1.org.model.Transaction;
import com.dunzo.round1.org.model.User;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DunzoApplication {
    public static void main(String[] args) {

        TransactionManager transactionManager = new TransactionManager();
        UserManager userManager = new UserManager();
        getAllTransactionFromJsonFile(transactionManager);
        updateUserTransactionSheet(transactionManager,userManager);
        personOwned(userManager,transactionManager,"M");
        personMostOwned(userManager);
        personMostDebitUser(userManager);

    }
 /*   void showAllUserOwned(UserManager userManager){
        userManager.showAllUserDebit();

    }*/

    private static void personMostOwned(UserManager userManager) {
        System.out.println(userManager.findMaximumOweUser());
    }

    private static void personMostDebitUser(UserManager userManager) {
        System.out.println(userManager.findMaximumDebitUser());
    }

    private static void personOwned(UserManager userManager, TransactionManager transactionManager,String userId) {
        System.out.println(userManager.getUserMap().get(userId).getCreditAmount());
    }

    private static void updateUserTransactionSheet(TransactionManager transactionManager, UserManager userManager) {
        for(Transaction transaction : transactionManager.getTransactionList()){
            if(userManager.getUserMap().get(transaction.getOwnedUserId())==null){
                userManager.getUserMap().put(transaction.getOwnedUserId(),new User(transaction.getOwnedUserId()));
            }
            User creditUser =  userManager.getUserMap().get(transaction.getOwnedUserId());
            creditUser.setCreditAmount(creditUser.getCreditAmount()+transaction.getAmount());

            if(userManager.getUserMap().get(transaction.getDebitUserId())==null){
                userManager.getUserMap().put(transaction.getDebitUserId(),new User(transaction.getDebitUserId()));
            }
            User debitUser = userManager.getUserMap().get(transaction.getDebitUserId());
            debitUser.setDebitAmount(debitUser.getDebitAmount()+transaction.getAmount());

            if(userManager.getDebitAccount().get(transaction.getDebitUserId())==null){
                userManager.getDebitAccount().put(transaction.getDebitUserId(),new HashMap<>());

            }
            if(userManager.getOweAccount().get(transaction.getOwnedUserId())==null){
                userManager.getOweAccount().put(transaction.getOwnedUserId(),new HashMap<>());

            }
            userManager.getOweAccount().get(transaction.getOwnedUserId()).put(transaction.getDebitUserId(),transaction.getAmount());
            userManager.getDebitAccount().get(transaction.getDebitUserId()).put(transaction.getOwnedUserId(),transaction.getAmount());
            userManager.getUserMap().get(transaction.getDebitUserId()).setDebitAmount(userManager.getUserMap().get(transaction.getDebitUserId()).getDebitAmount()+transaction.getAmount());
            userManager.getUserMap().get(transaction.getOwnedUserId()).setCreditAmount(userManager.getUserMap().get(transaction.getOwnedUserId()).getCreditAmount()+transaction.getAmount());

        }
    }


    private static void getAllTransactionFromJsonFile(TransactionManager transactionManager) {
        try (CSVReader reader = new CSVReader(new FileReader("/home/anuj/Documents/project/dunzo/src/main/resources/transaction.csv"))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> {
               transactionManager.getTransactionList().add(new Transaction(x[0],x[1],Double.parseDouble(x[2])));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
