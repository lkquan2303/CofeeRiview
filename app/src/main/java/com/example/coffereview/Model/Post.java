package com.example.coffereview.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    String uid_user, id ,content, feeling, time, day;

public Post(){

}

    public Post(String uid_user, String id, String content, String feeling, String time, String day){
    this.uid_user = uid_user;
    this.id = id;
    this.content = content;
    this.feeling = feeling;
    this.time = time;
    this.day = day;
}

    public String getUid_user() {
        return uid_user;
    }

    public void setUid_user(String uid_user) {
        this.uid_user = uid_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getTime() { return time; }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
