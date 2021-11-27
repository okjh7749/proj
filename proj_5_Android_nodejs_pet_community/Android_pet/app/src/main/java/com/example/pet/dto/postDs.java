package com.example.pet.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class postDs {

    @SerializedName("postDs")
    @Expose
    private ArrayList<postD> postDs = null;

    public ArrayList<postD> getPostDs() {
        return postDs;
    }

    public void setPostDs(ArrayList<postD> postDs) {
        this.postDs = postDs;
    }

    public postDs(ArrayList<postD> postDs){//글쓰기
        this.postDs =postDs;
    }


    public postD get(int position) {
        return postDs.get(position);
    }

    public int size() {
        return postDs.size();
    }
}
