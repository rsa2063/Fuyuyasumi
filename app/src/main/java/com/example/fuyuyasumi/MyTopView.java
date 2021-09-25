package com.example.fuyuyasumi;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by zhangbin on 2016/2/13.
 */
public class MyTopView extends RelativeLayout {
    private Button leftButton, rightButton;
    private TextView title;
    private OnTopViewListener listener;
    public MyTopView (Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0x9fffffff);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTopView);
        leftButton = new Button(context);
        rightButton = new Button(context);
        title = new TextView(context);

        leftButton.setAllCaps(false);//cancel all cap
        leftButton.setText(ta.getText(R.styleable.MyTopView_leftText));
        leftButton.setTextColor(ta.getColor(R.styleable.MyTopView_leftColor, 0));

        leftButton.setBackground(ta.getDrawable(R.styleable.MyTopView_leftBackground));
        title.setText(ta.getText(R.styleable.MyTopView_titleText));
        title.setTextColor(ta.getColor(R.styleable.MyTopView_titleColor, 0));
        title.setTextSize(ta.getDimension(R.styleable.MyTopView_titleSize, 10));
        rightButton.setText(ta.getText(R.styleable.MyTopView_rightText));
        rightButton.setTextColor(ta.getColor(R.styleable.MyTopView_rightColor, 0));
        rightButton.setBackground(ta.getDrawable(R.styleable.MyTopView_rightBackground));
        ta.recycle();
        LayoutParams paramsL = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        paramsL.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftButton, paramsL);
        LayoutParams paramsT = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        paramsT.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(title, paramsT);
        LayoutParams paramsR = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        paramsR.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightButton, paramsR);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });
    }

    public void setListener(OnTopViewListener listener) {
        this.listener = listener;
    }
    public interface OnTopViewListener {
        void leftClick();
        void rightClick();
    }
}
