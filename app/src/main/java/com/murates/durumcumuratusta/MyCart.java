package com.murates.durumcumuratusta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MyCart extends AppCompatActivity {

    String cartName, cartPrice,cartQuantity;

    public MyCart() {
    }

    public MyCart(String cartName, String cartPrice, String cartQuantity) {
        this.cartName = cartName;
        this.cartPrice = cartPrice;
        this.cartQuantity = cartQuantity;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public String getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(String cartPrice) {
        this.cartPrice = cartPrice;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
    }
}
