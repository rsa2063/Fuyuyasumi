package com.example.fuyuyasumi;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.os.StatFs;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Formatter;

public class MainActivity extends AppCompatActivity {
    ListView vv;
    ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView memInfo = (TextView) findViewById(R.id.memory_info);
        ActivityManager ma = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        ma.getMemoryInfo(info);
        memInfo.setText("avaiable memory is " + android.text.format.Formatter.formatFileSize(getApplicationContext(), info.availMem) +
                "\ntotal memory is " + android.text.format.Formatter.formatFileSize(getApplicationContext(), info.totalMem));

        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            Log.d("test", "0current position is " + cursor.getPosition());
            while (cursor.moveToNext()) {
                Log.d("test", "1current position is " + cursor.getPosition());
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.d("test", "2 name&number " + name + "|" + number);

            }
        } catch (Exception e) {
            Log.d("test", " error" + e.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        String externalDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d("test", "0   -" + externalDirectory);
        String directoryState = Environment.getExternalStorageState();
        Log.d("test", "1   -" + directoryState + "|equeals.MEDIA_MOUNTED" + directoryState.equals(Environment.MEDIA_MOUNTED));

        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        Log.d("test", "4 -total block is " + statFs.getBlockCount() + "  block size is " + statFs.getBlockSize() +
        "available block is " + statFs.getAvailableBlocks() + "total size is " + statFs.getBlockSize() * statFs.getBlockCount()
        + "availble size is " + statFs.getAvailableBlocks() * statFs.getBlockSize());

        /*File [] folders = MyApplication.context.getExternalFilesDirs(null);
        if(folders.length > 1) {
            File testFile = new File(folders[1], "/testfile.txt");
            File testFile2 = new File("/storage/sdcard1/testfile.txt");
            File testFile3 = new File("/storage/sdcard1/a.txt");
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            try {
                //testFile2.createNewFile();
                //testFile2.setWritable(true);
                FileOutputStream out = new FileOutputStream(testFile);
                out.write("test location".getBytes());
                //Log.d("test", "1" + Environment.getDataDirectory().getPath());

                FileInputStream in = new FileInputStream(testFile3);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder builder = new StringBuilder();
                String t;
                while ((t = reader.readLine()) != null) {
                    builder.append(t);
                }
                //File [] folders = MyApplication.context.getExternalFilesDirs(null);

                Log.d("test", "3" + Environment.getDataDirectory().getPath() + "\npublic derectory " +
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) +
                        "\ngetexternalFilesdir " + MyApplication.context.getExternalFilesDir(null));
                for (File f : folders) {
                    Log.d("test", "31 " + f + "\n");
                }
                out.write(builder.toString().getBytes());
                out.close();
                reader.close();
            } catch (Exception e) {
                Log.d("test", "2   - error" + e.getMessage());
            } finally {

            }
        }*/


        Button b1 = (Button) findViewById(R.id.b_1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, ImageSwitcherActivity.class);
                //Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                Intent intent = new Intent(MainActivity.this, CustomViewActivity.class);
                //Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
                //Intent intent = new Intent(MainActivity.this, SmsActivity.class);
                //Intent intent = new Intent(MainActivity.this, Camera1Activity.class);
                startActivity(intent);
            }
        });
        Button b2 = (Button) findViewById(R.id.b_2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyScrollViewGroupActivity.class);
                startActivity(intent);
            }
        });

        Button b3 = (Button)findViewById(R.id.b_3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EncryptFilenameActivity.class);
                startActivity(intent);
            }
        });

        Button b4 = (Button)findViewById(R.id.b_4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompassActivity.class);
                startActivity(intent);
            }
        });
    }


}
