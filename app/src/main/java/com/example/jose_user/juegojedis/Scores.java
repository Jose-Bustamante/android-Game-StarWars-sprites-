package com.example.jose_user.juegojedis;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Scores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        BdScores bdScores = new BdScores(this);
        int score;
        score = (int) getIntent().getExtras().get("score");

        bdScores.insertarScore(score);
        int[] scores = bdScores.cojerScores();
        Log.e("Scores ", "" + scores.length);

        TextView tvScore = (TextView) findViewById(R.id.tvShowScore);
        tvScore.setText(""+score);
        TextView tvScore1 = (TextView) findViewById(R.id.tvScore1Res);
        tvScore1.setText(""+scores[0]);
        TextView tvScore2 = (TextView) findViewById(R.id.tvScore2Res);
        tvScore2.setText(""+scores[1]);
        TextView tvScore3 = (TextView) findViewById(R.id.tvScore3Res);
        tvScore3.setText(""+scores[2]);
        TextView tvScore4 = (TextView) findViewById(R.id.tvScore4Res);
        tvScore4.setText(""+scores[3]);
        TextView tvScore5 = (TextView) findViewById(R.id.tvScore5Res);
        tvScore5.setText(""+scores[4]);

    }

    public void btnContinuar(View v){
        //llamar al menu principal.
        finish();
    }
}
