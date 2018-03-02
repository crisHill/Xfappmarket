package com.zls.xfappmarket.e2.data;

/**
 * Created by oop on 2018/2/9.
 */

public class Const {


    public static class MsgWhat{
        public static final int CHANGE_BG = 11;
        public static final int FLOWER_MOVE = 12;
        public static final int CLEAR_FLOWER = 13;
        public static final int CREATE_FLOWER = 14;
        public static final int REFRESH_LISTEN_BUTTON = 15;
    }
    public static class RawDim {
        public static final int UI_WIDTH = 151;
        public static final int UI_HEIGHT = 291;

        public static final int BOY_RES_WIDTH = 1055;
        public static final int BOY_RES_HEIGHT = 582;

        public static final int GIRL_RES_WIDTH = 1057;
        public static final int GIRL_RES_HEIGHT = 291;
    }
    public static class Phase{
        public static final int READY = -1;
        public static int LAST_PHASE = Phase.WALK_2_SCENE2;

        public static final int WALK_2_SCENE2 = 0;
        public static final int WALK_2_BRIDGE = 1;
        public static final int CLIMB = 2;
        public static final int WALK_2_LOVER = 3;
    }
    public static class MoveBy{//移动的距离/屏幕宽高 之比

        public static final float[][] X = {
                {-1, 0, 0, 0, },//stage
                {0, 0, 0, 0, },//girl
                {0.02f, 0.13f, 0.13f, 0.13f}//boy
        };
        public static final float[][] Y = {
                {0, 0, 0, 0},//stage
                {0, 0, 0, 0},//girl
                {0, 0, BRIDGE_HEIGHT_RATIO, 0}//boy
        };

    }
    public static final float BRIDGE_HEIGHT_RATIO = (float) 0.12;
    public static final int[] PHASE_TIME = {4000, 1000, 1000, 1000};
    public static final int[] BG_CHANGE_INTERVAL_TIME = {250, 250, 250, 250};
    public static final double HUMAN_INIT_TO_TOP_RATIO = 0.83;

    public static final int FLOWER_STEP_X = 5;
    public static final int FLOWER_STEP_Y = 10;
    public static final int FLOWER_DIRECTION_CHANGE = 5;
    public static final int FLOWER_PIVOT_ANGLE = 45;//每次旋转的度数
    public static final int FLOWER_CREATE_TIME = 250;//每秒钟创建 1000 / x 个花

    public static class SpeechIn{
        public static final String[] START_FLOWER = {
                "该有鲜花", "鲜花雨", "鲜花与"
        };
        public static final String[] END_FLOWER = {
                "停止鲜花"
        };
        public static final String[] START_MUSIC = {
                "开始音乐", "音乐开始", "音乐起", "起音乐", "音乐播放", "播放音乐"
        };
        public static final String[] END_MUSIC = {
                "停止音乐", "音乐停止", "停止播放", "关闭音乐"
        };
        public static final String[] TO_FIND_GIRL = {
                "找小玉", "去找他", "去找她", "去找它", "去吧"
        };
        public static final String[] TO_REACH_GIRL = {
                "花送给"
        };
        public static final String[] WHAT_TO_SAY_WHEN_MEET = {
                "有什么想说的",
        };
        public static final String[] WHAT_TO_DO = {
                "干什么", "想做", "去哪",
        };
    }
    public static class SpeechOut{
        public static final String[] NO_MATCH = {
                "不知道你在说什么", "请说人话", "听不懂", "漫漫长夜，无心睡眠", "小声点，吵到月亮睡觉了", "我的世界不懂你"
        };
        public static final String[] BOY_ANSWER_REACH_GIRL = {
                "桥在，月在，你在，岁月如此美好",
                "你微微地笑着，不同我说什么话，而我觉得，我拥有了全世界",
                "任何为人称道的美丽，都不及月下的你",
                "我喜欢的样子你都有，你有的样子我都喜欢",
                "我踏月而来，只因你在山中",
                "这世上，我爱三样东西：太阳，月亮，你。太阳在白天，月儿在夜晚，而你在日日夜夜。"
        };
        public static final String[] GIRL_ANSWER_REACH_GIRL = {
                "自从你出现后，我才知道原来有人爱是那么的美好", "我不要短暂的温存，只要你一世的陪伴", "我的眼里只有你，自从遇见你，一切繁华都成为背景", "我能想到最甜蜜的事，就是在喜欢你的每一天里，被你喜欢",
                "我希望我们谈一场天长地久的爱情；我要你用万年青树枝编一只戒指在花丛里向我求婚，说出你一生一世地老天荒对我也不改变的誓言，我要月亮做我们的月老证明我们的感情。"
        };
        public static final String[] ANSWER_WHAT_TO_DO = {
                "找我家小玉儿", "这里有一束鲜花，需要送给最美的少女", "想和我的她说：今夜月色很美",
        };
    }


}
