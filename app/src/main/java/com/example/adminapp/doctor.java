package com.example.adminapp;

public class doctor {

    String name;
    String username;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    String time;
    String day;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String phone;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String location;
    String password;
    String speciality;

    public doctor
          (String _name,String _username,String _location,String _password,String _speciality,String _phone,String _day,String _time) {

               name = _name;
               username = _username;
                location = _location;
               password = _password;
               speciality = _speciality;
               phone=_phone;
               time=_time;
               day=_day;
           }

    public doctor(String _special,String _name,String _location,String _phone,String _username) {

        name = _name;
        speciality=_special;
        phone=_phone;
        location=_location;
        username = _username;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }



}
