package com.example.fuyuyasumi;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class PopupActivity extends AppCompatActivity {
    private PopupWindow popup;
    private LayoutInflater inflater;
    private View mainL;
    private View popupL;
    private IntentFilter filter;
    private ActionReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupo);

        initPopup();

        Button show = (Button) findViewById(R.id.b_1);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        Button dismiss = (Button) findViewById(R.id.b_2);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        filter = new IntentFilter();
        filter.addAction("com.example.fuyuyasumi.TEST");
        receiver = new ActionReceiver();
        //registerReceiver(receiver, filter);
        Button b3 = (Button) findViewById(R.id.b_3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.fuyuyasumi.TEST");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(receiver);
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(getApplication(), "on key down ", Toast.LENGTH_SHORT).show();
        if (!popup.isShowing() && keyCode == KeyEvent.KEYCODE_MENU) {
            show();
        } else {
            popup.dismiss();
        }

        if (popup.isShowing() && keyCode == KeyEvent.KEYCODE_BACK) {
            popup.dismiss();
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }*/

    private void initPopup() {
        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        mainL = inflater.inflate(R.layout.activity_popupo, null);
        popupL = inflater.inflate(R.layout.activity_popup, null);

        popup = new PopupWindow(popupL, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void show() {
        popup.showAtLocation(mainL, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popup.update();
    }

}
