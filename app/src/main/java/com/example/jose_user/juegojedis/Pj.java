package com.example.jose_user.juegojedis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Jose-User on 03/02/2016.
 */
public class Pj {
    private int x = 0;
    private int y = 0;
    private int xSpeed = 5;
    private int ySpeed = 5;
    private GameView gameView;
    private Bitmap bmp;
    private static final int BMP_ROWS = 6;
    private static final int BMP_COLUMNS = 4;
    private int currentFrame = 0, currentFrameAttack = 0;
    private int width;
    private int height;
    private boolean attack;
    private MediaPlayer mp;
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 0, 2, 1 };
    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right

    public Pj(GameView gameView, Bitmap bmp, int x, int y) {
        this.gameView=gameView;
        this.bmp=bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.x = x;
        this.y = y;
        this.attack = false;
        Random rnd = new Random();
        xSpeed = rnd.nextInt(10)-2;
        ySpeed = rnd.nextInt(10)-2;


    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() { return width; }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAttack(boolean b){attack = b;}

    public boolean isAttack(){return attack;}

    public void controlVelocidadX(){
        this.xSpeed = this.xSpeed+gameView.getVelocidadX();
        if(this.xSpeed > 10){
            this.xSpeed = 10;
        }
        if(this.xSpeed < -10){
            this.xSpeed = -10;
        }
        //Log.e("Valor Xspee", ""+xSpeed);
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

    private void update() {
        controlVelocidadX();
        x = x + (xSpeed);
        if ( x + (xSpeed) < 0) {
            xSpeed = 0;
            x=0;
        }if(x > gameView.getWidth() - width - (xSpeed) ){
            xSpeed = 0;
            x = gameView.getWidth() -width;
        }

        controlVelocidadY();
        //ySpeed--;
        y = y + (ySpeed);
        //Log.e("Valor Yspee1", ""+ySpeed);
        //Log.e("posY1", " "+y);
        if ( y + (ySpeed) < 0) {
            ySpeed = 0;
            y=0;
            //Log.e("ENTRA POR UNO", " "+y);
        }if(y > gameView.getHeight() - height - (ySpeed)){
            ySpeed = 0;
            y = gameView.getHeight() - height;
            //Log.e("ENTRA POR OTRO", " "+y);
        }

        //Log.e("Valor Yspee2", ""+ySpeed);
        //Log.e("posY2", " "+y);

        currentFrame = ++currentFrame % BMP_COLUMNS;
    }



    public void onDraw(Canvas canvas) {



        if(attack){
            //gestionar el movimiento de ataque

            int srcX = currentFrameAttack * width;
            int srcY = 0;
            if(getAnimationRow() == 0 || getAnimationRow() == 2){srcY = 4 * height;} //ataque a la izquierda si el muñeco va hacia izquierda o abajo
            if(getAnimationRow() == 1 || getAnimationRow() == 3){srcY = 5 * height;}
            Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
            Rect dst = new Rect(x, y, x + width, y + height);
            currentFrameAttack = ++currentFrame % BMP_COLUMNS;

            canvas.drawBitmap(bmp, src, dst, null);

        }else{
            update();
            int srcX = currentFrame * width;
            int srcY = getAnimationRow() * height;
            Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
            Rect dst = new Rect(x, y, x + width, y + height);
            canvas.drawBitmap(bmp, src, dst, null);
        }



    }


    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        if(direction == 4){direction = 3;} //esto lo hago porque cuando xPeed es 10 y ySpeed es -10, direction es 4. y esta fuera del array.
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }




    public boolean colisionaConPj(int xPos, int Ypos, int objetoWidth, int objetoHegith){ //COMPROBAR TODOS LOS PUNTOS DE COLISION, AHORA MISMO SOLO COLISIONA CON UNO
        if(((x < xPos && x+width > xPos) || (x < xPos+objetoWidth && x+width > xPos+objetoWidth)) && ((y < Ypos && y+height > Ypos))
                || ((x < xPos && x+width > xPos) || (x < xPos+objetoWidth && x+width > xPos+objetoWidth)) && (y < Ypos+objetoHegith && y+height > Ypos+objetoHegith)){
            return true;
        }else{
            return false;
        }
    }
}
