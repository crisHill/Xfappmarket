package com.zls.xfappmarket.e3.util;

import android.content.Context;
import android.text.TextUtils;

import com.zls.xfappmarket.e3.data.Const;

import java.util.Random;

/**
 * Created by oop on 2018/2/12.
 */

public class TextResolver {

    public static void resolve(Context context, String text){

        if(isNonsenseText(text)){
            return;
        }

        if(isStartMusic(context, text)){
            MsgManager.getINSTANCE().inform(MsgManager.Type.START_OR_END_MUSIC, true);
            return;
        }

        if(isEndMusic(context, text)){
            MsgManager.getINSTANCE().inform(MsgManager.Type.START_OR_END_MUSIC, false);
            return;
        }

        if(isStartFlower(context, text)){
            MsgManager.getINSTANCE().inform(MsgManager.Type.START_OR_END_FLOWER, true);
            return;
        }

        if(isEndFlower(context, text)){
            MsgManager.getINSTANCE().inform(MsgManager.Type.START_OR_END_FLOWER, false);
            return;
        }

        if(isWhatToDo(context, text)){
            MsgManager.getINSTANCE().inform(MsgManager.Type.ASK_WHAT_TO_DO, null);
            return;
        }

        if(isToFindGirl(context, text)){
            MsgManager.getINSTANCE().inform(MsgManager.Type.ASK_TO_FIND_GIRL, null);
            return;
        }

        if(isToReachGirl(context, text)){
            MsgManager.getINSTANCE().inform(MsgManager.Type.ASK_TO_REACH_GIRL, null);
            return;
        }

        if(isWhatToSayWhenMeet(context, text)){
            MsgManager.getINSTANCE().inform(MsgManager.Type.ASK_WHAT_TO_SAY, null);
            return;
        }

        MsgManager.getINSTANCE().inform(MsgManager.Type.NO_MATCH, null);

    }

    public static String answerNoMatch(Context context){
        String key = SpUtil.NO_MATCH;
        String[] defaultStrArr = Const.SpeechOut.NO_MATCH;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return getRandomResult(arr);
    }

    public static String answerWhatToDo(Context context){
        String key = SpUtil.ANSWER_WHAT_TO_DO;
        String[] defaultStrArr = Const.SpeechOut.ANSWER_WHAT_TO_DO;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return getRandomResult(arr);
    }

    public static String boyAnswerReachGirl(Context context){
        String key = SpUtil.BOY_ANSWER_REACH_GIRL;
        String[] defaultStrArr = Const.SpeechOut.BOY_ANSWER_REACH_GIRL;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return getRandomResult(arr);
    }
    public static String girlAnswerReachGirl(Context context){
        String key = SpUtil.GIRL_ANSWER_REACH_GIRL;
        String[] defaultStrArr = Const.SpeechOut.GIRL_ANSWER_REACH_GIRL;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return getRandomResult(arr);
    }

    private static final String getRandomResult(String[] candidates){
        int size = candidates.length;
        Random rand = new Random();
        int index = rand.nextInt(size);
        return candidates[index];
    }

    private static boolean contains(String[] strs, String text){
        for (String str : strs){
            if(text.contains(str)){
                return true;
            }
        }
        return false;
    }


    private static boolean isNonsenseText(String text){
        return TextUtils.isEmpty(text) || text.length() < 2;
    }

    private static boolean isStartMusic(Context context, String text){
        String key = SpUtil.START_MUSIC;
        String[] defaultStrArr = Const.SpeechIn.START_MUSIC;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr, text);
    }
    private static boolean isEndMusic(Context context, String text){
        String key = SpUtil.END_MUSIC;
        String[] defaultStrArr = Const.SpeechIn.END_MUSIC;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr, text);
    }
    private static boolean isStartFlower(Context context, String text){
        String key = SpUtil.START_FLOWER;
        String[] defaultStrArr = Const.SpeechIn.START_FLOWER;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr, text);
    }
    private static boolean isEndFlower(Context context, String text){
        String key = SpUtil.END_FLOWER;
        String[] defaultStrArr = Const.SpeechIn.END_FLOWER;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr, text);
    }
    private static boolean isWhatToDo(Context context, String text){
        String key = SpUtil.WHAT_TO_DO;
        String[] defaultStrArr = Const.SpeechIn.WHAT_TO_DO;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr, text);
    }
    private static boolean isToFindGirl(Context context, String text){
        String key = SpUtil.TO_FIND_GIRL;
        String[] defaultStrArr = Const.SpeechIn.TO_FIND_GIRL;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr, text);
    }
    private static boolean isToReachGirl(Context context, String text){
        String key = SpUtil.TO_REACH_GIRL;
        String[] defaultStrArr = Const.SpeechIn.TO_REACH_GIRL;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr, text);
    }
    private static boolean isWhatToSayWhenMeet(Context context, String text){
        String key = SpUtil.WHAT_TO_SAY_WHEN_MEET;
        String[] defaultStrArr = Const.SpeechIn.WHAT_TO_SAY_WHEN_MEET;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr, text);
    }

}
