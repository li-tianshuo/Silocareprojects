package cc.shuozi.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Add_rss_link extends AppCompatActivity {
    private String Filename="rss_link.json";
    private String linkcon;
    private String[] name;
    private String[] link_url;
    private int length=0;
    public void Read_Json(){
        FileInputStream inputStream;

        try {
            inputStream = openFileInput(Filename);
            byte temp[] = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = inputStream.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            linkcon=sb.toString();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void JsonToMap() throws JSONException {
        linkcon=linkcon.replaceAll("\\\\", "");
        linkcon=linkcon.replaceAll("^\"|\"$", "");
        linkcon=linkcon.replaceAll("(\"\\{\")","\\{\"").replaceAll("(\"\\}\")","\"\\}");
        linkcon=linkcon.replaceAll("=",":");
        //Log.e("1",linkcon);
        //linkcon.replaceAll("}\"", "}");
        //linkcon.replaceAll("\"\\{", "{");

        JSONObject jsonObject=new JSONObject(linkcon);
        JSONArray jsonArray=jsonObject.getJSONArray("link");
        name=new String[jsonArray.length()];
        link_url=new String[jsonArray.length()];
        for(int i=0;i<jsonArray.length();i++){
            Log.e("1",jsonObject.getString("link"));
            //Log.e("1", jsonArray.toString());
            JSONObject object=jsonArray.getJSONObject(i);
            name[i]=object.getString("name");
            link_url[i]=object.getString("link_url");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rss_link);
        Read_Json();

        if(linkcon!=null){
            try {
                JsonToMap();
                length=name.length;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("!", String.valueOf(name.length));

        }

        Button button = findViewById(R.id.add_rss_link_b);
        EditText name_text=findViewById(R.id.rss_name_add);
        EditText url_text=findViewById(R.id.rss_url_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName=name_text.getText().toString();
                String strURL=url_text.getText().toString();


                length=length+1;
                String[] temp_1=new String[length];
                String[] temp_2=new String[length];
                for(int i=0;i<length-1;i++){
                    temp_1[i]=name[i];
                    temp_2[i]=link_url[i];

                }
                temp_1[length-1]=strName;
                temp_2[length-1]=strURL;
                name=temp_1;
                link_url=temp_2;

                //Map<String, String> json_map_2=new HashMap<>();
                JSONObject object1=new JSONObject();
                JSONArray array_1=new JSONArray();
                //Log.e("!", String.valueOf(length));
                for(int i=0;i<name.length;i++){
                    Map<String, String> json_map_1=new HashMap<>();
                    json_map_1.put("\"name\"","\""+name[i]+"\"");
                    //Log.e("!!",name[i]);
                    json_map_1.put("\"link_url\"","\""+link_url[i]+"\"");
                    array_1.put(json_map_1);
                }
                try {
                    object1.put("link",array_1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                File file=new File("rss_link.json");
                FileOutputStream  fileOutputStream= null;
                try {
                    fileOutputStream = openFileOutput("rss_link.json", Context.MODE_PRIVATE);
                    byte[] bytes=object1.toString().getBytes();
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(Add_rss_link.this, rss_main.class));
                finish();
            }
        });
    }
}