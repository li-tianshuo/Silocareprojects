package cc.shuozi.uidesign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataBase extends signup implements ValueEventListener{
    private TextView tf_name;
    private TextView tl_name;
    private TextView tEmail;
    private TextView tpasswd;
    private Button signup;
    private FirebaseDatabase UserBase = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = UserBase.getReference();
    private DatabaseReference mChildRefrence = userRef.child("User");


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tEmail = (EditText) findViewById(R.id.editText);
        tpasswd = (EditText) findViewById(R.id.editText2);
        tf_name = (EditText) findViewById(R.id.editText3);
        tl_name = (EditText) findViewById(R.id.editText4);
        signup = findViewById(R.id.button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = tEmail.getText().toString();
                userRef.setValue(Email);
                String passwd = tpasswd.getText().toString();
                mChildRefrence.setValue(passwd);
                String f_Name = tf_name.getText().toString();
                String l_Name = tl_name.getText().toString();
            }
        });

    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

   Button clicker = findViewById(R.id.button);

}



