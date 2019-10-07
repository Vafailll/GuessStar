package com.example.toolsshop;

import androidx.annotation.NonNull;

public class Drill {
    private String tittle;
    private String info;
    private int imageResourceId;

    public Drill(String tittle, String info, int imageResourceId) {
        this.tittle = tittle;
        this.info = info;
        this.imageResourceId = imageResourceId;
    }

    public String getTittle() {
        return tittle;
    }

    @NonNull
    @Override
    public String toString() {
        return tittle;

    }

    public String getInfo() {
        return info;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
