package space.frankuzi.quiz;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Level2 extends AppCompatActivity {

    private Dialog dialog;

    private int _imageLeftNumber;
    private int _imageRightNumber;
    private int _countRightAnswers;

    private LevelOneImages levelOneImages = new LevelOneImages();
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        TextView textLevels = findViewById(R.id.text_levels);
        textLevels.setText(R.string.levels1);

        final ImageView imageLeft = findViewById(R.id.image_left);
        imageLeft.setClipToOutline(true);

        final ImageView imageRight = findViewById(R.id.image_right);
        imageRight.setClipToOutline(true);

        final TextView textLeft = findViewById(R.id.text_left);
        final TextView textRight = findViewById(R.id.text_right);

        Window window = getWindow();
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        Window dialogWindow = dialog.getWindow();
        assert dialogWindow != null;
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View dialogWindowDecorView = dialogWindow.getDecorView();
        dialogWindowDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.setCancelable(false);

        TextView buttonClose = dialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(v -> {
            try {
                backToMenu();
            }catch (Exception ignored){

            }
            dialog.dismiss();
        });

        Button buttonContinue = dialog.findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(v -> {
            try{
                dialog.dismiss();
            }catch (Exception ignored){

            }
        });

        dialog.show();

        Button buttonBack = findViewById(R.id.university_button_back);
        buttonBack.setOnClickListener(v -> {
            try{
                backToMenu();
            }catch (Exception ignored)
            {

            }
        });

        int[] progress = {
                R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5,
                R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
                R.id.point11, R.id.point12, R.id.point13, R.id.point14, R.id.point15,
                R.id.point16, R.id.point17, R.id.point18, R.id.point19, R.id.point20
        };

        Animation animation = AnimationUtils.loadAnimation(Level2.this, R.anim.alpha);

        _imageLeftNumber = random.nextInt(10);
        imageLeft.setImageResource(levelOneImages.images[_imageLeftNumber]);
        textLeft.setText(levelOneImages.imagesTexts[_imageLeftNumber]);


        do {
            _imageRightNumber = random.nextInt(10);
        }
        while (_imageRightNumber == _imageLeftNumber);

        imageRight.setImageResource(levelOneImages.images[_imageRightNumber]);
        textRight.setText(levelOneImages.imagesTexts[_imageRightNumber]);

        imageLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    imageRight.setEnabled(false);

                    if (_imageLeftNumber > _imageRightNumber) {
                        imageLeft.setImageResource(R.drawable.image_true);
                    }else {
                        imageLeft.setImageResource(R.drawable.image_false);
                    }
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (_imageLeftNumber > _imageRightNumber){
                        if (_countRightAnswers < 20)
                            _countRightAnswers++;

                        for (int i = 0; i < 20; i++){
                            TextView textView = findViewById(progress[i]);
                            textView.setBackgroundResource(R.drawable.style_points);
                        }

                        //todo: попробовать переписать
                        for (int i = 0; i < _countRightAnswers; i++){
                            TextView textView = findViewById(progress[i]);
                            textView.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }else{
                        if (_countRightAnswers > 0) {
                            if (_countRightAnswers ==1){
                                _countRightAnswers = 0;
                            } else {
                                _countRightAnswers -= 2;
                            }

                            for (int i = 0; i < 19; i++){
                                TextView textView = findViewById(progress[i]);
                                textView.setBackgroundResource(R.drawable.style_points);
                            }

                            //todo: попробовать переписать
                            for (int i = 0; i < _countRightAnswers; i++) {
                                TextView textView = findViewById(progress[i]);
                                textView.setBackgroundResource(R.drawable.style_points_green);
                            }
                        }
                    }

                    if (_countRightAnswers == 20){

                    }else {
                        _imageLeftNumber = random.nextInt(10);
                        imageLeft.setImageResource(levelOneImages.images[_imageLeftNumber]);
                        imageLeft.startAnimation(animation);
                        textLeft.setText(levelOneImages.imagesTexts[_imageLeftNumber]);


                        do {
                            _imageRightNumber = random.nextInt(10);
                        }
                        while (_imageRightNumber == _imageLeftNumber);

                        imageRight.setImageResource(levelOneImages.images[_imageRightNumber]);
                        imageRight.startAnimation(animation);
                        textRight.setText(levelOneImages.imagesTexts[_imageRightNumber]);

                        imageRight.setEnabled(true);
                    }
                }
                return true;
            }
        });

        imageRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    imageLeft.setEnabled(false);

                    if (_imageRightNumber > _imageLeftNumber) {
                        imageRight.setImageResource(R.drawable.image_true);
                    }else {
                        imageRight.setImageResource(R.drawable.image_false);
                    }
                }else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (_imageRightNumber > _imageLeftNumber){
                        if (_countRightAnswers < 20)
                            _countRightAnswers++;

                        for (int i = 0; i < 20; i++){
                            TextView textView = findViewById(progress[i]);
                            textView.setBackgroundResource(R.drawable.style_points);
                        }

                        //todo: попробовать переписать
                        for (int i = 0; i < _countRightAnswers; i++){
                            TextView textView = findViewById(progress[i]);
                            textView.setBackgroundResource(R.drawable.style_points_green);
                        }
                    }else{
                        if (_countRightAnswers > 0) {
                            if (_countRightAnswers ==1){
                                _countRightAnswers = 0;
                            } else {
                                _countRightAnswers -= 2;
                            }

                            for (int i = 0; i < 19; i++){
                                TextView textView = findViewById(progress[i]);
                                textView.setBackgroundResource(R.drawable.style_points);
                            }

                            //todo: попробовать переписать
                            for (int i = 0; i < _countRightAnswers; i++) {
                                TextView textView = findViewById(progress[i]);
                                textView.setBackgroundResource(R.drawable.style_points_green);
                            }
                        }
                    }

                    if (_countRightAnswers == 20){

                    }else {
                        _imageLeftNumber = random.nextInt(10);
                        imageLeft.setImageResource(levelOneImages.images[_imageLeftNumber]);
                        imageLeft.startAnimation(animation);
                        textLeft.setText(levelOneImages.imagesTexts[_imageLeftNumber]);


                        do {
                            _imageRightNumber = random.nextInt(10);
                        }
                        while (_imageRightNumber == _imageLeftNumber);

                        imageRight.setImageResource(levelOneImages.images[_imageRightNumber]);
                        imageRight.startAnimation(animation);
                        textRight.setText(levelOneImages.imagesTexts[_imageRightNumber]);

                        imageLeft.setEnabled(true);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        backToMenu();
    }

    private void backToMenu()
    {
        Intent intent = new Intent(Level2.this, GameLevels.class);
        startActivity(intent);
        finish();
    }
}