package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class symptoms_goal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager2 viewPager;
    private ActionBarDrawerToggle drawerToggle;
    private BottomNavigationView bottomNavigationView;
    private void initdrawer() {
        drawerLayout=findViewById(R.id.drawer_goals);
        toolbar=findViewById(R.id.toolbar_symptoms_goals);

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
        setContentView(R.layout.activity_symptoms_goal);
        NavigationView navigationView=findViewById(R.id.main_side);
        viewPager=findViewById(R.id.goals_viewpage);
        bottomNavigationView=findViewById(R.id.goals_bottom_tab);
        initdrawer();
        navigationView.setNavigationItemSelectedListener(this);

        decision_ViewPagerAdapter goals_viewpageradapter=new decision_ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());

        goals_viewpageradapter.addFragment(new Symptoms_fragment());
        goals_viewpageradapter.addFragment(new Goals_fragment());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(goals_viewpageradapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.side_mainmenu:
                Intent main1=new Intent(symptoms_goal.this, ongoing_main_menu.class);
                startActivity(main1);
                return true;
            case R.id.side_setting:
                Intent main2=new Intent(symptoms_goal.this, MainMenu.class);
                startActivity(main2);
                return true;
            case R.id.side_implementation:
                Intent main3=new Intent(symptoms_goal.this, Implementation.class);
                startActivity(main3);
                return true;
            case R.id.side_decision:
                Intent main4=new Intent(symptoms_goal.this, decision_making.class);
                startActivity(main4);
                return true;
            case R.id.goal_menu:
                Intent main5=new Intent(symptoms_goal.this, symptoms_goal.class);
                startActivity(main5);
                return true;
        }
        return false;
    }
}