package com.example.pet.dto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.*;
public class userD {
    @Expose
    @SerializedName("userID") private String userID;
    @Expose
    @SerializedName("userPWD") private String userPWD;
    @Expose
    @SerializedName("userPWD2") private String userPWD2;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserPWD(String userPWD) {
        this.userPWD = userPWD;
    }

    public void setUserPWD2(String userPWD2) {
        this.userPWD2 = userPWD2;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserPWD() {
        return userPWD;
    }

    public String getUserPWD2() {
        return userPWD2;
    }




    public userD(){

    }
    public userD(String userID,String userPWD){
        this.userID=userID;
        this.userPWD=userPWD;
    }
    public userD(String userID,String userPWD,String userPWD2){
        this.userID=userID;
        this.userPWD=userPWD;
        this.userPWD2=userPWD2;
    }

}
