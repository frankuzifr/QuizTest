package space.frankuzi.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long _backPressedTime;
    private Toast _backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createStartButton();

        Window window = getWindow();
        WindowsSettings.setWindowSettings(window);
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

    private void createStartButton(){
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
    }
}