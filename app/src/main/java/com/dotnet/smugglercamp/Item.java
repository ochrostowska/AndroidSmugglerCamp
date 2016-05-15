package com.dotnet.smugglercamp;

import java.io.Serializable;

public class Item implements Serializable {

    //Zmienne składające się na nasz rekord
    private int item_id;
    private String codename;
    private String name;
    private int quantity;

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}