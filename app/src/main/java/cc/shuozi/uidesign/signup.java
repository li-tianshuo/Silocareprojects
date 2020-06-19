package cc.shuozi.uidesign;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView text1 ;
    private TextView text2 ;
    private EditText editbox_email;
    private EditText editbox_passwd;
    private EditText editbox_fname;
    private EditText editbox_lname;
    private Button signup;
    private int shortAnimationDuration;
    private int STORAGE_PERMISSION_CODE = 1;
    private void informationupdate(FirebaseUser user,String f_name,String l_name)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first, middle, and last name
        Map<String, Object> userinformation = new HashMap<>();
        userinformation.put("uid",user.getUid());
        userinformation.put("First Name", f_name);
        userinformation.put("Last Name", l_name);



// Add a new document with a generated ID
        db.collection("users")
                .add(userinformation)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Status", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Status", "Error adding document", e);
                    }
                });
    }
    public void JSONmaker(String fname, String lname,String email,String passwd) throws JSONException
    {
        String FILENAME = "signup.json";
        String jsonsentence;
        JSONObject information=new JSONObject();
        information.put("First Name",fname);
        information.put("Last Name",lname);
        information.put("Email",email);
        information.put("Password",passwd);
        jsonsentence=information.toString();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(jsonsentence.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateUI(FirebaseUser user)
    {
        if (user!=null)
        {
            Intent intent=new Intent(signup.this, MainMenu.class);
            intent.putExtra("uid",user.getUid());
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        text1 = findViewById(R.id.textView3);
        text2 = findViewById(R.id.textView6);
        editbox_email = findViewById(R.id.editText);
        editbox_passwd = findViewById(R.id.editText2);
        editbox_fname = findViewById(R.id.editText3);
        editbox_lname = findViewById(R.id.editText4);
        signup = findViewById(R.id.button);

        text1.setAlpha(0.0f);
        text2.setAlpha(0.0f);
        editbox_email.setAlpha(0.0f);
        editbox_passwd.setAlpha(0.0f);
        editbox_fname.setAlpha(0.0f);
        editbox_lname.setAlpha(0.0f);
        signup.setAlpha(0.0f);

        text1.animate().alpha(1).setDuration(6000);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                text2.animate().alpha(1).setDuration(3000);
            }
        }, 2000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_email.animate().alpha(1).setDuration(3000);
            }
        }, 4000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_passwd.animate().alpha(1).setDuration(3000);
            }
        }, 5000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_fname.animate().alpha(1).setDuration(3000);
            }
        }, 6000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                editbox_lname.animate().alpha(1).setDuration(3000);
            }
        }, 7000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                signup.animate().alpha(1).setDuration(3000);
            }
        }, 8000);


        signup.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          String email = editbox_email.getText().toString();
                                          String password = editbox_passwd.getText().toString();
                                          final String f_name=editbox_fname.getText().toString();
                                          final String l_name=editbox_lname.getText().toString();


                                          mAuth.createUserWithEmailAndPassword(email, password)
                                                  .addOnCompleteListener(signup. this, new OnCompleteListener<AuthResult>() {
                                                      @Override
                                                      public void onComplete(@NonNull Task<AuthResult> task) {
                                                          if (task.isSuccessful()) {
                                                              // Sign in success, update UI with the signed-in user's information
                                                              Log.d("Status", "createUserWithEmail:success");
                                                              FirebaseUser user = mAuth.getCurrentUser();
                                                              informationupdate(user,f_name,l_name);
                                                              updateUI(user);
                                                              if(ContextCompat.checkSelfPermission(signup.this, Manifest.permission.READ_EXTERNAL_STORAGE )== PackageManager.PERMISSION_GRANTED){
                                                                 Toast.makeText(signup.this, "Permissions has already been granted",Toast.LENGTH_SHORT).show();

                                                              }else {
                                                                  requestStoragePermission();
                                                              }
                                                          } else {
                                                              // If sign in fails, display a message to the user.
                                                              Log.w("Status", "createUserWithEmail:failure", task.getException());
                                                              Toast.makeText(signup.this, "Authentication failed.",
                                                                      Toast.LENGTH_SHORT).show();
                                                              updateUI(null);
                                                          }


                                                      }
                                                  });

                                          /*

                   try {
                    JSONmaker(editbox_fname.getText().toString(), editbox_lname.getText().toString(), editbox_email.getText().toString(), editbox_passwd.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        */

                                      }
                                  });}

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

        } else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }

}






