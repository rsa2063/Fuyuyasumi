package com.example.fuyuyasumi;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class MyTextView extends TextView {
    private Paint mPaint;
    private LinearGradient mLinearGradient;
    private Matrix matrix;
    private int mViewWidth = 0;
    private int mTranslate = 0;
    public MyTextView(Context context){
        super(context);
    }
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(
                        0,
                        0,
                        mViewWidth,
                        0,
                        new int[]{
                                Color.BLUE, 0xffffffff,
                                Color.BLUE},
                        null,
                        Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                matrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*Paint p1 = new Paint();
        p1.setColor(getResources().getColor(android.R.color.background_dark));
        p1.setStyle(Paint.Style.FILL);
        Paint p2 = new Paint();
        //Button b1 = new Button(MyApplication.context);
        p2.setColor(Color.LTGRAY);
        p2.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), p1);
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, p2);
        canvas.save();
        canvas.translate(10, 10);
        super.onDraw(canvas);
        canvas.restore();*/
        super.onDraw(canvas);
        if (matrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate >  mViewWidth) {
                mTranslate = -mViewWidth;
            }
            matrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(matrix);
            postInvalidateDelayed(100);
        }
    }
}
