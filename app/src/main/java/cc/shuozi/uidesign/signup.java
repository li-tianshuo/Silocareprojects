package cc.shuozi.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class signup extends AppCompatActivity {
    private TextView text1 ;
    private TextView text2 ;
    private EditText editbox_email;
    private EditText editbox_passwd;
    private EditText editbox_fname;
    private EditText editbox_lname;
    private Button signup;
    private int shortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        text1=(TextView)findViewById(R.id.textView3);
        text2=(TextView)findViewById(R.id.textView6);
        editbox_email=(EditText)findViewById(R.id.editText);
        editbox_passwd=(EditText)findViewById(R.id.editText2);
        editbox_fname=(EditText)findViewById(R.id.editText3);
        editbox_lname=(EditText)findViewById(R.id.editText4);
        signup=(Button)findViewById(R.id.button);

        text1.setAlpha(0.0f);
        text2.setAlpha(0.0f);
        editbox_email.setAlpha(0.0f);
        editbox_passwd.setAlpha(0.0f);
        editbox_fname.setAlpha(0.0f);
        editbox_lname.setAlpha(0.0f);
        signup.setAlpha(0.0f);


        text1.animate().alpha(1).setDuration(6000);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                text2.animate().alpha(1).setDuration(3000);
            }
        },2000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_email.animate().alpha(1).setDuration(3000);
            }
        },4000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_passwd.animate().alpha(1).setDuration(3000);
            }
        },5000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_fname.animate().alpha(1).setDuration(3000);
            }
        },6000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_lname.animate().alpha(1).setDuration(3000);
            }
        },7000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                signup.animate().alpha(1).setDuration(3000);
            }
        },8000);

    }

}
