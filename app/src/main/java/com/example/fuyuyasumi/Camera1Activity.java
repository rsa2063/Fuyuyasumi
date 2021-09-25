package com.example.fuyuyasumi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Camera1Activity extends AppCompatActivity implements SurfaceHolder.Callback{

    private Camera c;
    private Button tori2;
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (c == null) {
                try {
                    c = Camera.open();
                    c.setPreviewDisplay(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Camera.Parameters p = c.getParameters();
            p.setPictureFormat(PixelFormat.JPEG);
            c.setDisplayOrientation(90);
            c.setParameters(p);
            c.startPreview();


        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            c.stopPreview();
            c.release();
            c = null;
        }
    };

    Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap p;
            p = BitmapFactory.decodeByteArray(data, 0, data.length);
            File f =   new File((MyApplication.context.getExternalFilesDirs(null))[1], "a.jpg");
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));
                p.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera1);

        //surface & camera & button
        tori2 = (Button) findViewById(R.id.tori2);
        tori2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera.Parameters p = c.getParameters();
                p.setPictureFormat(PixelFormat.JPEG);
                p.setRotation(90);
                p.setJpegQuality(100);
                c.setDisplayOrientation(90);
                c.setParameters(p);
                c.takePicture(null, null, jpeg);
            }
        });
        surfaceView = (SurfaceView) findViewById(R.id.camera_view);
        holder = surfaceView.getHolder();
        holder.addCallback(callback);
        holder.setKeepScreenOn(true);

        Button t = (Button) findViewById(R.id.tori);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(MyApplication.context.getExternalFilesDir(null), "t.jpg")));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (c == null) {
            try {
                c = Camera.open();
                c.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters p = c.getParameters();
        p.setPictureFormat(PixelFormat.JPEG);
        c.setDisplayOrientation(90);
        c.setParameters(p);
        c.startPreview();


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        c.stopPreview();
        c.release();
        c = null;
    }
}
