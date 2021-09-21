package cc.shuozi.uidesign;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.lang.reflect.Array;

public class data_education extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView avator_ongoing;
    private String datas1[]={"Body Measurements", "Health Records", "Daily Movement"};
    private void initdrawer() {
        drawerLayout=findViewById(R.id.drawerlayout_data_e);
        toolbar=findViewById(R.id.toolbar_data_e);



        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }


            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };

        drawerToggle.syncState();

        drawerLayout.setDrawerListener(drawerToggle);


        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.side_mainmenu:
                Intent main1=new Intent(data_education.this, ongoing_main_menu.class);
                startActivity(main1);
                finish();
                return true;
            case R.id.side_setting:
                Intent main2=new Intent(data_education.this, MainMenu.class);
                startActivity(main2);
                finish();
                return true;
            case R.id.side_implementation:
                Intent main3=new Intent(data_education.this, Implementation.class);
                startActivity(main3);
                finish();
                return true;
            case R.id.side_decision:
                Intent main4=new Intent(data_education.this, decision_making.class);
                startActivity(main4);
                finish();
                return true;
            case R.id.goal_menu:
                Intent main5=new Intent(data_education.this, symptoms_goal.class);
                startActivity(main5);
                finish();
                return true;
            case R.id.data_e_menu:
                Intent main6=new Intent(data_education.this, health_video.class);
                startActivity(main6);
                finish();
                return true;
            case R.id.information_menu:
                Intent main7=new Intent(data_education.this, information.class);
                startActivity(main7);
                finish();
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_education);
        NavigationView navigationView = findViewById(R.id.main_side);
        initdrawer();
        navigationView.setNavigationItemSelectedListener(this);
        ListView d_e_list= findViewById(R.id.data_e_list);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas1){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                return view;
            }
        };

        d_e_list.setAdapter(adapter1);
        d_e_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                    case 1:
                    case 2:
                    default:
                }
            }
        });
/*
        avator_ongoing= findViewById(R.id.avator_ongoing_main);
        Log.e("!",String.valueOf(avatorExists()));
        if (avatorExists())
        {
            Uri imgUri=Uri.parse("file:///data/data/cc.shuozi.uidesign/avator.jpg");
            avator_ongoing.setImageURI(imgUri);
        }



 */
    }
    public boolean avatorExists()
    {
        try
        {
            File f=new File("file:///data/data/cc.shuozi.uidesign/avator.jpg");
            if(!f.exists())
            {
                return true;
            }

        }
        catch (Exception e)
        {
            return true;
        }

        return false;
    }


}