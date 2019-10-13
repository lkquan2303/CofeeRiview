package com.example.coffereview.Model;

public class InforClass {
    String uid_user;
    String id;
    String height;
    String weight;
    String name;
    public InforClass()
    {}

    public InforClass(String uid_user, String id, String height, String weight, String name){
        this.uid_user = uid_user;
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.name = name;

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
