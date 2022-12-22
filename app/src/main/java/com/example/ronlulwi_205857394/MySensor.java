package com.example.ronlulwi_205857394;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class MySensor {

    public interface CallBack_movement {
        void moveTo(int loc);
        void setSpeed(int speed);
    }

    private SensorManager mySensorManager;
    private Sensor sensor;
    private CallBack_movement callBack_movement;

    public MySensor(CallBack_movement callBack_movement) {
        Context context = MyApp.getAppContext();
        this.mySensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = this.mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBack_movement = callBack_movement;
    }

    public void start() {
        mySensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        mySensorManager.unregisterListener(sensorEventListener);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            //Log.d("event.values[0];", "" + event.values[1]);
            calculateMovement(x, y);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void calculateMovement(float x, float y) {
        // TODO calc movement !
        int nextMove = 0;
        int speed = 0;

        if(x < -5 )
            nextMove=4;
        else if(-5 < x && x < -2.5)
           nextMove=3;
        else if(-2.5 < x && x < 2.5)
           nextMove=2;
        else if(-2.5 < x && x < 5)
           nextMove=1;
        else if(x > 5)
            nextMove=0;

        if(y < -1)
            speed = 200;
        else if(y < 1 && y>-1)
            speed = 450;
        else if(y > 1)
            speed = 700;

        if (callBack_movement != null) {
            callBack_movement.moveTo(nextMove);
            callBack_movement.setSpeed(speed);
        }

    }
}





