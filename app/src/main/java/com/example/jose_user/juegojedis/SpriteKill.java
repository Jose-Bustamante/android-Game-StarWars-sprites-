package com.example.jose_user.juegojedis;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

/**
 * Created by Jose-User on 05/02/2016.
 */
public class SpriteKill {
    private float x;
    private float y;
    private Bitmap bmp;
    private int life = 15;
    private List<SpriteKill> temps;

    public SpriteKill(List<SpriteKill> temps, GameView gameView, float x, float y, Bitmap bmp) {

        this.x = x;
        this.y = y;
        this.bmp = bmp;
        this.temps = temps;
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }

    private void update() {
        if (--life < 1) {
            temps.remove(this);
        }
    }
}
