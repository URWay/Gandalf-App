package com.app.gandalf.piquatro.models;

/**
 * Created by augus on 12/11/2017.
 */

public class Singleton {

    private static final Singleton INSTANCE = new Singleton();
    private int lastID;

    private Singleton() {}

    public int getLastID() {
        return lastID;
    }

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }


}
