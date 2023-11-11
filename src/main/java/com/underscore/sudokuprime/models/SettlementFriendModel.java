package com.underscore.sudokuprime.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SettlementFriendModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pKey;
    private String payeeName;
    private String payeeId;
    private String payerId;
    private String payerName;
    private String amount;
    private String nextAutoReminder;
    private String splitRatio;
    private String groupId;


    public SettlementFriendModel(String payeeNme,
                                 String amount,
                                 String payeeId,
                                 String payerId,
                                 String payerName,
                                 String splitRatio,
                                 String groupId
                                 ) {
        this.payeeName = payeeNme;
        this.payerName = payerName;
        this.amount = amount;
        this.payeeId = payeeId;
        this.payerId = payerId;
        this.splitRatio = splitRatio;
        this.groupId = groupId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }


    public String getNextAutoReminder() {
        return nextAutoReminder;
    }

    public void setNextAutoReminder(String nextAutoReminder) {
        this.nextAutoReminder = nextAutoReminder;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getSplitRatio() {
        return splitRatio;
    }

    public void setSplitRatio(String splitRatio) {
        this.splitRatio = splitRatio;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
