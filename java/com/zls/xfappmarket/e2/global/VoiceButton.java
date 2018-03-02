package com.zls.xfappmarket.e2.global;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zls.xfappmarket.e2.data.Const;
import com.zls.xfappmarket.e2.itf.MsgReceiver;
import com.zls.xfappmarket.e2.util.GlbDataHolder;
import com.zls.xfappmarket.e2.util.MsgManager;

/**
 * TODO: document your custom view class.
 */
public class VoiceButton extends View implements MsgReceiver{

    private final int COLOR_ONE = Color.parseColor("#87CEFA");
    private final int COLOR_TWO = Color.parseColor("#55FFCC");
    private final int WHAT = Const.MsgWhat.REFRESH_LISTEN_BUTTON;
    private final int SLEEP_TIME = 150;
    private final String STR_ON = "listening";
    private final String STR_OFF = "speak";
    private final int ALPHA_OFF = 50;
    private final int ALPHA_ON = 150;

    private Paint paint;
    private boolean on;
    private int centerX, centerY;
    private int contentWidth, contentHeight;
    private int maxCircleRadius, minCircleRadius;
    private int ringWidth;
    private int curRadius;
    private int curColor;
    private String curText;
    private int curAlpha;
    private float touchX, touchY;
    private float downX, downY;
    private boolean touching = false;
    private Context context;

    public VoiceButton(Context context) {
        this(context, null);
    }

    public VoiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);

        MsgManager.getINSTANCE().register(MsgManager.Type.HIDE_OR_SHOW_VOICE_BUTTON, this);
        MsgManager.getINSTANCE().register(MsgManager.Type.TURN_ON_OR_OFF_VOICE_BUTTON, this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public boolean handleTouch(MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            touchX = motionEvent.getX();
            touchY = motionEvent.getY();
            downX = touchX;
            downY = touchY;

            float left = getX();
            float right = left + getWidth();
            float top = getY();
            float bottom = top + getHeight();
            if(touchX >= left && touchX <= right && touchY >= top && touchY <= bottom){
                touching = true;
            }

        }else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){

            if(touching){
                float dx = motionEvent.getX() - touchX;
                float dy = motionEvent.getY() - touchY;

                if(dx * dx + dy * dy > 10 * 10){
                    move(dx, dy);
                    touchX = motionEvent.getX();
                    touchY = motionEvent.getY();
                }
            }

        }

        if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            boolean handled = touching;
            touching = false;

            if(handled){
                if(downX == motionEvent.getX() && downY == motionEvent.getY()){
                    turn(true);
                }
                return true;
            }

        }else {
            if(touching){
                return true;
            }
        }

        return false;
    }

    private void move(float dx, float dy){
        float aim = this.getX() + dx;
        if(aim < 1){
            aim = 1;
        }else if(aim > GlbDataHolder.halfStageWidth - getWidth() - 1){
            aim = GlbDataHolder.halfStageWidth - getWidth() - 1;
        }
        this.setX(aim);

        aim = this.getY() + dy;
        if(aim < 1){
            aim = 1;
        }else if(aim > GlbDataHolder.stageHeight - getHeight() - 1){
            aim = GlbDataHolder.stageHeight - getHeight() - 1;
        }
        this.setY(aim);
    }

    public void turn(boolean on){
        if(this.on == on){
            return;
        }
        this.on = on;
        if(on){
            curText = STR_ON;
            curAlpha = ALPHA_ON;
            startInvalidate();
            Planner.getINSTANCE(context).startListen();
        }else {
            reset();
        }
    }

    private void reset(){
        curRadius = minCircleRadius;
        curText = STR_OFF;
        curColor = COLOR_ONE;
        curAlpha = ALPHA_OFF;
        invalidate();
    }
    public void nextDraw(){
        curColor =  curColor == COLOR_ONE ? COLOR_TWO : COLOR_ONE;
        curRadius = curRadius + ((maxCircleRadius - minCircleRadius) / 5) > maxCircleRadius ? minCircleRadius : curRadius + ((maxCircleRadius - minCircleRadius) / 5);
        invalidate();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == WHAT){
                nextDraw();
            }
        }
    };

    private void startInvalidate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(on){
                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(WHAT);
                }
            }
        }).start();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        contentWidth = getWidth() - paddingLeft - paddingRight;
        contentHeight = getHeight() - paddingTop - paddingBottom;

        ringWidth = 5;
        int small = contentHeight > contentWidth ? contentWidth : contentHeight;

        maxCircleRadius = small / 2 - ringWidth;
        minCircleRadius = small / 10 + ringWidth;

        if(maxCircleRadius <= minCircleRadius){
            throw new IllegalArgumentException("invalidate dimens for this element");
        }

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        curRadius = minCircleRadius;
        curColor = COLOR_ONE;
        curText = STR_OFF;
        curAlpha = ALPHA_OFF;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //drawRing(canvas, centerX, centerY, minCircleRadius, Color.GREEN);
        //drawRing(canvas, centerX, centerY, maxCircleRadius, Color.RED);

        drawBg(canvas);
        drawRing(canvas);
        drawText(canvas);

        super.onDraw(canvas);
    }

    private void drawBg(Canvas canvas){
        paint.setColor(Color.parseColor("#88323232"));
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(curAlpha);//取值范围为0~255，数值越小越透明
        canvas.drawCircle(centerX, centerY, maxCircleRadius, paint);
    }

    private void drawRing(Canvas canvas){
        paint.setColor(curColor);
        paint.setStrokeWidth(ringWidth);//环的宽度
        paint.setStyle(Paint.Style.STROKE); //绘制空心圆
        canvas.drawCircle(centerX, centerY, curRadius, paint);
    }

    private void drawText(Canvas canvas){
        paint.setColor(Color.BLACK);
        paint.setTextSize(minCircleRadius);
        paint.setStyle(Paint.Style.FILL);

        //拿到字符串的宽度
        /*float stringWidth = paint.measureText(text);
        float x =(getWidth() - stringWidth) / 2;
        canvas.drawText(text, x, getHeight()/2, paint);
*/
        Rect rect = new Rect();
        paint.getTextBounds(curText, 0, curText.length(), rect);
        float x = centerX - (rect.width() / 2);
        float y = centerY;
        canvas.drawText(curText, x, y, paint);

    }

    @Override
    public void onReceive(int type, Object obj) {

        if(type == MsgManager.Type.HIDE_OR_SHOW_VOICE_BUTTON){
            hide((Boolean) obj);
            return;
        }

        if(type == MsgManager.Type.TURN_ON_OR_OFF_VOICE_BUTTON){
            turn((Boolean) obj);
            return;
        }

    }

    public void hide(boolean hide){
        if(hide){
            this.setVisibility(GONE);
        }else{
            this.setVisibility(VISIBLE);
        }
    }

}
