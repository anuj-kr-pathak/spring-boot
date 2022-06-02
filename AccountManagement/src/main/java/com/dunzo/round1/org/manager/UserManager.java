package com.dunzo.round1.org.manager;

import com.dunzo.round1.org.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    Map<String, User> userMap;
    Map<String,Map<String,Double>> debitAccount;
    Map<String,Map<String,Double>> oweAccount;

    public UserManager() {
        this.userMap = new HashMap<>();
        this.debitAccount = new HashMap<>();
        this.oweAccount = new HashMap<>();
    }
    public Map<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    public Map<String, Map<String, Double>> getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(Map<String, Map<String, Double>> debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Map<String, Map<String, Double>> getOweAccount() {
        return oweAccount;
    }

    public void setOweAccount(Map<String, Map<String, Double>> oweAccount) {
        this.oweAccount = oweAccount;
    }

    public String findMaximumOweUser(){
        String result = null;
        Double max=0.0;
        for(Map.Entry<String,User> entry : getUserMap().entrySet()){
            if(max < entry.getValue().getCreditAmount()){
                max=entry.getValue().getCreditAmount();
                result = entry.getKey();
            }
        }
       return result;
    }

    public String findMaximumDebitUser(){
        String result = null;
        Double max=0.0;
        for(Map.Entry<String,User> entry : getUserMap().entrySet()){
            if(max < entry.getValue().getDebitAmount()){
                max=entry.getValue().getDebitAmount();
                result = entry.getKey();
            }
        }
        return result;
    }

}
