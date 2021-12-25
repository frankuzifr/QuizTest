package space.frankuzi.quiz;

import android.view.View;
import android.view.Window;

public class WindowsSettings {
    public static void setWindowSettings(Window window){
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
