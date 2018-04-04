package com.zls.xfappmarket.e3.util;

import com.zls.xfappmarket.e3.data.Const;

/**
 * Created by oop on 2018/2/11.
 */

public class GlbDataHolder {

    public static int halfStageWidth;
    public static int stageHeight;

    private static boolean phaseExecuting = false;
    public static void startPhase(){
        phaseExecuting = true;
    }
    public static void endPhase(){
        phaseExecuting = false;
    }
    public static boolean isPhaseExecuting(){
        return phaseExecuting;
    }

}
