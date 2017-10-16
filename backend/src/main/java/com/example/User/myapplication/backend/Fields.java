package com.example.User.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Fields {
    public int f;
    public  int j;

    public Fields() {
    }

    @Id
    public int id;

    public Fields(int f, int j) {
        this.f = f;
        this.j = j;
    }

    public int getF() {
        return f;
    }

    public int getJ() {
        return j;
    }
}
