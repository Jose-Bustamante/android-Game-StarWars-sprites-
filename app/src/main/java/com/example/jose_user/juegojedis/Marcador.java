package com.example.jose_user.juegojedis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by Jose-User on 29/01/2016.
 */
public class Marcador {


    private int x; //posicion del marcador en X
    private int y; //posicion del marcador en Y
    private GameView gameView;
    private Paint pincel;
    private int score = 00000;



    public Marcador(GameView game, int x, int y) {
        this.y = y;
        this.x = x;
        this.gameView = game;


        /*
        float GESTURE_THRESHOLD_DIP = 16.0f;

        // Convert the dips to pixels
        final float scale = gameView.getContext().getResources().getDisplayMetrics().density; //todo esto es para coger la densidad de la pantalla para que no se vea grande en pantallas pequñas
        int mGestureThreshold = (int) (GESTURE_THRESHOLD_DIP * scale + 70.5f);*/
        int screenWidth = game.getWidthScreen();
        int screenHeight = game.getHeightScreen();
        Log.e(""+screenWidth, ""+screenHeight);
        pincel = new Paint();
        pincel.setColor(Color.RED);
        pincel.setStrokeWidth(screenWidth/15);
        pincel.setTextSize(screenHeight/7);
    }

    private void update() {
        //cuando se mate a un enemigo actualozar con update el marcador.
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawText(Integer.toString(score), x, y, pincel);

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        //Log.e("puntuacion: ", ""+score);
    }







}
