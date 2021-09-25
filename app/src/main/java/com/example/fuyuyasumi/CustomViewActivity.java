package com.example.fuyuyasumi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_view);

        MyTopView v = (MyTopView) findViewById(R.id.topview);
        v.setListener(new MyTopView.OnTopViewListener() {
            @Override
            public void leftClick() {
                notice.notify("you clicked left Button");
            }

            @Override
            public void rightClick() {
                notice.notify("you clicked rightt Button");
            }
        });
    }
}
