package cc.shuozi.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainMenu extends AppCompatActivity {
    private String messagecon;
    private ViewFlipper message;
    private String Filename="message.json";

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
            Log.i("success",str+c);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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