package color.test.roha.com.colortestproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class ProfileDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.90);
        int screenHeight = (int) (metrics.heightPixels * 0.80);

        setContentView(R.layout.activity_profile_dialog);

        getWindow().setLayout(screenWidth, screenHeight); //set below the setContentvie

        setFinishOnTouchOutside(true);

    }
}
