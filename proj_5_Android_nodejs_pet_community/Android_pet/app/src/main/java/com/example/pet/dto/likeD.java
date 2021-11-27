package com.example.pet.dto;

public class likeD {
    public int getPostno() {
        return postno;
    }

    public void setPostno(int postno) {
        this.postno = postno;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    int postno;
    String UserID;

    public likeD(int postno,String UserID){
        this.postno=postno;
        this.UserID=UserID;

    }

}
