package com.example.jose_user.juegojedis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Jose-User on 18/11/2015.
 */
public class BdScores extends SQLiteOpenHelper{

    private static final int VERSION_BASEDATOS = 5;
    private static final String NOMBRE_BASEDATOS = "BD_Scores.db";
    private static final String NOMBRE_TABLA = "Scores";

    private static final String insScores = "CREATE TABLE Scores (idScore INTEGER PRIMARY KEY AUTOINCREMENT, score Integer)";


    public BdScores(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(insScores);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + NOMBRE_TABLA + ";");
        db.execSQL("CREATE TABLE Scores (idScore INTEGER PRIMARY KEY AUTOINCREMENT, score Integer)");
        //insertarScore(20,1);
        db.execSQL("INSERT INTO Scores (idScore,score) VALUES (null, 0);");
        db.execSQL("INSERT INTO Scores (idScore,score) VALUES (null, 0);");
        db.execSQL("INSERT INTO Scores (idScore,score) VALUES (null, 0);");
        db.execSQL("INSERT INTO Scores (idScore,score) VALUES (null, 0);");
        db.execSQL("INSERT INTO Scores (idScore,score) VALUES (null, 0);");

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //Log.e("abierta", "db");
    }

    public void insertarScore(int score){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("score", score);
        Long nreg_afectados = db.insert("Scores", null, valores);
        Log.e("insertado", " "+nreg_afectados);
        db.close();

    }



    public int[] cojerScores(){
        int[] scores = new int[5];
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT score FROM Scores ORDER BY score DESC", null);
        cursor.moveToFirst();
        int cont = cursor.getCount();
        if(cont > 5){cont = 5;}
        //Log.e("Tamaño cursor", ""+cursor.getCount());
        //int tamano = cursor.getCount();
        for(int i = 1; i<cont; i++){
             scores[i-1] = (int) cursor.getLong(0);
            cursor.moveToNext();
        }
        db.close();
        return scores;
    }

}
