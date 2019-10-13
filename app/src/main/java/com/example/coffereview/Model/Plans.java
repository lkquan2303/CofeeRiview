package com.example.coffereview.Model;

public class Plans {
    String uid_user, id_plan, day;

    public Plans(){

    }

    public Plans(String uid_user, String id_plan, String day){
        this.uid_user = uid_user;
        this.id_plan = id_plan;
        this.day = day;
    }


    public String getUid_user() {
        return uid_user;
    }

    public void setUid_user(String uid_user) {
        this.uid_user = uid_user;
    }

    public String getId_plan() {
        return id_plan;
    }

    public void setId_plan(String id_plan) {
        this.id_plan = id_plan;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}