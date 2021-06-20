package com.murates.durumcumuratusta;

import androidx.appcompat.app.AppCompatActivity;

public class MyOrder extends AppCompatActivity {
    String date;
    String time;
    String orderPrice;

    public MyOrder() {
    }

    public MyOrder(String orderPrice,String date, String time) {
        this.date = date;
        this.time = time;
        this.orderPrice = orderPrice;
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

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }
}
