package cc.shuozi.uidesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class Setting extends AppCompatActivity {
    private Button logout;
    private ListView information_setting;
    private ListView app_setting;
    private String datas1[]={"Modify account information", "Set Condition information", "Set Doctor Information"};
    private String datas2[]={"Support Center", "Contact us", "About us"};
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        information_setting=findViewById(R.id.setting_information);
        app_setting=findViewById(R.id.setting_app);
        adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas1);
        adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas2);
        information_setting.setAdapter(adapter1);
        app_setting.setAdapter(adapter2);
        information_setting.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0 :
                        //startActivity(new Intent(Setting.this, account_information.class));
                        break;
                    case 1 :
                        startActivity(new Intent(Setting.this, pre_information.class));
                        break;
                    case 2 :
                        Intent intent=new Intent(Setting.this, doctor_information.class);
                        intent.putExtra("status","add_major");
                        startActivity(intent);
                        break;
                    default :

                }
            }
        });
        logout=findViewById(R.id.log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Setting.this, MainActivity.class));
            }
        });
    }
}