package com.example.attendancemanager;

public class Word {

    private String wSubject;
    private String wPresent;
    private String wTotal;

    public Word(String wSubject,String wPresent, String wTotal){
        this.wSubject = wSubject;
        this.wPresent = wPresent;
        this.wTotal = wTotal;
    }

    public String getwTotal() {
        return wTotal;
    }

    public String getwSubject() {
        return wSubject;
    }

    public String getwPresent() {
        return wPresent;
    }
}
