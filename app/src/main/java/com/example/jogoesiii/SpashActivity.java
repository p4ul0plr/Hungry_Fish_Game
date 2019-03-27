package com.example.jogoesiii;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class SpashActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mediaPlayer = MediaPlayer.create(this, R.raw.som_inicio);
        mediaPlayer.start();
        //Fazendo a SplashScreen ficar ativada por 5 segundos
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2500);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    Intent mainIntent = new Intent(SpashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        thread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();

    }
}
