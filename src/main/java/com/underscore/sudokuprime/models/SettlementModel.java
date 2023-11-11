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
    private String groupId;
    private String splitRatio;

    public String getSplitRatio() {
        return splitRatio;
    }

    public void setSplitRatio(String splitRatio) {
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

    private String amount;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
}
