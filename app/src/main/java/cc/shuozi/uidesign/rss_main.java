package cc.shuozi.uidesign;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class rss_main extends AppCompatActivity {
    private String Filename = "rss_link.json";
    private String linkcon;
    private String[] name;
    private String[] link_url;

    public void Read_Json() {
        FileInputStream inputStream;

        try {
            inputStream = openFileInput(Filename);
            byte temp[] = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            linkcon = sb.toString();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void JsonToMap() throws JSONException {
        linkcon = linkcon.replaceAll("\\\\", "");
        linkcon = linkcon.replaceAll("^\"|\"$", "");
        linkcon = linkcon.replaceAll("(\"\\{\")", "\\{\"").replaceAll("(\"\\}\")", "\"\\}");
        linkcon = linkcon.replaceAll("=", ":");
        Log.e("1", linkcon);
        //linkcon.replaceAll("}\"", "}");
        //linkcon.replaceAll("\"\\{", "{");

        JSONObject jsonObject = new JSONObject(linkcon);
        JSONArray jsonArray = jsonObject.getJSONArray("link");
        name = new String[jsonArray.length()];
        link_url = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            Log.e("1", jsonObject.getString("link"));
            //Log.e("1", jsonArray.toString());
            JSONObject object = jsonArray.getJSONObject(i);
            name[i] = object.getString("name");
            link_url[i] = object.getString("link_url");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_main);
        ListView rss_list = findViewById(R.id.rss_main_list);
        Read_Json();
        if (linkcon != null) {
            try {
                JsonToMap();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
            rss_list.setAdapter(adapter);

            rss_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(rss_main.this, rss_feed.class).putExtra("rssLink", link_url[i]));
                }
            });

            rss_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    List<String> list1 = new ArrayList<String>(Arrays.asList(name));
                    list1.remove(i);
                    name = list1.toArray(new String[0]);
                    List<String> list2 = new ArrayList<String>(Arrays.asList(link_url));
                    list2.remove(i);
                    link_url = list2.toArray(new String[0]);
                    JSONObject object1=new JSONObject();
                    JSONArray array_1=new JSONArray();
                    //Log.e("!", String.valueOf(length));
                    for(int k=0;k<name.length;k++){
                        Map<String, String> json_map_1=new HashMap<>();
                        json_map_1.put("\"name\"","\""+name[k]+"\"");
                        //Log.e("!!",name[i]);
                        json_map_1.put("\"link_url\"","\""+link_url[k]+"\"");
                        array_1.put(json_map_1);
                    }
                    try {
                        object1.put("link",array_1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    File file=new File("rss_link.json");
                    FileOutputStream fileOutputStream= null;
                    try {
                        fileOutputStream = openFileOutput("rss_link.json", Context.MODE_PRIVATE);
                        byte[] bytes=object1.toString().getBytes();
                        fileOutputStream.write(bytes);
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(new Intent(rss_main.this, rss_main.class)));
                    finish();
                    return false;
                }
            });

            //String[] health_rss={"Health Feed1","Health Feed2","Health Feed3"};

            //ArrayList<String> rssLinks = new ArrayList<>();
            FloatingActionButton add_link = findViewById(R.id.add_rss_link_button);

            add_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(rss_main.this, Add_rss_link.class));
                    finish();
                }
            });
            //rssLinks.add("https://medicalxpress.com/rss-feed/");
            //rssLinks.add("https://www.healthcareitnews.com/home/feed");
            //rssLinks.add("https://www.medicinenet.com/rss/dailyhealth.xml");

        }
    }
}