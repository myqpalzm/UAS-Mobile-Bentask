package id.ac.umn.uas_mobile_bentask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //menghilangkan ActionBar
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();
        getSupportActionBar().hide();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
                finish();
            }
        }, 3000L); //3000 L = 3 detik
    }
}