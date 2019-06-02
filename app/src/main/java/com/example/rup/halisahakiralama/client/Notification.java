package com.example.rup.halisahakiralama.client;

import com.google.api.client.util.Key;

public class Notification {



    @Key private Long id;
    @Key private String user;
    @Key private String willingUser;
    @Key private String text;
    @Key private String date;
    @Key private boolean isRead;
    @Key private boolean approval;
    @Key private String type;
    @Key private Long claimId;


    public void setApproval(boolean approval) {
        this.approval = approval;
    }
    public boolean getApproval() {
        return approval;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWillingUser() {
        return willingUser;
    }

    public void setWillingUser(String willingUser) {
        this.willingUser = willingUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }
}
