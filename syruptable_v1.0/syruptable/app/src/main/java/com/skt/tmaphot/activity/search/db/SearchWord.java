package com.skt.tmaphot.activity.search.db;

public class SearchWord {
    private int id;
    private String keyword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public SearchWord() {
    }

    public SearchWord(String keyword) {
        this.keyword = keyword;
    }
}
