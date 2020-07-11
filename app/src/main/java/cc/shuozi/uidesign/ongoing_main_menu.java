package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

public class ongoing_main_menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private void initdrawer() {
        drawerLayout=findViewById(R.id.drawerlayout_main);
        toolbar=findViewById(R.id.toolbar_main);



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
                Intent main1=new Intent(ongoing_main_menu.this, ongoing_main_menu.class);
                startActivity(main1);
                return true;
            case R.id.side_setting:
                Intent main2=new Intent(ongoing_main_menu.this, MainMenu.class);
                startActivity(main2);
                return true;
            case R.id.side_implementation:
                Intent main3=new Intent(ongoing_main_menu.this, Implementation.class);
                startActivity(main3);
                return true;
            case R.id.side_decision:
                Intent main4=new Intent(ongoing_main_menu.this, decision_making.class);
                startActivity(main4);
                return true;
            case R.id.goal_menu:
                Intent main5=new Intent(ongoing_main_menu.this, symptoms_goal.class);
                startActivity(main5);
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_main_menu);
        NavigationView navigationView=findViewById(R.id.main_side);
        initdrawer();
        navigationView.setNavigationItemSelectedListener(this);
        ImageButton ongoing_px=findViewById(R.id.ongoing_px);
        ImageButton ongoing_pa=findViewById(R.id.ongoing_pa);
        ImageButton ongoing_ma=findViewById(R.id.ongoing_ma);
        ImageButton ongoing_diet=findViewById(R.id.ongoing_diet);


        ongoing_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ongoing_main_menu.this,decision_making.class);
                intent.putExtra("mode",4);
                startActivity(intent);
            }
        });

        ongoing_pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ongoing_main_menu.this,decision_making.class);
                intent.putExtra("mode",3);
                startActivity(intent);
            }
        });

        ongoing_px.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ongoing_main_menu.this,decision_making.class);
                intent.putExtra("mode",1);
                startActivity(intent);
            }
        });

        ongoing_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ongoing_main_menu.this,decision_making.class);
                intent.putExtra("mode",2);
                startActivity(intent);
            }
        });


    }

}