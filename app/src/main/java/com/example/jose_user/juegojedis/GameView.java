package com.example.jose_user.juegojedis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Jose-User on 21/01/2016.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private Bitmap bmp;
    private Bitmap dibujo,dibujo2, dibujo3, dibujo4, dibujoNave, botonStart, botonStartPushed, tromper, vader, vida, fondo, muerte, shoot;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private int x = 0;
    private int velocidadX = 1, velocidadY = 1;
    private boolean direccion = true;
    private ArrayList<Jedi> listaJedi = new ArrayList<>();
    private ArrayList<Tromper> listaTromper = new ArrayList<>();
    private Jedi jedi;
    private Jedi jedi2, jedi3, jedi4;
    private Nave nave1;
    private Marcador score;
    private int nEnemigos;
    private boolean menuActivo;
    private MenuPrincipal menu;
    private Context mContext;
    private String level;
    private boolean music, vibre;
    private int troperPossitionX, troperPossitionY;
    private Pj jugador;
    public int widthScreen, heightScreen;
    public Random r = new Random();
    private Vidas vidas;
    private ArrayList<MediaPlayer> arraySounds;
    private ArrayList<SpriteKill> arraySpriteKill;
    private ArrayList<SpriteShoot> arraySpriteShoot;
    private ArrayList<SpriteExplosion> arraySpriteExplosion;
    private Boolean flagEnd = true;


    public GameView(Context context, int widthScreen, int heightScreen, ArrayList<MediaPlayer> arraySounds) {
        super(context);
        mContext = context;
        this.arraySounds = arraySounds;
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(this);
        SharedPreferences prefs = getContext().getSharedPreferences("com.example.jose_user.juegojedis_preferences", Context.MODE_PRIVATE);
        level = prefs.getString("level", "");


        music = prefs.getBoolean("sound", false);
        vibre = prefs.getBoolean("vibre", false);
        //Log.e("valor sound", ""+music);
        if(!level.equals("0")&&!level.equals("1")&&!level.equals("2")){level = "0";}
        Log.e("levels valorAntes:", level);
        music = prefs.getBoolean("sound", false);
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        arraySpriteKill = new ArrayList<>();
        arraySpriteShoot = new ArrayList<>();
        arraySpriteExplosion = new ArrayList<>();
        dibujarNPG();




    }

    private void empezarJuego() {
        //botonStart = BitmapFactory.decodeResource(getResources(), R.drawable.play_button);
        //botonStartPushed = BitmapFactory.decodeResource(getResources(), R.drawable.play_button2);
        //menu = new MenuPrincipal(this, botonStart, botonStartPushed, 200, 400);
        //menuActivo = true;
    }

    public void dibujarNPG(){
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_storage_black_24dp);
        dibujo = BitmapFactory.decodeResource(getResources(), R.drawable.bad1);
        dibujo2 = BitmapFactory.decodeResource(getResources(), R.drawable.yoda);
        dibujo3 = BitmapFactory.decodeResource(getResources(), R.drawable.bad2);
        dibujo4 = BitmapFactory.decodeResource(getResources(), R.drawable.bad3);
        dibujoNave = BitmapFactory.decodeResource(getResources(), R.drawable.nave1);
        tromper = BitmapFactory.decodeResource(getResources(), R.drawable.imperial);
        vader = BitmapFactory.decodeResource(getResources(), R.drawable.dark3);
        vida = BitmapFactory.decodeResource(getResources(), R.drawable.vidas2);
        fondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondo);
        muerte = BitmapFactory.decodeResource(getResources(), R.drawable.blood);
        shoot = BitmapFactory.decodeResource(getResources(), R.drawable.shoot);
        /*jedi = new Jedi(this,dibujo,0,0);
        jedi2 = new Jedi(this, dibujo2,0,200);
        jedi3 = new Jedi(this, dibujo3,0,400);
        jedi4 = new Jedi(this, dibujo4,0,600);*/
        listaJedi.add(new Jedi(this, dibujo, 0, 0));
        listaJedi.add(new Jedi(this, dibujo2,0,200));
        listaJedi.add(new Jedi(this, dibujo3,0,400));
        listaJedi.add(new Jedi(this, dibujo4, 0, 600));
        nave1 = new Nave(this, dibujoNave, 150,150);
        score = new Marcador(this, (widthScreen/100)*3, (heightScreen/100)*18);
        vidas = new Vidas(this, vida);
        jugador = new Pj(this, vader, widthScreen/2, heightScreen/2);
        for(int i = 1; i<10; i++){
            troperPossitionX = r.nextInt(widthScreen - widthScreen/2) + 50;
            troperPossitionY = r.nextInt(heightScreen - heightScreen/2) + 50;
            listaTromper.add(new Tromper(this, tromper, troperPossitionX, troperPossitionY));
        }



        nEnemigos = listaTromper.size();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        nEnemigos = listaTromper.size();
        juegoTerminado();

        //canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(fondo, 0, 0, null);
        x = x + velocidadX;
        if(x + bmp.getWidth() > getWidth()){x=1;}
        if(x < 0){x= getWidth()- bmp.getWidth()-1;}


          //DEVERDAD QUE NO CONSIGO ENTENDER PORQUE SI DIBUJO LA NAVE O EL ESCORE DESPUES DEL BUCLE NO SE VE!!
        for(int j = arraySpriteExplosion.size() - 1; j>=0;j--){
            arraySpriteExplosion.get(j).onDraw(canvas);
        }

        for(int j = arraySpriteShoot.size() - 1; j>=0;j--){
            arraySpriteShoot.get(j).onDraw(canvas);
        }

        for(int j = arraySpriteKill.size() - 1; j>=0;j--){
            arraySpriteKill.get(j).onDraw(canvas);
        }
        vidas.onDraw(canvas);
        jugador.onDraw(canvas);
        for(int i = 0; i < listaTromper.size(); i++){
            listaTromper.get(i).onDraw(canvas);
        }


        score.onDraw(canvas);
        //nave1.onDraw(canvas);

       // for(int x=0; x<=listaJedi.size();x++){ //al poner un for vacio consigo que me dibuje todo
            //listaJedi.get(x).onDraw(canvas);
        //}


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //Log.e("asd", "" + gameLoopThread.getState());
        if(gameLoopThread.getState() != Thread.State.TERMINATED){
            gameLoopThread.setRunning(true);
            gameLoopThread.start();
        }else{
            gameLoopThread = new GameLoopThread(this);
            gameLoopThread.setRunning(true);
            gameLoopThread.start();

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //Investigar este metodo que puede arreglar los problemas de hilos.
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
     public boolean onTouchEvent(MotionEvent event) {


        if(event.getAction() == MotionEvent.ACTION_UP){
            pjAtaque(false);
        }else if(event.getAction() == MotionEvent.ACTION_DOWN){
            pjAtaque(true);
            colisionPJ();
            if(music){
                MediaPlayer md = arraySounds.get(1);
                md.start();
            }

        }

        return true;
    }


    public void juegoTerminado() {
        //si juego termina llamar a un iten de Scores con un bundle con el marcador.
        if(nEnemigos == 0 && flagEnd){
            ((Activity) mContext).finish();
            flagEnd = false;
            Intent i = new Intent(mContext, Scores.class);
            i.putExtra("score", score.getScore());
            mContext.startActivity(i);
        }

        if(vidas.getVidas() == 4 & flagEnd){
            ((Activity) mContext).finish();
            flagEnd = false;
            Intent i = new Intent(mContext, Scores.class);
            i.putExtra("score", score.getScore());
            mContext.startActivity(i);}
        //Mejor llamar aqui a otra actividad que muestre el score, y luego te de la posibilidad de volver al menu.

    }

    public void colisionPJ(){
        for (int i = listaJedi.size()-1; i >= 0; i--) {
            Jedi sprite = listaJedi.get(i);
            if (jugador.colisionaConPj(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight())){    //int xPos, int Ypos, int objetoWidth, int objetoHegith   //SEGUIR POR AQUI CON LAS COLISIONES
                listaJedi.remove(sprite);
                score.setScore(score.getScore() + 1);
                nEnemigos = listaTromper.size();
                nEnemigos--;
                //Log.e("enemigos=", nEnemigos+"");

                //Log.e("Devuelve true", "si");
                break;
            }
        }

        for (int i = listaTromper.size()-1; i >= 0; i--) {
            Tromper sprite = listaTromper.get(i);
            if (jugador.colisionaConPj(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight())){    //int xPos, int Ypos, int objetoWidth, int objetoHegith   //SEGUIR POR AQUI CON LAS COLISIONES
                arraySpriteKill.add(new SpriteKill(arraySpriteKill,this,sprite.getX(),sprite.getY(),muerte));
                arraySpriteShoot.add(new SpriteShoot(arraySpriteShoot,this, jugador, 10, 10, shoot));
                listaTromper.remove(sprite);
                score.setScore(score.getScore() + 1);
                nEnemigos = listaTromper.size();
                nEnemigos--;

                //Log.e("enemigos=", nEnemigos+"");

                //Log.e("Devuelve true", "si");
                break;
            }
        }
    }

    public void colisionNave(){
        for (int i = listaJedi.size()-1; i >= 0; i--) {
            Jedi sprite = listaJedi.get(i);
            if (nave1.colisionaConNave(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight())){    //int xPos, int Ypos, int objetoWidth, int objetoHegith   //SEGUIR POR AQUI CON LAS COLISIONES
                listaJedi.remove(sprite);
                score.setScore(score.getScore() + 1);
                nEnemigos = listaTromper.size();
                nEnemigos--;
                //Log.e("enemigos=", nEnemigos+"");

                //Log.e("Devuelve true", "si");
                break;
            }
        }

        for (int i = listaTromper.size()-1; i >= 0; i--) {
            Tromper sprite = listaTromper.get(i);
            if (nave1.colisionaConNave(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight())){    //int xPos, int Ypos, int objetoWidth, int objetoHegith   //SEGUIR POR AQUI CON LAS COLISIONES
                listaTromper.remove(sprite);
                score.setScore(score.getScore() + 1);
                nEnemigos = listaTromper.size();
               // Log.e("enemigos", ""+nEnemigos);
                nEnemigos--;
                //Log.e("enemigos=", nEnemigos+"");

                //Log.e("Devuelve true", "si");
                break;
            }
        }
    }

    public void cambiarVelocidadNave(int velX, int velY){
        this.velocidadX = velX;
        this.velocidadY = velY;}

    public void cambiarVelocidadPJ(int velX, int velY){
        velocidadX = velX;
        velocidadY = velY;
    }

    public ArrayList<Tromper> getListaTromper() {
        return listaTromper;
    }

    public void pjAtaque(boolean b){
        jugador.setAttack(b);
    }

    public void changeLifes(int vidas){this.vidas.setVida(this.vidas.getVidas() + vidas);}

    public int getVelocidadX(){
        return this.velocidadX;
    }

    public int getVelocidadY(){
        return this.velocidadY;
    }

    public boolean isMenuActivo() {
        return menuActivo;
    }

    public void setMenuActivo(boolean menuActivo) {
        this.menuActivo = menuActivo;
    }

    public void createTropers(){
        for(int i = 1; i<(Integer.parseInt(level)+1)*2; i++){
            troperPossitionX = r.nextInt(widthScreen - widthScreen/2) + 50;
            troperPossitionY = r.nextInt(heightScreen - heightScreen/2) + 50;
            listaTromper.add(new Tromper(this, tromper, troperPossitionX, troperPossitionY));
        }
    }

    public void createExplosion(List<SpriteExplosion> arraSpriteExplosions, GameView gameView, int x, int y) {
        arraSpriteExplosions.add(new SpriteExplosion(arraSpriteExplosions, gameView, x,y));
        if(vibre){
            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
        }


    }

    public ArrayList<SpriteExplosion> getArraySpriteExplosion(){return arraySpriteExplosion;}

    public ArrayList<SpriteShoot> getArraySpriteShoot(){
        return arraySpriteShoot;
    }

    public int getLevel(){return Integer.parseInt(level);}

    public void createShoot(int posX, int posY){    //crear aqui los disparos que se inicien desde la posicion que le paso. asi loi puedo llamar desde la calse tropers.
        arraySpriteShoot.add(new SpriteShoot(arraySpriteShoot,this, jugador, posX, posY, shoot));
    }

    public int getHeightScreen() {
        return heightScreen;
    }

    public int getWidthScreen() {
        return widthScreen;
    }
}