package com.underscore.sudokuprime.models;

import jakarta.persistence.*;

@Entity
public class RoomModel {

    @Id
    @Column(unique=true)
    private String roomId;

    private String userOneId;

    private String userTwoId;

    private String boardNo;

    private String creatorFcmToken;

    public RoomModel() {
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserOne() {
        return userOneId;
    }

    public void setUserOne(String userOneId) {
        this.userOneId = userOneId;
    }

    public String getUserTwo() {
        return userTwoId;
    }

    public void setUserTwo(String userTwoId) {
        this.userTwoId = userTwoId;
    }

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    public String getCreatorFcmToken() {
        return creatorFcmToken;
    }

    public void setCreatorFcmToken(String creatorFcmToken) {
        this.creatorFcmToken = creatorFcmToken;
    }
}
