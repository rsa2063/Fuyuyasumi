package com.example.fuyuyasumi;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity {

    private SensorManager manager;
    private float  lastDegree = 0;
    private float[] accValues = new float[3];
    private float[] magValues = new float[3];
    int i = 0;
    private static final String TAG = "sensor";

    private ImageView front;
    private TextView degree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        front = (ImageView)findViewById(R.id.front);
        degree = (TextView)findViewById(R.id.degree);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensorAcc = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensorMagnet = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor sensorOr = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//        manager.registerListener(l1, sensorOr, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(listener, sensorAcc, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null) {
//            manager.unregisterListener(l1);
            manager.unregisterListener(listener);
        }
    }

    private SensorEventListener l1 = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Log.d("987z", "      " + event.values[0]);

            float now = -event.values[0];
            if (Math.abs(lastDegree - now) >= 5) {
                RotateAnimation animation = new RotateAnimation(lastDegree, now, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(300);
                animation.setFillAfter(true);
                front.startAnimation(animation);
                lastDegree = now;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accValues = event.values.clone();

            } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magValues = event.values.clone();

            }

            float[] r = new float[9];
            float[] result = new float[3];
            SensorManager.getRotationMatrix(r, null, accValues, magValues);
            SensorManager.getOrientation(r, result);


            float now = -(float) Math.toDegrees(result[0]);
            if(i++ % 100 == 1) {
                Log.d(TAG, "      " + now);
                if(-now >= -5 && -now < 5){
                    Log.i(TAG, "正北");
                }
                else if(-now >= 5 && -now < 85){
                    Log.i(TAG, "东北");
                }
                else if(-now >= 85 && -now <=95){
                    Log.i(TAG, "正东");
                }
                else if(-now >= 95 && -now <175){
                    Log.i(TAG, "东南");
                }
                else if((-now >= 175 && -now <= 180) || (-now) >= -180 && -now < -175){
                    Log.i(TAG, "正南");
                }
                else if(-now >= -175 && -now <-95){
                    Log.i(TAG, "西南");
                }
                else if(-now >= -95 && -now < -85){
                    Log.i(TAG, "正西");
                }
                else if(-now >= -85 && -now <-5){
                    Log.i(TAG, "西北");
                }
            }
            

            
            if (Math.abs(lastDegree - now) >= 5) {
                degree.setText(-now + "");
                RotateAnimation animation = new RotateAnimation(lastDegree, now, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                front.startAnimation(animation);
                lastDegree = now;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}