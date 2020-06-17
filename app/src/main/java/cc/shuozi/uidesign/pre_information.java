package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class pre_information extends AppCompatActivity {
    private String documentid;
    private Button update;
    private Spinner condition1_layout;
    private Spinner condition2_layout;
    private EditText condition3_layout;
    private EditText condition4_layout;
    private String condition1;
    private String condition2;
    private String condition3;
    private String condition4;
    private FirebaseAuth mAuth;
    private boolean Status=false;
    private ArrayList<String> list=new ArrayList<String>();

    private void checkcondition(final callback oncallbackList)
    {
        mAuth=FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("users")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                list.add(doc.getString("Condition 1"));
                                list.add(doc.getString("Condition 2"));
                                list.add(doc.getString("Condition 3"));
                                list.add(doc.getString("Condition 4"));
                            }
                            oncallbackList.onCallbackList((ArrayList<String>) list);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });


    }
    private void getid(final callback oncallback)
    {
        mAuth=FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("users")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                documentid=doc.getId();
                            }
                            oncallback.onCallback(documentid);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_information);

        condition1_layout=findViewById(R.id.condition1);
        condition2_layout=findViewById(R.id.condition2);
        condition3_layout=findViewById(R.id.condition3);
        condition4_layout=findViewById(R.id.condition4);
        update=findViewById(R.id.update_information);

        update.setEnabled(false);
        update.setText("Please wait");
        getid(new callback() {
            @Override
            public void onCallback(String string) {
                documentid=string;
                update.setEnabled(true);
                update.setText("Set");
            }

            @Override
            public void onCallbackList(ArrayList<String> list) {

            }
        });

        checkcondition(new callback() {
            @Override
            public void onCallback(String string) {

            }

            @Override
            public void onCallbackList(ArrayList<String> list) {
                if (list.get(0)!=null) {
                    condition1_layout.setSelection(((ArrayAdapter) condition2_layout.getAdapter()).getPosition(list.get(0)));
                    condition2_layout.setSelection(((ArrayAdapter) condition2_layout.getAdapter()).getPosition(list.get(1)));
                    condition3_layout.setText(list.get(2));
                    condition4_layout.setText(list.get(3));
                    Status=true;
                }else{
                    Status=false;
                }
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                condition1=condition1_layout.getSelectedItem().toString();
                condition2=condition2_layout.getSelectedItem().toString();
                condition3=condition3_layout.getText().toString();
                condition4=condition4_layout.getText().toString();

                DocumentReference updateref = db.collection("users").document(documentid);

                updateref
                        .update("Condition 1", condition1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Status", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Status", "Error updating document", e);
                            }
                        });

                updateref
                        .update("Condition 2", condition2)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Status", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Status", "Error updating document", e);
                            }
                        });

                updateref
                        .update("Condition 3", condition3)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Status", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Status", "Error updating document", e);
                            }
                        });

                updateref
                        .update("Condition 4", condition4)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Status", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Status", "Error updating document", e);
                            }
                        });

            }
        });
    }
}