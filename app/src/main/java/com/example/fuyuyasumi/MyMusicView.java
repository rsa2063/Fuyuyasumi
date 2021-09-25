package com.example.fuyuyasumi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by zhangbin on 2016/2/15.
 */
public class MyMusicView extends View {
    Paint p = new Paint();
    int num;
    public MyMusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureL(widthMeasureSpec), measureL(heightMeasureSpec));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LinearGradient g = new LinearGradient(0, 0, 40, 395, Color.BLUE, Color.RED, Shader.TileMode.CLAMP);
        p.setShader(g);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 10; i++) {
            num = (new Random()).nextInt(395);
            canvas.drawRect(i * 40 + 5 , 395 - num, i * 40 + 35, 395, p);
        }
        postInvalidateDelayed(300);
    }

    private int measureL(int lMeasureSpce) {
        int result = 0;
        int specWidth = MeasureSpec.getSize(lMeasureSpce);
        int specMode = MeasureSpec.getMode(lMeasureSpce);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specWidth;
        } else {
            result = 400;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specWidth);
            }
        }
        return result;
    }
}
