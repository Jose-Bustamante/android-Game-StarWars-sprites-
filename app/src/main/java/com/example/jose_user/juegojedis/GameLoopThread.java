package com.example.jose_user.juegojedis;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jose-User on 21/01/2016.
 */
public class GameLoopThread extends Thread {

    static final long FPS = 10;
    private GameView view;
    private boolean running = false;
    private Random r = new Random();




    public GameLoopThread(GameView view) {
        this.view = view;


    }

    public boolean running(){
        return running;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {

        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        long currentTimeTromper = System.currentTimeMillis();
        long currentTimeShoot = System.currentTimeMillis();

        long createShootTime = 5000 / (view.getLevel()+1);
        long createTropersTime = 10000;

        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();


            try {

                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            }catch (Exception e){

            }
            finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }

            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}

            if(currentTimeTromper+createTropersTime < System.currentTimeMillis()){
                currentTimeTromper = System.currentTimeMillis();
                view.createTropers();

            }

            if(currentTimeShoot+createShootTime < System.currentTimeMillis()){
                currentTimeShoot = System.currentTimeMillis();
                ArrayList<Tromper> arrayTrompers = new ArrayList<>();
                arrayTrompers = view.getListaTromper();

                if(arrayTrompers.size()>0){
                    int aleatorio = arrayTrompers.size();
                    Tromper t = arrayTrompers.get(r.nextInt(aleatorio));
                    view.createShoot(t.getX(),t.getY());
                    //Log.e("Create shoot", "Se" + r.nextInt(aleatorio));
                }


            }

        }


    }

}
