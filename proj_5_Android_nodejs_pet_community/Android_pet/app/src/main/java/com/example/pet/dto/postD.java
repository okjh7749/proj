package com.example.pet.dto;

public class postD {


    int postno;
    String title;
    String content;
    String writer;
    String write_date;
    String reservation_date;
    String img_path;
    String kind;
    int addtime;
    double latitue;
    double longitude;
    double distance;

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public void setLatitue(double latitue) {
        this.latitue = latitue;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPostno() {
        return postno;
    }

    public void setPostno(int postno) {
        this.postno = postno;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
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

    public String getWrite_date() {
        return write_date;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public double getLatitue() {
        return latitue;
    }

    public void setLatitude(double latitude) {
        this.latitue = latitue;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public postD(int postno){//글쓰기
        this.postno =postno;
    }
    public postD(String title,String content ){//글쓰기
        this.title =title;
        this.content=content;
    }
    public postD(int postno,String title,String content,String writer,String write_date){//글쓰기 수정
        this.postno=postno;
        this.title =title;
        this.content=content;
        this.writer=writer;
        this.write_date=write_date;
    }
    public postD(int postno,String title,String content,String writer,String write_date,String img_path){//글쓰기 수정
        this.postno=postno;
        this.title =title;
        this.content=content;
        this.writer=writer;
        this.write_date=write_date;
        this.img_path=img_path;
    }
    public postD(String title,String content,String img_path ){//이미지 글쓰기
        this.title =title;
        this.content=content;
        this.img_path=img_path;
    }
    public postD(String title,String content,int addtime,double latitue, double longitude ){//약속예약
        this.title =title;
        this.content=content;
        this.addtime=addtime;
        this.latitue=latitue;
        this.longitude=longitude;
    }
    public postD(String title,String content,int addtime,String img_path,double latitue, double longitude ){//강아지찾기기
       this.title =title;
        this.content=content;
        this.addtime=addtime;
        this.img_path=img_path;
        this.latitue=latitue;
        this.longitude=longitude;
    }
    public postD(int postno,String title,String content,String writer,String write_date,String kind,String img_path,double latitue, double longitude,double distance){//글쓰기 수정
        this.postno=postno;
        this.title =title;
        this.content=content;
        this.writer=writer;
        this.write_date=write_date;
        this.kind=kind;
        this.img_path=img_path;
        this.latitue=latitue;
        this.longitude=longitude;
        this.distance =distance;
    }
    public postD(int postno,String title,String content,String writer,String reservation_date,double latitue, double longitude,double distance){
        this.postno=postno;
        this.title =title;
        this.content=content;
        this.writer=writer;
        this.reservation_date=reservation_date;
        this.latitue=latitue;
        this.longitude=longitude;
        this.distance =distance;
    }
    public postD(int postno,String title,String content,String writer,String reservation_date,double latitue, double longitude){
        this.postno=postno;
        this.title =title;
        this.content=content;
        this.writer=writer;
        this.reservation_date=reservation_date;
        this.latitue=latitue;
        this.longitude=longitude;
    }


}
