package com.example.jose_user.juegojedis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

/**
 * Created by Jose-User on 25/01/2016.
 */
public class Nave {
    private int x = 0;
    private int y = 0;
    private int xSpeed = 5;
    private int ySpeed;
    private GameView gameView;
    private Bitmap bmp;
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 4;
    private int currentFrame = 0;
    private int width;
    private int height;


    public Nave(GameView gameView, Bitmap bmp, int x, int y) {
        this.gameView=gameView;
        this.bmp=bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.x = x;
        this.y = y;
        Random rnd = new Random();
        xSpeed = rnd.nextInt(10)-2;
        ySpeed = rnd.nextInt(10)-2;
    }

    private void update() {

        controlVelocidadX();
        x = x + xSpeed;
        if ( x + xSpeed < 0) {
            xSpeed = 0;
            x=0;
        }if(x > gameView.getWidth() - width - xSpeed ){
            xSpeed = 0;
            x = gameView.getWidth() -width;
        }

        controlVelocidadY();
        y = y + ySpeed;
        if ( y + ySpeed < 0) {
            ySpeed = 0;
            y=0;
        }if(y > gameView.getHeight() - height - ySpeed ){
            ySpeed = 0;
            y = gameView.getHeight() - height;
        }


        currentFrame = ++currentFrame % BMP_COLUMNS;
    }

    public void onDraw(Canvas canvas) {
        update();

        int srcX = currentFrame * width;
        int srcY = 2 * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
        //Log.e("posicion nave: ", "X:"+x+" y: "+y);


    }

    public void controlVelocidadX(){
        this.xSpeed = this.xSpeed+gameView.getVelocidadX();
        if(this.xSpeed > 10){
            this.xSpeed = 10;
        }
        if(this.xSpeed < -10){
            this.xSpeed = -10;
        }
    }
    public void controlVelocidadY(){
        this.ySpeed = this.ySpeed+gameView.getVelocidadY();
        if(this.ySpeed > 10){
            this.ySpeed = 10;
        }
        if(this.ySpeed < -10){
            this.ySpeed = -10;
        }
    }

    public boolean colisionaConNave(int xPos, int Ypos, int objetoWidth, int objetoHegith){ //COMPROBAR TODOS LOS PUNTOS DE COLISION, AHORA MISMO SOLO COLISIONA CON UNO
        if(((x < xPos && x+width > xPos) || (x < xPos+objetoWidth && x+width > xPos+objetoWidth)) && ((y < Ypos && y+height > Ypos))
                || ((x < xPos && x+width > xPos) || (x < xPos+objetoWidth && x+width > xPos+objetoWidth)) && (y < Ypos+objetoHegith && y+height > Ypos+objetoHegith)){
            return true;
        }else{
            return false;
        }
    }

    /* FUNCION ANTIGUA, Retornar en caso de fallos
    public boolean colisionaConNave(int xPos, int Ypos, int objetoWidth, int objetoHegith){ //COMPROBAR TODOS LOS PUNTOS DE COLISION, AHORA MISMO SOLO COLISIONA CON UNO
        if(((xPos < x && xPos+objetoWidth > x) || (xPos < x+width && xPos+objetoWidth > x+width)) && ((Ypos < y && Ypos+objetoHegith > y))
                || ((xPos < x && xPos+objetoWidth > x) || (xPos < x+width && xPos+objetoWidth > x+width)) && (Ypos < y+height && Ypos+objetoHegith > y+height)){
            return true;
        }else{
            return false;
        }
    }
     */
}

