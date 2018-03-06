package com.zls.xfappmarket.e3.util;

import android.content.Context;
import android.util.Log;

/**
 * Created by oop on 2018/3/5.
 */

public class Lg {

    public static void i(String tag, String ... args){
        StringBuilder sb = new StringBuilder();
        int size = args.length;
        for (int i = 0; i < size; i++){
            sb.append(args[i]);
            if(i < size - 1){
                sb.append("\t");
            }
        }
        Log.i(tag, sb.toString());
    }

}
