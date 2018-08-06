package com.skt.tmaphot.activity;

public interface IRecyclerViewDataAdapter {
    public void notifyData();
    public void notifyChanged(int start, int last);
}
