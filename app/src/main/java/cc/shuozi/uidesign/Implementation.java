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
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;

public class Implementation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private String[][] data={{"Px"," "},{"Diet"," "},{"Physical Activity"," "}, {"Mental Activity"," "}};
    private void initdrawer() {
        drawerLayout=findViewById(R.id.drawerlayout_implementation);
        toolbar=findViewById(R.id.toolbar_implementation);



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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implementation);
        NavigationView navigationView=findViewById(R.id.main_side);
        initdrawer();
        navigationView.setNavigationItemSelectedListener(this);
        ListView list=findViewById(R.id.list_view_implementation);

        my_adapter_imp sc = new my_adapter_imp(this, data);
        list.setAdapter(sc);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.side_mainmenu:
                Intent main1=new Intent(Implementation.this, ongoing_main_menu.class);
                startActivity(main1);
                return true;
            case R.id.side_setting:
                Intent main2=new Intent(Implementation.this, MainMenu.class);
                startActivity(main2);
                return true;
            case R.id.side_implementation:
                Intent main3=new Intent(Implementation.this, Implementation.class);
                startActivity(main3);
                return true;
            case R.id.side_decision:
                Intent main4=new Intent(Implementation.this, decision_making.class);
                startActivity(main4);
                return true;
        }
        return false;
    }
}