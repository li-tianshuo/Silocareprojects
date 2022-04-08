package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class symptoms_goal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ViewPager2 viewPager;
    private ActionBarDrawerToggle drawerToggle;
    private BottomNavigationView bottomNavigationView;
    private ImageView avator_symtoms;
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
        avator_symtoms= findViewById(R.id.avatar__symptoms_goals);
        if (avatorExists())
        {
            Uri imgUri=Uri.parse("file:///data/data/cc.shuozi.uidesign/avator.jpg");
            avator_symtoms.setImageURI(imgUri);
        }
        viewPager=findViewById(R.id.goals_viewpage);
        bottomNavigationView=findViewById(R.id.goals_bottom_tab);
        initdrawer();

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setItemIconTintList(null);
        decision_ViewPagerAdapter goals_viewpageradapter=new decision_ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        goals_viewpageradapter.addFragment(new Implementation());
        goals_viewpageradapter.addFragment(new Symptoms_fragment());
        goals_viewpageradapter.addFragment(new Goals_fragment());
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.summary_item:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.symptoms_item:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.goal_item:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(goals_viewpageradapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                super.onPageSelected(position);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.side_mainmenu:
                Intent main1=new Intent(symptoms_goal.this, ongoing_main_menu.class);
                startActivity(main1);
                finish();
                return true;
            case R.id.side_setting:
                Intent main2=new Intent(symptoms_goal.this, MainMenu.class);
                startActivity(main2);
                finish();
                return true;
            case R.id.side_implementation:
                Intent main3=new Intent(symptoms_goal.this, symptoms_goal.class);
                startActivity(main3);
                finish();
                return true;
            case R.id.side_decision:
                Intent main4=new Intent(symptoms_goal.this, decision_making.class);
                startActivity(main4);
                finish();
                return true;
                /*
            case R.id.goal_menu:
                Intent main5=new Intent(symptoms_goal.this, symptoms_goal.class);
                startActivity(main5);
                finish();
                return true;

                 */
            case R.id.information_menu:
                Intent main6=new Intent(symptoms_goal.this, information.class);
                startActivity(main6);
                finish();
                return true;
        }
        return false;
    }
    public boolean avatorExists()
    {
        try
        {
            File f=new File("file:///data/data/cc.shuozi.uidesign/avator.jpg");
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return true;
        }

        return false;
    }

}