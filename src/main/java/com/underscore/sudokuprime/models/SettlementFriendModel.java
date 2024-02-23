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
    private String payeePic;
    private String payerId;
    private String payerName;
    private String payerPic;
    private double amount;
    private String nextAutoReminder;
    private double splitRatio;
    private String groupId;

    public SettlementFriendModel(String payeeNme,
                                 double amount,
                                 String payeeId,
                                 String payerId,
                                 String payerName,
                                 double splitRatio,
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public double getSplitRatio() {
        return splitRatio;
    }

    public void setSplitRatio(double splitRatio) {
        this.splitRatio = splitRatio;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPayerPic() {
        return payerPic;
    }

    public void setPayerPic(String payerPic) {
        this.payerPic = payerPic;
    }

    public String getPayeePic() {
        return payeePic;
    }

    public void setPayeePic(String payeePic) {
        this.payeePic = payeePic;
    }
}
