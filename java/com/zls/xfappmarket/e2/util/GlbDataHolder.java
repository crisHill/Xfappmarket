package com.zls.xfappmarket.e2.util;

import com.zls.xfappmarket.e2.data.Const;

/**
 * Created by oop on 2018/2/11.
 */

public class GlbDataHolder {

    public static int halfStageWidth;
    public static int stageHeight;

    private static int phase = Const.Phase.READY;
    public static boolean hasNextPhase(){
        return phase < Const.Phase.LAST_PHASE;
    }
    public static void nextPhase(){
        phase++;
    }
    public static int getPhase(){
        return phase;
    }

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

    public static boolean isPhaseLast() {
        return phase == Const.Phase.LAST_PHASE;
    }
    public static void resetPhaseToToLover(){
        Const.Phase.LAST_PHASE = Const.Phase.WALK_2_LOVER;
    }
    public static void onReset(){
        Const.Phase.LAST_PHASE = Const.Phase.WALK_2_SCENE2;
        phase = Const.Phase.READY;
    }

    public static boolean reachedGirl(){
        return phase >= Const.Phase.WALK_2_LOVER;
    }

}
