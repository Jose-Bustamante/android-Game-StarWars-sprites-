package com.example.jose_user.juegojedis;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    GameView game;
    private SensorManager sensorManager;
    private Sensor acelerometerSensor;
    private int veloX, veloY;
    private long timeMillis, timeChangePos;
    ArrayList<MediaPlayer> arraySounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        arraySounds = new ArrayList<>();
        arraySounds = getSounds();
        game = new GameView(this, width, height, arraySounds);
        setContentView(game);

        sensorManager = (SensorManager) getSystemService(
                SENSOR_SERVICE);

        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        if (!listaSensores.isEmpty()) {
            // Cogemos el primer sensor de tipo TYPE_ACCELEROMETER que tenga el dispositivo
            acelerometerSensor = listaSensores.get(0);
            sensorManager.registerListener(this, acelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        super.onPause();
        sensorManager.unregisterListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //sobreescribir onBackPressed

    @Override
    public void onSensorChanged(SensorEvent event) {

            synchronized (this) {
                switch(event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        //hacer cositas
                        veloX = (int) event.values[1]; // coje rangos de 10 a -10
                        veloY = (int) event.values[0]; // coje valores de 10 a -10
                        game.cambiarVelocidadNave(veloX,veloY);
                        //Log.e("posicion X: ", ""+veloX);
                        //Log.e("posicion X: ", ""+veloY);
                        break;
                }
            }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public ArrayList<MediaPlayer> getSounds(){
        MediaPlayer swordStartSound = MediaPlayer.create(getApplicationContext(), R.raw.swordstart);
        arraySounds.add(swordStartSound);
        MediaPlayer swordSound = MediaPlayer.create(getApplicationContext(), R.raw.swordcrash);
        arraySounds.add(swordSound);
        return arraySounds;
    }

    public int getVeloX() {
        return veloX;
    }

    public void setVeloX(int veloX) {
        this.veloX = veloX;
    }

    public int getVeloY() {
        return veloY;
    }

    public void setVeloY(int veloY) {
        this.veloY = veloY;
    }
}
