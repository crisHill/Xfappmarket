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

/**
 * Created by oop on 2018/2/11.
 */

public class Speaker {

    private final String TAG = "Speaker";
    private Context context;
    private SpeechSynthesizer mTts;
    private InitListener mTtsInitListener;

    // 本地发音人列表
    private String[] localVoicersEntries;
    private String[] localVoicersValue ;

    //缓冲进度
    private int mPercentForBuffering = 0;
    //播放进度
    private int mPercentForPlaying = 0;

    // 默认本地发音人
    public static String voicerLocalBoy="xiaofeng";
    public static String voicerLocalGirl="xiaoyan";

    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_LOCAL;

    private void showTip(final String str){
        System.out.println("showTip " + str);
    }

    public Speaker(Context context, SpeechSynthesizer mTts, InitListener mTtsInitListener, SynthesizerListener mTtsListener){
        this.context = context;
        this.mTts = mTts;
        this.mTtsInitListener = mTtsInitListener;
        this.mTtsListener = mTtsListener;

        // 本地发音人名称列表
        localVoicersEntries = context.getResources().getStringArray(R.array.voicer_local_entries);
        localVoicersValue = context.getResources().getStringArray(R.array.voicer_local_values);
    }

    public void say(String str, boolean boy){
        setParam(boy ? voicerLocalBoy : voicerLocalGirl);

        if(TextUtils.isEmpty(str)){
            str = "科大讯飞，让世界聆听我们的声音";
        }
        mTts.startSpeaking(str, mTtsListener);
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

    private SynthesizerListener mTtsListener;

}
