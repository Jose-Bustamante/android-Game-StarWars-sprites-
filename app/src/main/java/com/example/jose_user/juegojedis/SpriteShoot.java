package com.example.jose_user.juegojedis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.List;

/**
 * Created by Jose-User on 05/02/2016.
 */
public class SpriteShoot {
    private int x;
    private int y;
    private int objetiveX, objetiveY;
    private Bitmap bmp;
    private int life = 50;
    private List<SpriteShoot> temps;
    private Pj pj;
    private  GameView gameView;
    private int shootSpeedX = 30, shootSpeedY = 10, width, height;
    private boolean xTrue, yTrue;


    public SpriteShoot(List<SpriteShoot> temps, GameView gameView, Pj pj, int x, int y, Bitmap bmp) {

        this.x = x;
        this.y = y;
        this.bmp = bmp;
        this.temps = temps;
        this.pj = pj;
        this.gameView = gameView;
        if(pj.getX() > x){objetiveX = pj.getX()+100;}else{objetiveX = pj.getX()-100;}
        if(pj.getY() > y){objetiveY = pj.getY()+100;}else{objetiveY = pj.getY()-100;}


        width = bmp.getWidth();
        height = bmp.getHeight();
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bmp, x, y, null);
    }

    private void update() {

        //control movimiento sprite
        if(objetiveX > x){
            x= x + (shootSpeedX+(objetiveX - x)/10);
            if(x+(shootSpeedX+20)>objetiveX){xTrue = true;} //controlamos que llega a destino para terminar el sprite
        }else{
            x = x - (shootSpeedX+(x - objetiveX)/10);
            if(x-(shootSpeedX+20)>objetiveX){xTrue = true;}
        }
        if(objetiveY > y){
            y = y + (shootSpeedY+(objetiveY - y)/10);
            if(y+(shootSpeedY+20)>objetiveY){yTrue = true;}
        }else{
            y = y - (shootSpeedY+(y - objetiveY)/10);
            if(x-(shootSpeedY+20)>objetiveY){yTrue = true;}
        }

        //control sprite que se dibuja
        if(xTrue && yTrue){
            life = 0;
        }

        if(colisionaConPj(pj.getX(), pj.getY(), pj.getWidth(), pj.getHeight())){
           // Log.e("Colisiona", "si");
            life = 0;
            if(!pj.isAttack()){ //si el jugador esta bloqueando no quita vidas.
                gameView.changeLifes(1);
                gameView.createExplosion(gameView.getArraySpriteExplosion(), gameView, pj.getX(),pj.getY());
            }

        }

        //control duracion sprite
        if (--life < 1) {
            temps.remove(this);
        }


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
