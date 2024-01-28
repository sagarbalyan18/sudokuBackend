package com.underscore.sudokuprime.models;

import jakarta.persistence.*;

@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pKey;
    private String name;
    @Column(unique=true)
    private String userId;
    private String age;
    private String email;
    private String fcmToken;
    private String status;
    private String upi;
    private String totalAmount;
    private String userPic;

    public UserModel() {
    }

    public UserModel(String name,
                     String userId,
                     String age,
                     String email,
                     String fcmToken,
                     String upi
    ) {
        this.name = name;
        this.userId = userId;
        this.age = age;
        this.email = email;
        this.fcmToken = fcmToken;
        this.upi = upi;
    }

    public String getName() {
        return name;
    }
    public String getUpi() {
        return upi;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setUpi(String upi) {
        this.upi = upi;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
}
