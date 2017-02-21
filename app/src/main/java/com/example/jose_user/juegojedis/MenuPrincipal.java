package com.example.jose_user.juegojedis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Jose-User on 29/01/2016.
 */
public class MenuPrincipal { //CLASE EN DESUSO LA VOY A SUSTITUIR POR UN ACTIVITY, CON EL CUAL PODRE LLAMAR TAMBIEN AL SHAREDPREFERENCES;
    boolean buttonClicked = false;
    private Bitmap source, sourcePushed;
    public ArrayList<Rect> arrayBotones;
    private Rect button1, button2, button3;
    private  int x, y;
    private GameView gameview;


    public MenuPrincipal(GameView gameView, Bitmap bmp, Bitmap bmp2, int x, int y) {


        this.source = bmp;
        this.sourcePushed = bmp2;
        this.x = x;
        this.y = y;
        this.gameview = gameView;
        button1 = new Rect(x,y,x+700, y+400);



    }

    public void onDraw(Canvas canvas) {


       if (buttonClicked == true) {
            canvas.drawBitmap(sourcePushed, null, button1, null);
            // SourcePushed will be a that of a clicked button bitmap
        } else {
            canvas.drawBitmap(source, null, button1, null);
            // Source will be a that of a non clicked button bitmap
        }

    }

    public boolean isButtonClicked() {
        return buttonClicked;
    }

    public void setButtonClicked(boolean buttonClicked) {
        this.buttonClicked = buttonClicked;
    }

    public Rect getButton1() {
        return button1;
    }
}

