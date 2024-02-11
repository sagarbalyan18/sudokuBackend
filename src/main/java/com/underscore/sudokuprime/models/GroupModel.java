package com.underscore.sudokuprime.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pKey;
    private String groupName;
    @Column(unique = true)
    private String groupId;
    private String members;
    @Column(length = Integer.MAX_VALUE)
    private String groupPic;
    @Column(length = Integer.MAX_VALUE)
    private String groupCover;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getGroupPic() {
        return groupPic;
    }

    public void setGroupPic(String groupPic) {
        this.groupPic = groupPic;
    }

    public String getGroupCover() {
        return groupCover;
    }

    public void setGroupCover(String groupCover) {
        this.groupCover = groupCover;
    }
}