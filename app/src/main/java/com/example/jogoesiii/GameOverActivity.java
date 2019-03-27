package com.example.jogoesiii;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button startPlayAgain;
    private TextView displayScore;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this, R.raw.som_game_over);
        mediaPlayer.start();
        score  = getIntent().getExtras().get("Score").toString();
        startPlayAgain = (Button) findViewById(R.id.play_again_btn);
        displayScore = (TextView) findViewById(R.id.displayScore);
        startPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        displayScore.setText("Score = " + score);

    }
}
