package com.example.fuyuyasumi;

import android.app.Activity;
//import android.content.SharedPreferences;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.net.URISyntaxException;

public class EncryptFilenameActivity extends AppCompatActivity {
    Button button_encrypt;
    Button button_decrypt;
    Button button_chooseDirectory;
    Intent intent = null;
    boolean state = true;
    String desDirectory = "";

    SharedPreferences.Editor editor = null;
    SharedPreferences pref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt_filename);

        pref = getSharedPreferences("data", MODE_PRIVATE);

        //todo  set button type
        button_encrypt = (Button) findViewById(R.id.button_encrypt);
        button_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.encryptFilename(desDirectory);
                //notice1.encryptFilename();
                button_decrypt.setEnabled(true);
                button_encrypt.setEnabled(false);
                state = false;
                //new File("/mnt/ext_sdcard/tudou/新文件夹/testnull").renameTo(new File("/mnt/ext_sdcard/tudou/新文件夹/whatthehell"));

                //test base64
                try {
                    String base64 = Base64.encodeToString("test.mp4".getBytes("utf-8"), Base64.DEFAULT);
                    Log.d("file base 64 test", "test.mp4 after base64 is" + base64);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        button_decrypt = (Button) findViewById(R.id.button_decrypt);
        button_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.decryptFilename(desDirectory);
                //notice1.decryptFilename();
                button_decrypt.setEnabled(false);
                button_encrypt.setEnabled(true);
                state = true;
            }
        });

        button_chooseDirectory = (Button) findViewById(R.id.button_choose_directory);
        button_chooseDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        desDirectory = pref.getString("des", "");
        if(pref.getBoolean("state", true)) {
            button_decrypt.setEnabled(false);
            button_encrypt.setEnabled(true);
        } else {
            button_decrypt.setEnabled(true);
            button_encrypt.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putBoolean("state", state);
        editor.putString("des", desDirectory);
        //editor.commit();
        editor.apply();
        super.onDestroy();
    }

    private void showFileChooser() {
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    1);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String url;
            /*try {
                url = FileUtils.getPath(getActivity(), uri);
                Log.i("ht", "url" + url);
                String fileName = url.substring(url.lastIndexOf("/") + 1);
                intent = new Intent(getApplicationContext(), UploadServices.class);
                intent.putExtra("fileName", fileName);
                intent.putExtra("url", url);
                intent.putExtra("type ", "");
                intent.putExtra("fuid", "");
                intent.putExtra("type", "");

                getApplicationContext().startService(intent);

            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            desDirectory = uri.getPath().substring(0, uri.getPath().lastIndexOf('/'));
            Log.d("file test", "directoy is" + uri.getPath().substring(0, uri.getPath().lastIndexOf('/')));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
