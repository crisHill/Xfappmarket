package com.zls.xfappmarket.e2.util;

import android.content.Context;

import com.zls.xfappmarket.e2.data.Const;
import com.zls.xfappmarket.e2.itf.SyntaxListener;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by oop on 2018/2/12.
 */

public class TextResolver {

    private SyntaxListener listener;
    private Context context;
    private String text;
    public TextResolver(Context context, SyntaxListener listener){
        this.context = context;
        this.listener = listener;
    }

    private static final String getRandomResult(String[] candidates){
        int size = candidates.length;
        Random rand = new Random();
        int index = rand.nextInt(size);
        return candidates[index];
    }

    private final boolean contains(String[] strs){
        for (String str : strs){
            if(text.contains(str)){
                return true;
            }
        }
        return false;
    }

    public void resolve(String text){
        this.text = text;

        if(isStartMusic()){
            listener.startMusic();
            return;
        }

        if(isEndMusic()){
            listener.endMusic();
            return;
        }

        if(isStartFlower()){
            listener.startFlower();
            return;
        }

        if(isEndFlower()){
            listener.endFlower();
            return;
        }

        if(isWhatToDo()){
            listener.respWhatToDo(answerWhatToDo());
            return;
        }

        if(isToFindGirl()){
            listener.toFindGirl();
            return;
        }

        if(isToReachGirl()){
            listener.toReachGirl();
            return;
        }

        if(isWhatToSayWhenMeet()){
            listener.onReachGirl(boyAnswerReachGirl(), girlAnswerReachGirl());
            return;
        }

        listener.noMatch(answerNoMatch());

    }



    private String answerNoMatch(){
        String key = SpUtil.NO_MATCH;
        String[] defaultStrArr = Const.SpeechOut.NO_MATCH;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return getRandomResult(arr);
    }

    private String answerWhatToDo(){
        String key = SpUtil.ANSWER_WHAT_TO_DO;
        String[] defaultStrArr = Const.SpeechOut.ANSWER_WHAT_TO_DO;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return getRandomResult(arr);
    }

    private String boyAnswerReachGirl(){
        String key = SpUtil.BOY_ANSWER_REACH_GIRL;
        String[] defaultStrArr = Const.SpeechOut.BOY_ANSWER_REACH_GIRL;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return getRandomResult(arr);
    }
    private String girlAnswerReachGirl(){
        String key = SpUtil.GIRL_ANSWER_REACH_GIRL;
        String[] defaultStrArr = Const.SpeechOut.GIRL_ANSWER_REACH_GIRL;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return getRandomResult(arr);
    }

    private boolean isStartMusic(){
        String key = SpUtil.START_MUSIC;
        String[] defaultStrArr = Const.SpeechIn.START_MUSIC;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr);
    }
    private boolean isEndMusic(){
        String key = SpUtil.END_MUSIC;
        String[] defaultStrArr = Const.SpeechIn.END_MUSIC;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr);
    }
    private boolean isStartFlower(){
        String key = SpUtil.START_FLOWER;
        String[] defaultStrArr = Const.SpeechIn.START_FLOWER;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr);
    }
    private boolean isEndFlower(){
        String key = SpUtil.END_FLOWER;
        String[] defaultStrArr = Const.SpeechIn.END_FLOWER;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr);
    }
    private boolean isWhatToDo(){
        String key = SpUtil.WHAT_TO_DO;
        String[] defaultStrArr = Const.SpeechIn.WHAT_TO_DO;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr);
    }
    private boolean isToFindGirl(){
        String key = SpUtil.TO_FIND_GIRL;
        String[] defaultStrArr = Const.SpeechIn.TO_FIND_GIRL;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr);
    }
    private boolean isToReachGirl(){
        String key = SpUtil.TO_REACH_GIRL;
        String[] defaultStrArr = Const.SpeechIn.TO_REACH_GIRL;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr);
    }
    private boolean isWhatToSayWhenMeet(){
        String key = SpUtil.WHAT_TO_SAY_WHEN_MEET;
        String[] defaultStrArr = Const.SpeechIn.WHAT_TO_SAY_WHEN_MEET;
        String[] arr = SpUtil.getINSTANCE(context).getStrArr(key, defaultStrArr);
        return  contains(arr);
    }

}
