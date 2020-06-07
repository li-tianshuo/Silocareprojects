package cc.shuozi.uidesign;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {
    private ImageView Logo;
    private EditText U_name;
    private EditText P_name;
    private CheckBox CHECK;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Logo = findViewById(R.id.Logo);
        U_name = findViewById(R.id.u_name);
        P_name = findViewById(R.id.pass_wd);
        CHECK = findViewById(R.id.checkBox);
        Login = findViewById(R.id.Login_Button);


        Logo.setAlpha(0.0f);
        U_name.setAlpha(0.0f);
        P_name.setAlpha(0.0f);
        CHECK.setAlpha(0.0f);

        Login.setAlpha(0.0f);




        Logo.animate().alpha(1).setDuration(3000);
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                U_name.animate().alpha(1).setDuration(1000);
            }
        }, 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                P_name.animate().alpha(1).setDuration(1000);
            }
        }, 2000);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                CHECK.animate().alpha(1).setDuration(1000);
            }
        }, 2000);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Login.animate().alpha(1).setDuration(1000);
            }
        }, 3000);


    }
}