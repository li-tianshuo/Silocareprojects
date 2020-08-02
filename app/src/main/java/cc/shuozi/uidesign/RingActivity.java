package cc.shuozi.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

public class RingActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIMEOUT = 2000;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        Animation fadeut = new AlphaAnimation(1,0);
        fadeut.setInterpolator(new AccelerateInterpolator());
        fadeut.setStartOffset(200);
        fadeut.setDuration(1800);
        TextView text = findViewById(R.id.textView25);

        text.setAnimation(fadeut);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RingActivity.this,decision_making.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_SCREEN_TIMEOUT);
    }
}