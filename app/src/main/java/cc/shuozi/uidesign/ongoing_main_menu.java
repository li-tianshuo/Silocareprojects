package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class ongoing_main_menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView avator_ongoing;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CustomAdapter customAdapter;
    private void initdrawer() {
        drawerLayout=findViewById(R.id.drawerlayout_main);
        toolbar=findViewById(R.id.toolbar_main);





        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                /*
                recyclerView = (RecyclerView) findViewById(R.id.animated_list);
                mLayoutManager = new LinearLayoutManager(drawerView.getContext(), LinearLayoutManager.VERTICAL, false);

                customAdapter = new CustomAdapter();

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(customAdapter);

                 */
                //recyclerView.addItemDecoration(new DividerItemDecoration(drawerView.getContext(), DividerItemDecoration.VERTICAL));

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //customAdapter=new CustomAdapter(new String[]{""});
                /*
                recyclerView.setAdapter(null);
                recyclerView.clearAnimation();
                */
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
                finish();
                return true;
            case R.id.side_setting:
                Intent main2=new Intent(ongoing_main_menu.this, MainMenu.class);
                startActivity(main2);
                finish();
                return true;
            case R.id.side_implementation:
                Intent main3=new Intent(ongoing_main_menu.this, symptoms_goal.class);
                startActivity(main3);
                finish();
                return true;
            case R.id.side_decision:
                Intent main4=new Intent(ongoing_main_menu.this, decision_making.class);
                startActivity(main4);
                finish();
                return true;
                /*
            case R.id.goal_menu:
                Intent main5=new Intent(ongoing_main_menu.this, symptoms_goal.class);
                startActivity(main5);
                finish();
                return true;
            case R.id.data_e_menu:
                Intent main6=new Intent(ongoing_main_menu.this, rss_main.class);
                startActivity(main6);
                finish();
                return true;

                 */
            case R.id.information_menu:
                Intent main7=new Intent(ongoing_main_menu.this, information.class);
                startActivity(main7);
                finish();
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
        navigationView.getMenu().getItem(0).setIcon(R.mipmap.icons8_settings_32);
        ImageButton ongoing_px=findViewById(R.id.ongoing_px);
        ImageButton ongoing_pa=findViewById(R.id.ongoing_pa);
        ImageButton ongoing_ma=findViewById(R.id.ongoing_ma);
        ImageButton ongoing_diet=findViewById(R.id.ongoing_diet);
        Button ongoing_export=findViewById(R.id.ongoing_export);
        Button data_education=findViewById(R.id.data_education);
        avator_ongoing= findViewById(R.id.avator_ongoing_main);
        Log.e("!",String.valueOf(avatorExists()));
        if (avatorExists())
        {
            Uri imgUri=Uri.parse("file:///data/data/cc.shuozi.uidesign/avator.jpg");
            avator_ongoing.setImageURI(imgUri);
        }
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

        ongoing_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ongoing_main_menu.this,exportdata.class);
                intent.putExtra("mode",2);
                startActivity(intent);
            }
        });
        data_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ongoing_main_menu.this,data_education.class);
                startActivity(intent);
            }
        });
    }
    public void myItemClick(View view){
        int position = recyclerView.getChildAdapterPosition(view);
        switch(position)
        {
            case 0:
                Intent main1=new Intent(ongoing_main_menu.this, ongoing_main_menu.class);
                startActivity(main1);
                finish();
                break;
            case 1:
                Intent main7=new Intent(ongoing_main_menu.this, information.class);
                startActivity(main7);
                finish();
                break;
            case 2:
                Intent main3=new Intent(ongoing_main_menu.this, Implementation.class);
                startActivity(main3);
                finish();
                break;
            case 3:
                Intent main4=new Intent(ongoing_main_menu.this, decision_making.class);
                startActivity(main4);
                finish();
                break;
            case 4:
                Intent main5=new Intent(ongoing_main_menu.this, symptoms_goal.class);
                startActivity(main5);
                finish();
                break;
            case 5:
                Intent main6=new Intent(ongoing_main_menu.this, data_education.class);
                startActivity(main6);
                finish();
                break;
            case 6:
                Intent main2=new Intent(ongoing_main_menu.this, MainMenu.class);
                startActivity(main2);
                finish();
                break;

        }
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