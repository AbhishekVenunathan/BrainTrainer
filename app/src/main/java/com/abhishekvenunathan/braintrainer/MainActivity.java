package com.abhishekvenunathan.braintrainer;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Arrays.asList;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();
    final int gameDuration = 30;
    int gameTimeLeft;
    String correctAnswer;
    int scoreCorrectAnswers = 0;
    int scoreTotalAnswers = 0;
    Random random = new Random();

    TextView tvTime;
    TextView tvScore;
    TextView tvGame;
    TextView tvScoreText;
    Button btnRed;
    Button btnPurple;
    Button btnBlue;
    Button btnGreen;
    Button btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.textViewTime);
        tvScore = findViewById(R.id.textViewScore);
        tvGame = findViewById(R.id.textViewGame);
        tvScoreText = findViewById(R.id.textViewScoreText);
        btnRed = findViewById(R.id.buttonRed);
        btnPurple = findViewById(R.id.buttonPurple);
        btnBlue = findViewById(R.id.buttonBlue);
        btnGreen = findViewById(R.id.buttonGreen);
        btnPlayAgain = findViewById(R.id.buttonPlayAgain);
    }

    private void startTimer() {
        new CountDownTimer(gameDuration * 1000 + 100, 1000) {
            @Override
            public void onTick(long l) {
                updateTime((int) l / 1000);
            }

            @Override
            public void onFinish() {
                updateTime(0);
                tvScoreText.setText("Your time is up!");
                btnPlayAgain.setVisibility(View.VISIBLE);
                tvScore.setTypeface(null, Typeface.BOLD);
            }
        }.start();
    }


    public void startGame(View view) {
        ConstraintLayout clGame = findViewById(R.id.constraintLayoutGame);
        final ConstraintLayout clBegin = findViewById(R.id.constraintLayoutBegin);

        clBegin.animate().alpha(0).setDuration(500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) { }

            @Override
            public void onAnimationEnd(Animator animator) {

                clBegin.setVisibility(View.GONE);
                startTimer();
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
        clGame.setVisibility(View.VISIBLE);
        newGame();

    }


    private void newGame() {
        int randomCorrectBtn = random.nextInt(3) + 1;
        int firstNumber = random.nextInt(20);
        int secondNumber = random.nextInt(20);

        correctAnswer = String.valueOf(firstNumber + secondNumber);
        ArrayList<Button> buttons = new ArrayList<>(asList(btnGreen, btnBlue, btnPurple, btnRed));


        for (int n = 0; n < buttons.size(); n++) {
            if (n != randomCorrectBtn) {
                String newNumber = String.valueOf(random.nextInt(40));

                while (newNumber.equals(correctAnswer)) {
                    newNumber = String.valueOf(random.nextInt(40));
                }
                buttons.get(n).setText(String.valueOf(newNumber));
            } else {
                buttons.get(n).setText(correctAnswer);
            }
        }
        tvGame.setText(firstNumber + " + " + secondNumber);
    }

  
    public void startNewGame(View view) {
        tvScoreText.setVisibility(View.INVISIBLE);
        tvScore.setTypeface(null, Typeface.NORMAL);
        tvScore.setText("0/0");
        btnPlayAgain.setVisibility(View.GONE);
        scoreCorrectAnswers = 0;
        scoreTotalAnswers = 0;
        startTimer();
        newGame();
    }

    public void checkAnswer(View view) {
        if (gameTimeLeft == 0 ) {
            return;
        }
        scoreTotalAnswers++;
        Button answerButton = (Button) view;

        if (correctAnswer.equals(answerButton.getText().toString())) {
            scoreCorrectAnswers++;
            tvScoreText.setText(R.string.correct);
        } else {
            tvScoreText.setText(R.string.wrong);
        }
        tvScoreText.setVisibility(View.VISIBLE);
        tvScore.setText(scoreCorrectAnswers + "/" + scoreTotalAnswers);
        newGame();
    }

    private void updateTime(int time) {
        gameTimeLeft = time;
        if (gameTimeLeft<10){
            tvTime.setText("0"+gameTimeLeft+ "s");
        }else {
            tvTime.setText(gameTimeLeft + "s");
        }
    }
}
