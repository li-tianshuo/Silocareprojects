package cc.shuozi.uidesign;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public void JSONmaker(String fname, String lname,String email,String passwd) throws JSONException
    {
        String FILENAME = "signup.json";
        String jsonsentence;
        JSONObject information=new JSONObject();
        information.put("First Name",fname);
        information.put("Last Name",lname);
        information.put("Email",email);
        information.put("Password",passwd);
        jsonsentence=information.toString();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(jsonsentence.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);




        text1 = findViewById(R.id.textView3);
        text2 = findViewById(R.id.textView6);
        editbox_email = findViewById(R.id.editText);
        editbox_passwd = findViewById(R.id.editText2);
        editbox_fname = findViewById(R.id.editText3);
        editbox_lname = findViewById(R.id.editText4);
        signup = findViewById(R.id.button);

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
        }, 2000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_email.animate().alpha(1).setDuration(3000);
            }
        }, 4000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_passwd.animate().alpha(1).setDuration(3000);
            }
        }, 5000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_fname.animate().alpha(1).setDuration(3000);
            }
        }, 6000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_lname.animate().alpha(1).setDuration(3000);
            }
        }, 7000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                signup.animate().alpha(1).setDuration(3000);
            }
        }, 8000);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    JSONmaker(editbox_fname.getText().toString(), editbox_lname.getText().toString(), editbox_email.getText().toString(), editbox_passwd.getText().toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }


}







