package com.example.studentmanagementsystemusingfirebase;

import androidx.annotation.NonNull;

public class Student {
    private String name;
    private String email;

    public Student(String name,String email) {
        this.email = email;
        this.name=name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Name : "+name+"\n"+
                "Email : "+email;
    }
}
