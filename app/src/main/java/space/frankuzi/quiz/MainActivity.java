package space.frankuzi.quiz;

import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Time;
import java.util.TimeZone;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private long _backPressedTime;
    private Toast _backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = findViewById(R.id.buttonStart);

        buttonStart.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, GameLevels.class);
                startActivity(intent);
                finish();
            }
            catch (Exception e) {

            }
        });

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        if (_backPressedTime + 2000 > System.currentTimeMillis()){
            _backToast.cancel();
            super.onBackPressed();
            return;
        }

        _backToast = Toast.makeText(getBaseContext(), "Нажниме еще раз, чтобы выйти", Toast.LENGTH_SHORT);
        _backToast.show();
        _backPressedTime = System.currentTimeMillis();
    }
}