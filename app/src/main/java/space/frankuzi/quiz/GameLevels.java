package space.frankuzi.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameLevels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelevels);

        Button buttonBack = findViewById(R.id.button_back);

        buttonBack.setOnClickListener(v -> {
            BackButtonsAction();
        });

        TextView level1Button = findViewById(R.id.textView1);
        level1Button.setOnClickListener(v -> {
            try{
                Intent intent = new Intent(GameLevels.this, Level1.class);
                startActivity(intent);
                finish();
            }catch (Exception e){

            }
        });

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onBackPressed(){

        BackButtonsAction();
    }

    private void BackButtonsAction() {
        try {
            Intent intent = new Intent(GameLevels.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        catch (Exception e){

        }
    }
}