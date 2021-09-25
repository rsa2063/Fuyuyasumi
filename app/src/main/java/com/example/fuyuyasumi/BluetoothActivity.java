package com.example.fuyuyasumi;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BluetoothActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        Button openBlue = (Button) findViewById(R.id.open_bluetooth);
        openBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //startActivity(intent);
                notice.notify("bluetooth is opening");
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (adapter == null) {
                    notice.notify("bluetooth is not avaiable");
                    return;
                }
                adapter.enable();
            }
        });

        Button shutdown = (Button) findViewById(R.id.shutdown_bluetooth);
        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (adapter == null) {
                    notice.notify("bluetooth is not avaiable");
                    return;
                }
                adapter.disable();
                notice.notify("bluetooth has shutdown & name is " + adapter.getName() );
            }
        });

        Button getA = (Button) findViewById(R.id.get_accounts);
        getA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountManager manager = AccountManager.get(MyApplication.context);
                Account[] accounts = manager.getAccounts();
                for (Account a : accounts) {
                    //notice.notify("name : " + a.name + "\");
                    Log.d("account","name : " + a.name + "\ntype : " + a.type);
                }
            }
        });
    }
}
