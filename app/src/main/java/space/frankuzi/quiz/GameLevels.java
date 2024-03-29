package space.frankuzi.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class GameLevels extends AppCompatActivity {

    private AdView _adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelevels);

        MobileAds.initialize(this, "ca-app-pub-2851993153420910~9092853430");
        _adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        _adView.loadAd(adRequest);

        createBackButton();

        SharedPreferences progress = getSharedPreferences("Level", MODE_PRIVATE);
        int accessibleLevel = progress.getInt("Level", 1);

        /*SharedPreferences.Editor editor = progress.edit();
        editor.putInt("Level", 1);
        editor.commit();*/

        createFirstLevelButton(accessibleLevel);
        createSecondLevelButton(accessibleLevel);

        Window window = getWindow();
        WindowsSettings.setWindowSettings(window);
    }

    @Override
    public void onBackPressed(){
        backButtonAction();
    }

    private void backButtonAction() {
        Intent intent = new Intent(GameLevels.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createBackButton() {
        Button buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(v -> backButtonAction());
    }

    private void createFirstLevelButton(int accessibleLevel){
        if (accessibleLevel >= 1){
            TextView level1Button = findViewById(R.id.textView1);
            level1Button.setText("1");
            level1Button.setOnClickListener(v -> {
                Intent intent = new Intent(GameLevels.this, Level1.class);
                startActivity(intent);
                finish();
            });
    }}

    private void createSecondLevelButton(int accessibleLevel){
        if (accessibleLevel >= 2){
            TextView level2Button = findViewById(R.id.textView2);
            level2Button.setText("2");
            level2Button.setOnClickListener(v -> {
                Intent intent = new Intent(GameLevels.this, Level2.class);
                startActivity(intent);
                finish();
            });
    }}
}