package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class doctor_information extends AppCompatActivity {
    private int update_num;
    private FirebaseAuth mAuth;
    private String status;
    private Button add_major_doc;
    private Button update_doc;
    private Button add_doc;
    private EditText dname;
    private EditText demail;
    private EditText dphone;
    private String dname_string;
    private String demail_string;
    private String dphone_string;
    private int i;
    private int next_available;
    private int docnnum;
    private ArrayList<String> major=new ArrayList<String>();
    private ArrayList<String> doctor=new ArrayList<String>();
    private String documentid;

    private void getdoc(final callback oncallbacklist)
    {
        mAuth= FirebaseAuth.getInstance();
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
                                doctor.add(doc.getString("Doctor Name "+String.valueOf(update_num)));
                                doctor.add(doc.getString("Doctor Email "+String.valueOf(update_num)));
                                doctor.add(doc.getString("Doctor Phone "+String.valueOf(update_num)));
                            }
                            oncallbacklist.onCallbackList(doctor);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void checknextavailable(final callback oncallbacknumber)
    {
        mAuth= FirebaseAuth.getInstance();
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
                                for (next_available=1;next_available>0;next_available++)
                                {
                                    if (doc.getString("Doctor Status"+String.valueOf(next_available))=="false"||doc.getString("Doctor Name "+String.valueOf(next_available))==null)
                                    {
                                        break;
                                    }
                                }
                            }
                            oncallbacknumber.onCallbacknumber(next_available);
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

    private void getmajordoc(final callback oncallbacklist)
    {
        mAuth= FirebaseAuth.getInstance();
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
                                major.add(doc.getString("Primary Doctor Name"));
                                major.add(doc.getString("Primary Doctor Email"));
                                major.add(doc.getString("Primary Doctor Phone"));
                            }
                            oncallbacklist.onCallbackList(major);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void getdocnumber(final callback oncallbacknumber)
    {
        mAuth= FirebaseAuth.getInstance();
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
                                for (i=0; i>=0; i++)
                                {
                                    if (doc.getString("Doctor name "+i)==null)
                                    {
                                        break;
                                    }
                                }
                            }
                            oncallbacknumber.onCallbacknumber(i);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_information);
        add_major_doc=findViewById(R.id.set_major_doc);
        update_doc=findViewById(R.id.update_doc);
        add_doc=findViewById(R.id.add_doc);
        dname=findViewById(R.id.dname);
        demail=findViewById(R.id.demail);
        dphone=findViewById(R.id.dphone);

        Intent intent = getIntent();
        if (intent != null) {
            status = intent.getStringExtra("status");
        }
        getid(new callback() {
            @Override
            public void onCallback(String string) {
                documentid=string;
            }

            @Override
            public void onCallbacknumber(int i) {

            }

            @Override
            public void onCallbackList(ArrayList<String> list) {

            }

            @Override
            public void onCallbackListstring(String[][] data) {

            }
        });
        getdocnumber(new callback() {
            @Override
            public void onCallback(String string) {

            }

            @Override
            public void onCallbacknumber(int i) {
                docnnum=i;
            }

            @Override
            public void onCallbackList(ArrayList<String> list) {

            }

            @Override
            public void onCallbackListstring(String[][] data) {

            }
        });

        if (status.equals("update"))
        {
            update_num =  Integer.parseInt(intent.getStringExtra("list"));
            Log.e("Success", String.valueOf(update_num));
            add_major_doc.setVisibility(View.GONE);
            add_doc.setVisibility(View.GONE);
            update_doc.setEnabled(false);
            update_doc.setClickable(false);
            getdoc(new callback() {
                @Override
                public void onCallback(String string) {

                }

                @Override
                public void onCallbacknumber(int i) {

                }

                @Override
                public void onCallbackList(ArrayList<String> list) {
                    dname.setText(list.get(0));
                    demail.setText(list.get(1));
                    dphone.setText(list.get(2));
                    update_doc.setEnabled(true);
                    update_doc.setClickable(true);
                }

                @Override
                public void onCallbackListstring(String[][] data) {

                }
            });

        }else if (status.equals("add_major"))
        {
            add_doc.setVisibility(View.GONE);
            update_doc.setVisibility(View.GONE);
            add_major_doc.setEnabled(false);
            add_major_doc.setClickable(false);
            getmajordoc(new callback() {
                @Override
                public void onCallback(String string) {

                }

                @Override
                public void onCallbacknumber(int i) {

                }

                @Override
                public void onCallbackList(ArrayList<String> list) {
                    add_major_doc.setClickable(true);
                    add_major_doc.setEnabled(true);
                    if (list.get(0)!=null)
                    {
                        dname.setText(list.get(0));
                        demail.setText(list.get(1));
                        dphone.setText(list.get(2));
                    }
                }

                @Override
                public void onCallbackListstring(String[][] data) {

                }
            });
        } else if (status.equals("add_doc"))
        {
            update_doc.setVisibility(View.GONE);
            add_major_doc.setVisibility(View.GONE);
            add_doc.setEnabled(false);
            add_doc.setClickable(false);
            checknextavailable(new callback() {
                @Override
                public void onCallback(String string) {

                }

                @Override
                public void onCallbacknumber(int i) {
                    add_doc.setEnabled(true);
                    add_doc.setClickable(true);
                    next_available=i;
                }

                @Override
                public void onCallbackList(ArrayList<String> list) {

                }

                @Override
                public void onCallbackListstring(String[][] data) {

                }
            });
        }
        update_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                dname_string=dname.getText().toString();
                demail_string=demail.getText().toString();
                dphone_string=dphone.getText().toString();
                DocumentReference updateref = db.collection("users").document(documentid);

                updateref
                        .update("Doctor Name "+String.valueOf(update_num), dname_string)
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
                        .update("Doctor Status"+String.valueOf(update_num), true)
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
                        .update("Doctor Email "+String.valueOf(update_num), demail_string)
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
                        .update("Doctor Phone "+String.valueOf(update_num), dphone_string)
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
                finish();
            }
        });
        add_major_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                dname_string=dname.getText().toString();
                demail_string=demail.getText().toString();
                dphone_string=dphone.getText().toString();
                DocumentReference updateref = db.collection("users").document(documentid);

                updateref
                        .update("Primary Doctor Name", dname_string)
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
                        .update("Primary Doctor Email", demail_string)
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
                        .update("Primary Doctor Phone", dphone_string)
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
                finish();

            }
        });
        add_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                dname_string=dname.getText().toString();
                demail_string=demail.getText().toString();
                dphone_string=dphone.getText().toString();
                DocumentReference updateref = db.collection("users").document(documentid);

                updateref
                        .update("Doctor Name "+String.valueOf(next_available), dname_string)
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
                        .update("Doctor Status"+String.valueOf(next_available), true)
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
                        .update("Doctor Email "+String.valueOf(next_available), demail_string)
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
                        .update("Doctor Phone "+String.valueOf(next_available), dphone_string)
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
                finish();

            }
        });
    }

}