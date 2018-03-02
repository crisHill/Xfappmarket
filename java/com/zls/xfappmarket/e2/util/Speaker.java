package com.zls.xfappmarket.e2.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.zls.xfappmarket.R;
import com.zls.xfappmarket.e2.data.VoiceBean;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by oop on 2018/2/11.
 */

public class Speaker {

    private static Speaker INSTANCE;
    public static Speaker getINSTANCE(Context context){
        if(INSTANCE == null){
            INSTANCE = new Speaker(context);
        }
        return INSTANCE;
    }

    public static String voicerLocalBoy="xiaofeng";
    public static String voicerLocalGirl="xiaoyan";
    private Context context;
    private SpeechSynthesizer mTts;
    private Queue<VoiceBean> voiceBeanQueue;
    private VoiceBean curVoiceBean;

    private Speaker(Context context){
        this.context = context;
        this.voiceBeanQueue = new LinkedList<>();

        this.mTts = SpeechSynthesizer.createSynthesizer(context, new InitListener() {
            @Override
            public void onInit(int i) {
                // NEED TO DO NOTHING
            }
        });
    }

    public void say(VoiceBean ... voiceBeans){
        for (VoiceBean bean : voiceBeans){
            voiceBeanQueue.offer(bean);
        }
        nextSpeech();
    }

    private void nextSpeech(){
        curVoiceBean = voiceBeanQueue.poll();
        if(curVoiceBean == null){
            return;
        }
        setParam(curVoiceBean.isBoy() ? voicerLocalBoy : voicerLocalGirl);
        mTts.startSpeaking(curVoiceBean.getStr(), mTtsListener);
    }

    //获取发音人资源路径
    private String getResourcePath(String voice){
        StringBuffer tempBuffer = new StringBuffer();
        //合成通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(context, ResourceUtil.RESOURCE_TYPE.assets, "tts/common.jet"));
        tempBuffer.append(";");
        //发音人资源
        tempBuffer.append(ResourceUtil.generateResourcePath(context, ResourceUtil.RESOURCE_TYPE.assets, "tts/" + voice + ".jet"));
        return tempBuffer.toString();
    }

    private void setParam(String voice){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, "");

        //设置合成
        //设置使用本地引擎
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        //设置发音人资源路径
        mTts.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath(voice));
        //设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, voice);

        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "3");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");

        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }

    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            if (speechError == null) {//播放完成
                nextSpeech();
            } else if (speechError != null) {//播放错误

            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    public void onDestroy(){
        if( null != mTts ){
            // 退出时释放连接
            mTts.stopSpeaking();
            mTts.destroy();
        }
    }

}
