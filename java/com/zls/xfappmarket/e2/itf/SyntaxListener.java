package com.zls.xfappmarket.e2.itf;

/**
 * Created by oop on 2018/3/1.
 */

public interface SyntaxListener {

    void startFlower();
    void endFlower();
    void startMusic();
    void endMusic();
    void toFindGirl();
    void toReachGirl();

    void respWhatToDo(String resp);
    void onReachGirl(String boyResp, String girlResp);
    void noMatch(String resp);

}
