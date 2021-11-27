package com.example.pet.dto;

public class respostD {
    int postno;
    String title;
    String content;
    String writer;
    String write_day;

    public int getPostno() {
        return postno;
    }

    public void setPostno(int postno) {
        this.postno = postno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWrite_day() {
        return write_day;
    }

    public void setWrite_day(String write_day) {
        this.write_day = write_day;
    }
    public respostD(int postno,String title,String content,String writer,String write_day){//글쓰기
        this.postno=postno;
        this.title =title;
        this.content=content;
        this.writer=writer;
        this.write_day=write_day;
    }

}
