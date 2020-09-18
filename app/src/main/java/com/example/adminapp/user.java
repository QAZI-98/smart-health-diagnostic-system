package com.example.adminapp;
public class user {

    String name;
    String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public user(String _name,String _email,String _pass){
        email=_email;
        name=_name;
        password=_pass;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}

