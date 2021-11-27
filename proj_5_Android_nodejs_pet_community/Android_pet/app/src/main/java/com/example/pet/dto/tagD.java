package com.example.pet.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class tagD {
    @Expose
    @SerializedName("tagno") private int tagno;
    @Expose
    @SerializedName("tag_name") private String tag_name;
    @Expose
    @SerializedName("cnt") private int cnt;

    public int getTagno() {
        return tagno;
    }

    public void setTagno(int tagno) {
        this.tagno = tagno;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public tagD(){

    }
    public tagD(int tagno,String tag_name,int cnt) {
        this.tagno = tagno;
        this.tag_name = tag_name;
        this.cnt = cnt;
    }
}
