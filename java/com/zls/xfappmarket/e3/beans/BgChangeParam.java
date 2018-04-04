package com.zls.xfappmarket.e3.beans;

import android.graphics.drawable.BitmapDrawable;

import java.util.List;

/**
 * Created by oop on 2018/3/6.
 */

public class BgChangeParam {

    private long time;
    private List<BitmapDrawable> tempResList;
    private long refreshGapTime;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<BitmapDrawable> getTempResList() {
        return tempResList;
    }

    public void setTempResList(List<BitmapDrawable> tempResList) {
        this.tempResList = tempResList;
    }

    public long getRefreshGapTime() {
        return refreshGapTime;
    }

    public void setRefreshGapTime(long refreshGapTime) {
        this.refreshGapTime = refreshGapTime;
    }
}
