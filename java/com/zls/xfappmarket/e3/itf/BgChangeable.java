package com.zls.xfappmarket.e3.itf;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.zls.xfappmarket.e3.beans.BgChangeParam;

import java.util.List;

/**
 * Created by oop on 2018/2/11.
 */

public interface BgChangeable {

    void changeBg(BgChangeParam bgChangeParam, BitmapDrawable endRes);

}
