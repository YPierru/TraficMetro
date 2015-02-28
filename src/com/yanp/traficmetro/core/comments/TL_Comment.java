package com.yanp.traficmetro.core.comments;

/**
 * Created by YPierru on 19/01/2015.
 */
public class TL_Comment {

    private String date;
    private int color;

    public TL_Comment(int color, String date){
        this.color=color;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
