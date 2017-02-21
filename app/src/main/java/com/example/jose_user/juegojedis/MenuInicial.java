package com.example.jose_user.juegojedis;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



public class MenuInicial extends AppCompatActivity {

    private final int DURACION_SPLASH = 3000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                setContentView(R.layout.activity_menu_inicial);
            }

            ;
        }, DURACION_SPLASH);



        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.swordstart);
        mp.start();
    }


    public void btnStart(View v){
        //llamar a game View
        Intent juego = new Intent(this, MainActivity.class);
        startActivity(juego);
    }

    public void btnScore(View v){
        //llamar a intent score
        Intent i = new Intent(this, Scores.class);
        i.putExtra("score", 0);
        startActivity(i);
    }

    public void btnOpction(View v){
        //llamar a SharedPreferences
        Intent options = new Intent(this, Settings.class);
        startActivity(options);
    }

}
