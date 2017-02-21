package com.example.jose_user.juegojedis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

/**
 * Created by Jose-User on 04/02/2016.
 */
public class Vidas {

    private GameView gameView;
    private Bitmap bmp;
    private int width;
    private int height;
    private int vida, x, y;


    public Vidas(GameView gameView, Bitmap bmp) {
        this.gameView=gameView;
        this.bmp=bmp;
        this.width = bmp.getWidth() / 1;
        this.height = bmp.getHeight() / 4;
        this.x = gameView.widthScreen - (gameView.widthScreen/100)*20;
        this.y = (gameView.heightScreen/100)*5;
        this.vida = 0;


    }


    public void setVida(int vidas) {
        this.vida = vidas;
    }

    public int getVidas(){return this.vida;}

    public void onDraw(Canvas canvas) {


        int srcX = 0;
        int srcY = vida * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
        //Log.e("vidas actuales", ""+vida);

    }


}

