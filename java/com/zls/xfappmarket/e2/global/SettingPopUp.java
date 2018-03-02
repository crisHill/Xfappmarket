package com.zls.xfappmarket.e2.global;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.zls.xfappmarket.R;
import com.zls.xfappmarket.e2.itf.MsgReceiver;
import com.zls.xfappmarket.e2.util.GlbDataHolder;
import com.zls.xfappmarket.e2.util.MsgManager;

/**
 * Created by oop on 2018/2/28.
 */

public class SettingPopUp implements MsgReceiver{

    private final float WIDTH_RATIO = 0.5F;
    private Context context;
    private View popView;
    private PopupWindow popupWindow;
    private Button confirm;

    private View root;

    public SettingPopUp(Context context, View root){
        this.context = context;
        this.root = root;
        initViews();
    }

    private void show(){
        popupWindow.showAtLocation(root, Gravity.LEFT, 0, 0);
    }

    private void hide(){
        popupWindow.dismiss();
    }


    private void initViews() {

        popView = LayoutInflater.from(context).inflate(R.layout.setting_popup, null);
        popView.setFocusable(true);
        popView.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(popView, (int) (GlbDataHolder.halfStageWidth * WIDTH_RATIO), ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupwindow_anim_style_from_left);

        confirm = (Button) popView.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });

        Button hideVoiceButton = (Button) popView.findViewById(R.id.hideVoiceButton);
        hideVoiceButton.setTag(false);
        hideVoiceButton.setOnClickListener(clickListener);

        Button startFlower = (Button) popView.findViewById(R.id.startFlower);
        startFlower.setTag(false);
        startFlower.setOnClickListener(clickListener);

        Button startMusic = (Button) popView.findViewById(R.id.startMusic);
        startMusic.setTag(false);
        startMusic.setOnClickListener(clickListener);

        Button whatToDo = (Button) popView.findViewById(R.id.whatToDo);
        whatToDo.setOnClickListener(clickListener);

        Button toFindGirl = (Button) popView.findViewById(R.id.toFindGirl);
        toFindGirl.setOnClickListener(clickListener);

        Button toReachGirl = (Button) popView.findViewById(R.id.toReachGirl);
        toReachGirl.setOnClickListener(clickListener);

        Button whatToSay = (Button) popView.findViewById(R.id.whatToSay);
        whatToSay.setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.confirm:
                    hide();
                    break;
                case R.id.hideVoiceButton:
                    boolean tag = !(boolean) view.getTag();
                    view.setTag(tag);
                    MsgManager.getINSTANCE().inform(MsgManager.Type.HIDE_OR_SHOW_VOICE_BUTTON, tag);
                    if(tag){
                        ((Button)view).setText("显示语音按钮");
                    }else {
                        ((Button)view).setText("隐藏语音按钮");
                    }
                    break;
                case R.id.startFlower:
                    boolean tag2 = !(boolean) view.getTag();
                    view.setTag(tag2);
                    MsgManager.getINSTANCE().inform(MsgManager.Type.START_OR_END_FLOWER, tag2);
                    if(tag2){
                        ((Button)view).setText("鲜花雨停");
                    }else {
                        ((Button)view).setText("鲜花雨起");
                    }
                    break;
                case R.id.startMusic:
                    boolean tag3 = !(boolean) view.getTag();
                    view.setTag(tag3);
                    MsgManager.getINSTANCE().inform(MsgManager.Type.START_OR_END_MUSIC, tag3);
                    if(tag3){
                        ((Button)view).setText("停止音乐");
                    }else {
                        ((Button)view).setText("播放音乐");
                    }
                    break;
                case R.id.whatToDo:
                    MsgManager.getINSTANCE().inform(MsgManager.Type.ASK_WHAT_TO_DO, null);
                    break;
                case R.id.toFindGirl:
                    MsgManager.getINSTANCE().inform(MsgManager.Type.ASK_TO_FIND_GIRL, null);
                    break;
                case R.id.toReachGirl:
                    MsgManager.getINSTANCE().inform(MsgManager.Type.ASK_TO_REACH_GIRL, null);
                    break;
                case R.id.whatToSay:
                    MsgManager.getINSTANCE().inform(MsgManager.Type.ASK_WHAT_TO_SAY, null);
                    break;
            }
        }
    };

    @Override
    public void onReceive(int type, Object obj) {
        if(type == MsgManager.Type.SHOW_SETTING){
            show();
            return;
        }
    }
}
