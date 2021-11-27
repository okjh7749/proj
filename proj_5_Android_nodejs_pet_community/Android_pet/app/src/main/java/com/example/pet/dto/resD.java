package com.example.pet.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import retrofit2.Call;
import retrofit2.http.*;
public class resD {
    @Expose
    @SerializedName("code") private String code;
    @Expose
    @SerializedName("message") private String message;
    @Expose
    @SerializedName("sessionID") private String sessionID;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }



    public resD(){

    }
    public resD(String code,String message,String sessionID) {
        this.code = code;
        this.message = message;
        this.sessionID = sessionID;
    }

}




