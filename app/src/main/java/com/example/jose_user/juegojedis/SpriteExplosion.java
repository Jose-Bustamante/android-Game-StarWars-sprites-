package com.example.jose_user.juegojedis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.List;

/**
 * Created by Jose-User on 05/02/2016.
 */
public class SpriteExplosion {
    private int x;
    private int y;
    private Bitmap bmp;
    private int life = 10;
    private List<SpriteExplosion> temps;
    private int currentFrame = 0, width, height;
    private static final int BMP_COLUMNS = 5;


    public SpriteExplosion(List<SpriteExplosion> temps, GameView gameView, int x, int y) {

        this.x = x;
        this.y = y;
        this.bmp = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.explosion);
        this.temps = temps;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight();
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = 0;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    private void update() {


        if(currentFrame == 4){
            life = 0;
        }
        currentFrame = ++currentFrame % BMP_COLUMNS;
        if (--life < 1) {
            temps.remove(this);
        }
    }
}
