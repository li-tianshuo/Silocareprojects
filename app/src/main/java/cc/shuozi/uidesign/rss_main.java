package cc.shuozi.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class rss_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_main);
        ListView rss_list= findViewById(R.id.rss_main_list);
        String[] health_rss={"Health Feed1","Health Feed2","Health Feed3"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,health_rss);
        rss_list.setAdapter(adapter);
        ArrayList<String> rssLinks = new ArrayList<>();
        rssLinks.add("https://medicalxpress.com/rss-feed/");
        rssLinks.add("https://www.healthcareitnews.com/home/feed");
        rssLinks.add("https://www.medicinenet.com/rss/dailyhealth.xml");
        rss_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(rss_main.this, rss_feed.class).putExtra("rssLink", rssLinks.get(0)));
                        break;
                    case 1:
                        startActivity(new Intent(rss_main.this, rss_feed.class).putExtra("rssLink", rssLinks.get(1)));
                        break;
                    case 2:
                        startActivity(new Intent(rss_main.this, rss_feed.class).putExtra("rssLink", rssLinks.get(2)));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}