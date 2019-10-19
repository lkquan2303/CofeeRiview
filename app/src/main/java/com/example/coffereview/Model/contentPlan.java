package com.example.coffereview.Model;

public class contentPlan {
    String id_content, work, time, status, id_plan;

    public contentPlan(){

    }

    public contentPlan(String id_content, String work, String time, String status, String id_plan){
        this.id_content = id_content;
        this.work = work;
        this.time = time;
        this.status = status;
        this.id_plan = id_plan;
    }

    public String getId_content() {
        return id_content;
    }

    public void setId_content(String id_content) {
        this.id_content = id_content;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_plan() {
        return id_plan;
    }

    public void setId(String id_plan) {
        this.id_plan = id_plan;
    }
}