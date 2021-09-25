package com.example.fuyuyasumi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class ImageSwitcherActivity extends AppCompatActivity {
    private ImageSwitcher switcher;
    private Button pre;
    private Button next;
    private int[] images= {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private int mIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);

        switcher = (ImageSwitcher) findViewById(R.id.image_s);
        pre = (Button) findViewById(R.id.pre_image);
        next = (Button) findViewById(R.id.next_image);

        switcher.setFactory(factory);
        switcher.setImageResource(images[mIndex]);

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex--;
                if (mIndex >= 0) {
                    switcher.setImageResource(images[mIndex]);
                } else {
                    mIndex++;
                    Toast.makeText(getApplication(), "this is already first image ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex++;
                if (mIndex < images.length) {
                    switcher.setImageResource(images[mIndex]);
                } else {
                    mIndex--;
                    Toast.makeText(getApplication(), "this is already last image ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ViewSwitcher.ViewFactory factory = new ViewSwitcher.ViewFactory() {
        @Override
        public View makeView() {
            return new ImageView(ImageSwitcherActivity.this);
        }
    };
}
