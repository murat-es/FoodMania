package com.murates.durumcumuratusta;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationReser extends AppCompatActivity {

    private String date,time,numOfPerson;

    public ReservationReser(String date, String time, String numOfPerson) {
        this.date = date;
        this.time = time;
        this.numOfPerson = numOfPerson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumOfPerson() {
        return numOfPerson;
    }

    public void setNumOfPerson(String numOfPerson) {
        this.numOfPerson = numOfPerson;
    }
    }