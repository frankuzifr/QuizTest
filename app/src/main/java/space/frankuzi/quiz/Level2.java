package space.frankuzi.quiz;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class Level2 extends AppCompatActivity {

    private Dialog _startDialog;
    private Dialog _endDialog;

    private int _imageLeftNumber;
    private int _imageRightNumber;
    private int _countRightAnswers;
    private InterstitialAd _interstitialAd;

    private final int[] _progress = {
            R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5,
            R.id.point6, R.id.point7, R.id.point8, R.id.point9, R.id.point10,
            R.id.point11, R.id.point12, R.id.point13, R.id.point14, R.id.point15,
            R.id.point16, R.id.point17, R.id.point18, R.id.point19, R.id.point20
    };

    private final LevelsImages _levelsImages = new LevelsImages();
    private final Random _random = new Random();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal);

        MobileAds.initialize(this, "ca-app-pub-2851993153420910~9092853430");
        _interstitialAd = new InterstitialAd(this);
        _interstitialAd.setAdUnitId("ca-app-pub-2851993153420910/9116070011");

        AdRequest adRequest = new AdRequest.Builder().build();
        _interstitialAd.loadAd(adRequest);

        _interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                backToMenu();
            }
        });

        TextView textLevels = findViewById(R.id.text_levels);
        textLevels.setText(R.string.levels2);

        final ImageView imageLeft = findViewById(R.id.image_left);
        imageLeft.setClipToOutline(true);

        final ImageView imageRight = findViewById(R.id.image_right);
        imageRight.setClipToOutline(true);

        setWindowSettings();

        createStartDialog();

        createEndDialog();

        createBackButton();

        Animation animation = AnimationUtils.loadAnimation(Level2.this, R.anim.alpha);

        generateNewStep(imageLeft, imageRight, animation);

        imageLeft.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                imageRight.setEnabled(false);

                if (_imageLeftNumber > _imageRightNumber) {
                    imageLeft.setImageResource(R.drawable.image_true);
                }else {
                    imageLeft.setImageResource(R.drawable.image_false);
                }
            }else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (_imageLeftNumber > _imageRightNumber){
                    rightAnswer();
                }else{
                    wrongAnswer();
                }

                if (_countRightAnswers == 20){
                    saveProgress();
                    _endDialog.show();
                }else {
                    generateNewStep(imageLeft, imageRight, animation);

                    imageRight.setEnabled(true);
                }
            }
            return true;
        });

        imageRight.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                imageLeft.setEnabled(false);

                if (_imageRightNumber > _imageLeftNumber) {
                    imageRight.setImageResource(R.drawable.image_true);
                }else {
                    imageRight.setImageResource(R.drawable.image_false);
                }
            }else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (_imageRightNumber > _imageLeftNumber){
                    rightAnswer();
                }else{
                    wrongAnswer();
                }

                if (_countRightAnswers == 20){
                    saveProgress();
                    _endDialog.show();
                }else {
                    generateNewStep(imageLeft, imageRight, animation);

                    imageLeft.setEnabled(true);
                }
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (_interstitialAd.isLoaded())
            _interstitialAd.show();
        else
            backToMenu();
    }

    private void backToMenu()
    {
        Intent intent = new Intent(Level2.this, GameLevels.class);
        startActivity(intent);
        finish();
    }

    private void setWindowSettings() {
        Window window = getWindow();
        WindowsSettings.setWindowSettings(window);
    }

    private void createStartDialog() {
        _startDialog = new Dialog(this);
        _startDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _startDialog.setContentView(R.layout.previewdialog);
        Window dialogWindow = _startDialog.getWindow();
        assert dialogWindow != null;
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowsSettings.setWindowSettings(dialogWindow);
        _startDialog.setCancelable(false);

        ImageView previewImage = _startDialog.findViewById(R.id.previewImage);
        previewImage.setImageResource(R.drawable.previewimage2);

        TextView textDescription = _startDialog.findViewById(R.id.textDescription);
        textDescription.setText(R.string.level2);

        TextView buttonClose = _startDialog.findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(v -> {
            backToMenu();

            _startDialog.dismiss();
        });

        Button buttonContinue = _startDialog.findViewById(R.id.buttonContinue);
        buttonContinue.setOnClickListener(v -> _startDialog.dismiss());

        _startDialog.show();
    }

    private void createEndDialog(){
        _endDialog = new Dialog(this);
        _endDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _endDialog.setContentView(R.layout.dialog_end);
        Window endDialogWindow = _endDialog.getWindow();
        assert endDialogWindow != null;
        endDialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        endDialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        WindowsSettings.setWindowSettings(endDialogWindow);
        _endDialog.setCancelable(false);

        TextView textDescription = _endDialog.findViewById(R.id.textDescriptionEnd);
        textDescription.setText(R.string.level2End);

        TextView endDialogButtonClose = _endDialog.findViewById(R.id.buttonClose);
        endDialogButtonClose.setOnClickListener(v -> {
            backToMenu();
            _endDialog.dismiss();
        });

        Button endDialogButtonContinue = _endDialog.findViewById(R.id.buttonContinue);
        endDialogButtonContinue.setOnClickListener(v -> {
            textDescription.setText("На этом пока все");
            endDialogButtonContinue.setVisibility(View.INVISIBLE);
        });
    }

    private void createBackButton(){
        Button buttonBack = findViewById(R.id.university_button_back);
        buttonBack.setOnClickListener(v -> {
            if (_interstitialAd.isLoaded())
                _interstitialAd.show();
            else
                backToMenu();
        });
    }

    private void generateNewStep(ImageView imageLeft, ImageView imageRight, Animation animation) {
        _imageLeftNumber = _random.nextInt(10);
        imageLeft.setImageResource(_levelsImages.level2Images[_imageLeftNumber]);
        imageLeft.startAnimation(animation);
        TextView textLeft = findViewById(R.id.text_left);
        textLeft.setText(_levelsImages.level2ImagesTexts[_imageLeftNumber]);

        do {
            _imageRightNumber = _random.nextInt(10);
        }
        while (_imageRightNumber == _imageLeftNumber);

        imageRight.setImageResource(_levelsImages.level2Images[_imageRightNumber]);
        imageRight.startAnimation(animation);
        TextView textRight = findViewById(R.id.text_right);
        textRight.setText(_levelsImages.level2ImagesTexts[_imageRightNumber]);
    }

    private void rightAnswer(){
        if (_countRightAnswers < 20){
            TextView textView = findViewById(_progress[_countRightAnswers]);
            textView.setBackgroundResource(R.drawable.style_points_green);
            _countRightAnswers++;
        }
    }

    private void wrongAnswer(){
        if (_countRightAnswers <= 0)
            return;

        if (_countRightAnswers == 1){
            TextView textView = findViewById(_progress[_countRightAnswers - 1]);
            textView.setBackgroundResource(R.drawable.style_points);
            _countRightAnswers = 0;
        } else {
            for (int i = 0; i < 2; i++) {
                TextView textView = findViewById(_progress[_countRightAnswers - 1]);
                textView.setBackgroundResource(R.drawable.style_points);
                _countRightAnswers--;
            }
        }
    }

    private void saveProgress(){
        SharedPreferences progress = getSharedPreferences("Level", MODE_PRIVATE);
        final int level = progress.getInt("Level", 1);
        if (level <= 2){
            SharedPreferences.Editor editor = progress.edit();
            editor.putInt("Level", 3);
            editor.commit();
        }
    }
}