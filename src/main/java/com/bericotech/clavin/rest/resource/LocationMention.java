package com.bericotech.clavin.rest.resource;

public class LocationMention {
    public String text;
    public int position;

    public LocationMention() {}

    public LocationMention(String text, int position){
        this.text = text;
        this.position = position;
    }
}
