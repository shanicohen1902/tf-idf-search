package com.search.model;

import java.util.List;

public class Task {

    private String term;
    private String[]titles;
    private String address;

    public Task() { }

    public Task(String term, String[] titles, String address) {
        this.term = term;
        this.titles = titles;
        this.address = address;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
