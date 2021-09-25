package com.example.fuyuyasumi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhangbin on 2016/1/25.
 */
public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }
    public MyView(Context context, AttributeSet attr) {
        super(context, attr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, 100, 100, paint);
        canvas.drawText("my view", 2, 20, paint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.e);
        canvas.drawBitmap(bitmap, 100, 0, paint);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 100, 100, 200, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureL(widthMeasureSpec), heightMeasureSpec);
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
