package com.example.fuyuyasumi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by zhangbin on 2016/1/23.
 */
public class ActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //notice.notify("action  receiver dynamicly");
        Toast.makeText(MyApplication.context, "action  receiver dynamicly", Toast.LENGTH_SHORT).show();
    }
}
