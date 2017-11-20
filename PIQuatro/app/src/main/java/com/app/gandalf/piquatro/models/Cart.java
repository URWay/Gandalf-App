package com.app.gandalf.piquatro.models;

import java.util.List;

public class Cart {
    private List<Cart_List> items;

    public Cart() {

    }

    public Cart(List<Cart_List> items) {
        this.items = items;
    }

    public List<Cart_List> getAllItems() {
        return items;
    }

    public void addItem(List<Cart_List> item) {
        items = item;
    }

    public void removeItem(Cart_List item) {
        items.remove(item);
    }

    public void setItems(List<Cart_List> items) {
        this.items = items;
    }
}