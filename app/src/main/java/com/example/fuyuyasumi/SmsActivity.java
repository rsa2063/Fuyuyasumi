package com.example.fuyuyasumi;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SmsActivity extends AppCompatActivity {

    private EditText num;
    private EditText content;
    private Button send;
    private static String SMS_ACTION = "sms_success";
    private IntentFilter filter;
    private SmsRe receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        num = (EditText) findViewById(R.id.send_num);
        content = (EditText) findViewById(R.id.send_content);

        Button call = (Button) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:98765431206"));
                startActivity(intent);
            }
        });

        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numR = num.getText().toString();
                String contentR = content.getText().toString();
                Intent intent = new Intent(SMS_ACTION);
                PendingIntent pi = PendingIntent.getBroadcast(MyApplication.context, 0, intent, 0);
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(numR, null, contentR, pi, null);
            }
        });

        filter = new IntentFilter(SMS_ACTION);
        receiver = new SmsRe();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    class SmsRe extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            notice.notify("delivery succeed");
            Log.d("account", "delivery succeed");
        }
    }
}
