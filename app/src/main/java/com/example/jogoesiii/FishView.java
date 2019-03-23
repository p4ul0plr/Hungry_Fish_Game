package com.example.jogoesiii;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class FishView extends View {

    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;
    private boolean touch = false;
    private int canvasWidth;
    private int canvasHeigth;
    private int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();
    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();
    private int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();
    private Bitmap backgroundImage;
    private int backgroundImageX;
    private int backgroundImageSpeed = 10;
    private Paint scorePaint = new Paint();
    private int score;
    private int lifeCounterOfFish;
    private Bitmap life[] = new Bitmap[2];
    private MediaPlayer yellowBollSong, redBallSong, greenBallSong, fishJumpingSong;

    public FishView(Context context) {
        super(context);

        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.peixe_boca_aberta);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.peixe_boca_fechada);
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.fundo);
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);
        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
        fishY = 550;
        score = 0;
        lifeCounterOfFish = 3;
        backgroundImageX = 0;
        greenBallSong = MediaPlayer.create(getContext(), R.raw.som_bola_amarela_verde);
        yellowBollSong = MediaPlayer.create(getContext(), R.raw.som_bola_amarela_verde);
        redBallSong = MediaPlayer.create(getContext(), R.raw.son_bolo_vermelha);
        fishJumpingSong = MediaPlayer.create(getContext(), R.raw.som_peixe_pulando);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeigth = canvas.getHeight();

        //Para o meu celular -1425
        //Emulador 1455
        if (backgroundImageX <= -1455) {
            backgroundImageX = 0;
            canvas.drawBitmap(backgroundImage, backgroundImageX, 0, null);
        } else {
            canvas.drawBitmap(backgroundImage, backgroundImageX, 0, null);
            backgroundImageX -= backgroundImageSpeed;
        }
        //canvas.drawBitmap(backgroundImage, 0, 0, null);

        //Movimentação do peixe
        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeigth - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;
        if (fishY < minFishY) {
            fishY = minFishY;
        } else if (fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;

        //Animação do peixe
        if (touch) {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        } else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        //Desenha bola amarela
        yellowX = yellowX - yellowSpeed;
        if (hitBallChecker(yellowX, yellowY)) {
            score = score + 10;
            yellowX = - 100;
            yellowBollSong.start();
        }
        if (yellowX < 0) {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;

        }
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);

        //Desenha bola verde
        greenX = greenX - greenSpeed;
        if (hitBallChecker(greenX, greenY)) {
            score = score + 20;
            greenX = - 100;
            greenBallSong.start();
        }
        if (greenX < 0) {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;

        }
        canvas.drawCircle(greenX, greenY, 15, greenPaint);

        //Desenha bola vermelha
        redX = redX - redSpeed;
        if (hitBallChecker(redX, redY)) {

            if (score >= 100) {
                score = score - 100;
            } else {
                score = 0;
            }
            redX = - 100;
            lifeCounterOfFish--;
            redBallSong.start();
            if (lifeCounterOfFish == 0) {
                //Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("Score", score);
                getContext().startActivity(gameOverIntent);
            }
        }
        if (redX < 0) {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;

        }
        canvas.drawCircle(redX, redY, 40, redPaint);


        //Desenha o score
        canvas.drawText("Score : " + score, 20, 60, scorePaint);

        //Desenha os coreções de vida
        for (int i = 0; i < 3 ; i++) {
            int x = (int) (720 + life[0].getWidth() * 1.5 * i);
            int y = 10;
            if (i < lifeCounterOfFish) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }

        //canvas.drawBitmap(life[0], 780, 10, null);
        //canvas.drawBitmap(life[0], 880, 10, null);
       // canvas.drawBitmap(life[0], 980, 10, null);

        if (score >= 1000) {
            yellowSpeed = 26;
            greenSpeed = 30;
            redSpeed = 35;
        } else if (score >= 2000) {
            yellowSpeed = 36;
            greenSpeed = 40;
            redSpeed = 45;
        } else if (score >= 3000) {
            yellowSpeed = 46;
            greenSpeed = 50;
            redSpeed = 55;
        }
    }

    //Verifica colisão do peixe com as bolas
    public boolean hitBallChecker (int x, int y) {
        if (fishX < x &&
                x < (fishX + fish[0].getWidth()) &&
                fishY < y &&
                y < (fishY + fish[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fishSpeed = -22;
            fishJumpingSong.start();
        }
        return true;
    }

}
