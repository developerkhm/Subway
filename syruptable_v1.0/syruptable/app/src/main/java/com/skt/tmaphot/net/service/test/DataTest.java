package com.skt.tmaphot.net.service.test;

import java.util.List;

public class DataTest{
    private List<PostDatum> postData;
    private List<Album> albums;


    public DataTest(List<PostDatum> postData, List<Album> albums) {
        this.postData = postData;
        this.albums = albums;
    }

    public List<PostDatum> getPostData() {
        return postData;
    }

    public void setPostData(List<PostDatum> postData) {
        this.postData = postData;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}