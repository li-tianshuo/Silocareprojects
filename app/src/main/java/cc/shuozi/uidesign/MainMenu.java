package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainMenu extends AppCompatActivity {
    private String messagecon;
    private ViewFlipper message;
    private String Filename="message.json";
    private TextView username;
    private String value;
    private String name=null;


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
    private void getName()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("status",document.getString("First Name"));
                                Log.i("status",document.getString("Last Name"));
                            }
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

    }
}