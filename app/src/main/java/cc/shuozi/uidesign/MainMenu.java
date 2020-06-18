package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {
    private String messagecon;
    private ViewFlipper message;
    private String Filename="message.json";
    private TextView username;
    private String value;
    private String name=null;
    private ImageButton information_button;
    private ImageButton decision_making_button;
    private ImageButton implementation_button;
    private ImageButton setting_button;


    public void getmessage()
    {
        FileInputStream inputStream;

        try {
            inputStream = openFileInput(Filename);
            byte temp[] = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = inputStream.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            messagecon=sb.toString();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int messagenumber()
    {

        int number=0;
        int b=0;
        String str=messagecon;
        while((b=str.indexOf("{"))!=-1)
        {
            number++;
            str=str.substring(str.indexOf("{")+1,str.length());
        }
        return number;
    }
    public String gettitle(int num)
    {
        String title = null;
        String str=messagecon;
        int a=0;
        int b=0;
        int c=0;
        while((b =str.indexOf("{"))!=-1)
        {
            a++;
            c=str.indexOf(":");
            if (a==num)
            {
                title=str.substring(b+1,c);
                str=str.substring(str.indexOf("}")+1,str.length());
            }
            else
            {
                str=str.substring(str.indexOf("}")+1,str.length());
            }
        }
        return title;
    }


    public void fileexist()
    {
        FileOutputStream fos = null;
        String test="{Information1:This is a toast message1 }{Information2:This is a toast message 2}";

        File f=new File(Filename);
        if(!f.exists())
        {
            try {
                fos = openFileOutput(Filename, Context.MODE_PRIVATE);
                fos.write(test.getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void getname(final callback oncallback)
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                name=doc.getString("First Name")+" "+doc.getString("Last Name");
                            }
                            oncallback.onCallback(name);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        information_button=findViewById(R.id.information);
        decision_making_button=findViewById(R.id.decision);
        implementation_button=findViewById(R.id.implementaion);
        setting_button=findViewById(R.id.setting);
        username=findViewById(R.id.textView);

        Intent intent = getIntent();

        if (intent != null) {
            value = intent.getStringExtra("uid");
        }


        message=(ViewFlipper) findViewById(R.id.view_flipper);
        fileexist();
        getmessage();
        for(int i=0;i<messagenumber();i++){
            TextView textview=new TextView(this);
            textview.setText(gettitle(i+1));
            message.addView(textview);
        }
        message.setFlipInterval(2000);
        message.startFlipping();

        getname(new callback() {
            @Override
            public void onCallback(String string) {
                username.setText(string);
            }

            @Override
            public void onCallbacknumber(int i) {

            }

            @Override
            public void onCallbackList(ArrayList<String> list) {

            }

            @Override
            public void onCallbackListstring(String[][] data) {

            }
        });

        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Setting.class));
            }
        });

        information_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, information.class));
            }
        });

    }
}