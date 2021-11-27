package com.example.pet.dto;

public class commentD {

    int cno;
    int postno;
    String writer;
    String content;
    String write_date;

    public int getCno() {
        return cno;
    }

    public void setCno(int cno) {
        this.cno = cno;
    }

    public int getPostno() {
        return postno;
    }

    public void setPostno(int postno) {
        this.postno = postno;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String userID) {
        this.writer = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWrite_date() {
        return write_date;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }
    public commentD(String userID,String content,String write_date){
        this.writer =userID;
        this.content=content;
        this.write_date=write_date;
    }

}
