package com.zls.xfappmarket.e1;

/**
 * Created by oop on 2018/2/9.
 */

public class Const {

    /*

     阶段一：从开始到桥前
     阶段二：从桥前到完成上桥
     阶段三：从完成上桥到相会
     阶段四：从相会到转身完成

    */

    public static final int[] TIMES = {5000, 2000, 1500, 1500, 1500, 500};
    public static final double[] RATIO_BOY_X_TO = {0.15, 0.15, 0.27, 0.38, 0.5, 0.5};//男孩 各阶段需要在x方向移动到那个点，即点x坐标与屏幕宽度之比
    public static final double[] RATIO_GIRL_X_TO = {0.85, 0.85, 0.73, 0.62, 0.5, 0.5};
    public static final double[] RATIO_BOY_Y_DISTANCE = {0, 0, 0, 0.12, 0, 0};//男孩 各阶段需要在y方向移动的距离比，即距离与屏幕高度之比
    public static final int[] WHAT_PHASE_START = {100, 101, 102, 103, 104, 105};
    public static final int[] WHAT_PHASE_END = {200, 201, 202, 203, 204, 205};
    public static final int[] WHAT_PHASE_EXECUTING = {300, 301, 302, 303, 304, 305};
    public static final int WHAT_PHASE_DONE = 400;


    public static final int SLEEP_TIME_BOY = 250;
    public static final int SLEEP_TIME_STAGE = 40;

    public static final double BOY_INIT_MARGIN_RATIO_2_TOP = 0.83;


    public static final int PHASE_START_WALK_2_SCENE2 = 0;
    public static final int PHASE_START_REST = 1;
    public static final int PHASE_START_WALK_2_BRIDGE = 2;
    public static final int PHASE_START_CLIMB = 3;
    public static final int PHASE_START_WALK_2_LOVER = 4;
    public static final int PHASE_START_TURNAROUND = 5;
    public static final int PHASE_LAST = PHASE_START_WALK_2_LOVER;


}
