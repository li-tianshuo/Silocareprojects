package cc.shuozi.uidesign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {

    private ImageView Logo;
    private EditText U_name;
    private EditText P_name;
    private CheckBox CHECK;
    private Button Login;
    private FirebaseAuth mAuth;
    private View google_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mAuth = FirebaseAuth.getInstance();
        Logo = findViewById(R.id.Logo);
        U_name = findViewById(R.id.u_name);
        P_name = findViewById(R.id.pass_wd);
        CHECK = findViewById(R.id.checkBox);
        Login = findViewById(R.id.Login_Button);
        google_sign_in=findViewById(R.id.sign_in_button);

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



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=U_name.getText().toString();
                String password=P_name.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login. this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Status", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Status", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                            }
                        });
            }
        });




    }

    private void updateUI(FirebaseUser user) {
        if (user!=null)
        {
            Intent intent=new Intent(Login.this, MainMenu.class);
            intent.putExtra("uid",user.getUid());
            startActivity(intent);
        }
    }
}