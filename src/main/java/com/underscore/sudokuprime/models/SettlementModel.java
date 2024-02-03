package com.underscore.sudokuprime.models;

import jakarta.persistence.*;

@Entity
public class SettlementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pKey;
    private String date;
    private String payerId;
    private String payerName;
    private String payeeId;
    private String payeeName;
    private String description;
    private String category;
    private String groupId;
    private double splitRatio;
    private double amount;
    private boolean isSettled;

    public double getSplitRatio() {
        return splitRatio;
    }

    public void setSplitRatio(double splitRatio) {
        this.splitRatio = splitRatio;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getpKey() {
        return pKey;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
