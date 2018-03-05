package com.zls.xfappmarket.e3.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.zls.xfappmarket.e3.itf.MsgReceiver;

import java.io.IOException;
import java.util.Random;

/**
 * Created by oop on 2018/3/2.
 */

public class MusicManager implements MsgReceiver {

    private static MusicManager INSTANCE;
    public static MusicManager getINSTANCE(Context context){
        if(INSTANCE == null){
            INSTANCE = new MusicManager(context);
        }
        return INSTANCE;
    }

    private String[] musics = {
            "music/1947.wav",
            "music/Beatrice La Belle.mp3",
            "music/Enchanted.wav",
            "music/Feeling Lucky.mp3",
            "music/Glory.wav",
            "music/kiss the rain.mp3",
            "music/marryme.wav",
            "music/StrollInThePark.wav",
            "music/Summer Days No Drm.mp3",
            "music/wedding.mp3",
            "music/Wonder.mp3",
    };

    private Context context;
    private MediaPlayer mediaPlayer;

    private MusicManager(Context context){
        this.context = context;
        MsgManager.getINSTANCE().register(MsgManager.Type.START_OR_END_MUSIC, this);
    }

    public void start(){

        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前音乐音量
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获取最大声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0); // 设置为最大声音，可通过SeekBar更改音量大小

        AssetFileDescriptor fileDescriptor;
        try {
            String fileName = musics[new Random().nextInt(musics.length)];
            fileDescriptor = context.getAssets().openFd(fileName);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void end(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
        mediaPlayer = null;
    }

    @Override
    public void onReceive(int type, Object obj) {
        if((boolean)obj){
            start();
        }else {
            end();
        }
    }
}
