package com.zls.xfappmarket.e2.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.zls.xfappmarket.e2.data.Const;

/**
 * Created by oop on 2018/3/1.
 */

public class SpUtil {

    private static SpUtil INSTANCE;

    public static SpUtil getINSTANCE(Context context){
        if(INSTANCE == null){
            INSTANCE = new SpUtil(context);
        }
        return INSTANCE;
    }

    private final String SPLITER = "$";
    private final String SP_KEY = "SP_KEY";

    public static final String START_MUSIC = "START_MUSIC";
    public static final String END_MUSIC = "END_MUSIC";
    public static final String START_FLOWER = "START_FLOWER";
    public static final String END_FLOWER = "END_FLOWER";
    public static final String WHAT_TO_DO = "WHAT_TO_DO";
    public static final String ANSWER_WHAT_TO_DO = "ANSWER_WHAT_TO_DO";
    public static final String TO_FIND_GIRL = "TO_FIND_GIRL";
    public static final String TO_REACH_GIRL = "TO_REACH_GIRL";
    public static final String WHAT_TO_SAY_WHEN_MEET = "WHAT_TO_SAY_WHEN_MEET";
    public static final String BOY_ANSWER_REACH_GIRL = "BOY_ANSWER_REACH_GIRL";
    public static final String GIRL_ANSWER_REACH_GIRL = "GIRL_ANSWER_REACH_GIRL";
    public static final String NO_MATCH = "NO_MATCH";

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private SpUtil(Context context) {
        sp = context.getSharedPreferences(SP_KEY, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    private String[] split(String s){
        return s.split(SPLITER);
    }

    private String joint(String[] arr){
        StringBuffer sb = new StringBuffer("");
        if(arr.length > 0){
            for (String s : arr){
                sb.append(s);
                sb.append(SPLITER);
            }
            String result = sb.toString();
            return result.substring(0, result.length() - 1);
        }
        return "";
    }

    public String[] getStrArr(String key, String[] defaultStrArr){
        String strArr = sp.getString(key, "");
        if(TextUtils.isEmpty(strArr)){
            return defaultStrArr;
        }else {
            return split(strArr);
        }
    }

    public void setStrArr(String key, String[] strArr){
        editor.putString(key, joint(strArr));
        editor.commit();
    }

}
