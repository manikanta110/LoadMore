package com.example.user.loadmore.Model;

/**
 * Created by user on 07-Jan-18.
 */

public class Item {

    public String name;
    public int length;

    public Item(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
