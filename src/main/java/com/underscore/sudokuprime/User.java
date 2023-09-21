package com.underscore.sudokuprime;

public class User {

    private String name;
    private String userId;
    private String age;
    private String email;
    private String badge;
    private String rank;
    private String isTop100;
    private String boardsSolved;
    private String totalBoardPlayed;


    public User(String name, String userId, String age, String email, String badge, String rank, String isTop100, String boardsSolved, String totalBoardPlayed) {
        this.name = name;
        this.userId = userId;
        this.age = age;
        this.email = email;
        this.badge = badge;
        this.rank = rank;
        this.isTop100 = isTop100;
        this.boardsSolved = boardsSolved;
        this.totalBoardPlayed = totalBoardPlayed;
    }

    public String getName() {
        return name;
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

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getIsTop100() {
        return isTop100;
    }

    public void setIsTop100(String isTop100) {
        this.isTop100 = isTop100;
    }

    public String getBoardsSolved() {
        return boardsSolved;
    }

    public void setBoardsSolved(String boardsSolved) {
        this.boardsSolved = boardsSolved;
    }

    public String getTotalBoardPlayed() {
        return totalBoardPlayed;
    }

    public void setTotalBoardPlayed(String totalBoardPlayed) {
        this.totalBoardPlayed = totalBoardPlayed;
    }
}
