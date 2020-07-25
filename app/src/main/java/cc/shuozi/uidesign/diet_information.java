package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class diet_information extends AppCompatActivity {
    private String food_name_string;
    private String documentid;
    private FirebaseAuth mAuth;
    private String uid;
    private int food_weight_string;
    private String[][] information=new String[1][5];
    private food[][] foods_exist=new food[1][40];
    private int i=0;
    private ArrayList<food> data=new ArrayList<food>();
    private my_adapter_food_diet_information my_adapter=new my_adapter_food_diet_information(diet_information.this,data);
    private int time_id;
    private int year;
    private int month;
    private int day;
    private void getdocumentid(final callback oncallback) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value = user.getUid();
        db.collection("diet")
                .whereEqualTo("uid", value)
                .whereEqualTo("Time id",time_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                documentid = doc.getId();
                            }
                            oncallback.onCallback(documentid);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });

    }
    private void getinfo(final callback oncallbackString)
    {
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("diet")
                .whereEqualTo("uid", value)
                .whereEqualTo("Time id",time_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                /*
                                 * information[][0]->time uid
                                 * information[][1]->year
                                 * information[][2]->month
                                 * information[][3]->day
                                 * */
                                    information[0][0] = String.valueOf(doc.get("Time id"));
                                    information[0][1] = String.valueOf(doc.get("Diet year"));
                                    information[0][2] = String.valueOf(doc.get("Diet month"));
                                    information[0][3] = String.valueOf(doc.get("Diet day"));
                                    information[0][4] = doc.getString("Diet type");
                                    for (int b = 0;b>=0;b++) {
                                        if (doc.getString("Food name " + b)==null) {
                                            Log.e("success","break");
                                            break;
                                        }else{
                                            food cd=new food(doc.getString("Food name " + String.valueOf(b)), Integer.parseInt(String.valueOf(doc.get("Food Weight " + String.valueOf(b)))));
                                            foods_exist[0][b]=cd;
                                        }
                                    }
                                    i++;


                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            oncallbackString.onCallbacklistdiet(information,foods_exist);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(diet_information.this,decision_making.class);
        intent.putExtra("mode",2);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_information);
        ListView listview=findViewById(R.id.food_thing);
        listview.setAdapter(my_adapter);
        FloatingActionButton add_food_fab=findViewById(R.id.add_food);
        final Calendar calendar=Calendar.getInstance();
        Button add_diet=findViewById(R.id.add_diet);
        final Spinner diet_type=findViewById(R.id.diet_type);
        final Button update_diet=findViewById(R.id.update_diet);
        final EditText diet_date=findViewById(R.id.diet_date);
        int id = getIntent().getIntExtra("status",0);
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uid=user.getUid();
        add_food_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreatefoodselection();
            }
        });
        if (id==0)
        {
            add_diet.setEnabled(false);
            add_diet.setClickable(false);

            update_diet.setVisibility(View.GONE);
            year=calendar.get(Calendar.YEAR);
            month=calendar.get(Calendar.MONTH);
            day= calendar.get(Calendar.DAY_OF_MONTH);
            diet_date.setText(month+"/"+day+"/"+year);
            time_id=(int)System.currentTimeMillis();
            diet_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(diet_information.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            diet_information.this.year=year;
                            diet_information.this.month=month;
                            day=dayOfMonth;
                            diet_date.setText(month+"/"+day+"/"+year);
                        }
                    },year,month,day).show();
                }
            });

            add_diet.setEnabled(true);
            add_diet.setClickable(true);

        }else if (id==1)
        {
            time_id=getIntent().getIntExtra("time_id",0);
            update_diet.setEnabled(false);
            update_diet.setClickable(false);
            add_diet.setVisibility(View.GONE);
            getdocumentid(new callback() {
                @Override
                public void onCallback(String string) {
                    documentid=string;
                    getinfo(new callback() {
                        @Override
                        public void onCallback(String string) {

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

                        @Override
                        public void onCallbacklistdiet(String[][] information_use, food[][] foods) {
                            update_diet.setEnabled(true);
                            update_diet.setClickable(true);
                            year=Integer.parseInt(information_use[0][1]);
                            month=Integer.parseInt(information_use[0][2]);
                            day= Integer.parseInt(information_use[0][3]);
                            diet_date.setText(month+"/"+day+"/"+year);
                            diet_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DatePickerDialog(diet_information.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            diet_information.this.year=year;
                                            diet_information.this.month=month;
                                            day=dayOfMonth;
                                            diet_date.setText(month+"/"+day+"/"+year);
                                        }
                                    },year,month,day).show();
                                }
                            });
                            diet_type.setSelection(((ArrayAdapter) diet_type.getAdapter()).getPosition(information_use[0][4]));
                            int a=0;
                            while(foods[0][a]!=null)
                            {
                                my_adapter.addFood(foods[0][a]);
                                a++;
                            }
                        }
                    });
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

                @Override
                public void onCallbacklistdiet(String[][] data, food[][] foods) {

                }
            });
        }

        update_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size()==0||data.size()>40)
                {
                    Toast.makeText(diet_information.this,"You have no or too much food in your item!",Toast.LENGTH_LONG).show();
                }else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference updateref = db.collection("diet").document(documentid);
                    updateref
                            .update("Diet year",year)
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
                            .update("Diet month",month)
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
                            .update("Diet day",day)
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
                            .update("Diet type",diet_type.getSelectedItem().toString())
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
                    for (int i = 0; i < data.size(); i++) {
                        updateref
                                .update("Food name " + i,data.get(i).getName())
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
                                .update("Food Weight " + i,data.get(i).getWeight())
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
                }
                Intent intent = new Intent(diet_information.this,decision_making.class);
                intent.putExtra("mode",2);
                startActivity(intent);
                finish();
            }

        });

        add_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size()==0||data.size()>40)
                {
                    Toast.makeText(diet_information.this,"You have no or too much food in your item!",Toast.LENGTH_LONG).show();
                }else {
                    time_id = (int) System.currentTimeMillis();
                    final FirebaseFirestore db = FirebaseFirestore.getInstance();

                    Map<String, Object> diet_event = new HashMap<>();
                    diet_event.put("Diet year", year);
                    diet_event.put("Diet month", month);
                    diet_event.put("Diet day", day);
                    diet_event.put("Diet type", diet_type.getSelectedItem().toString());
                    diet_event.put("uid", uid);
                    diet_event.put("Time id", time_id);
                    for (int i = 0; i < data.size(); i++) {
                        diet_event.put("Food name " + i, data.get(i).getName());
                        diet_event.put("Food Weight " + i, data.get(i).getWeight());
                    }
                    db.collection("diet")
                            .add(diet_event)
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
                    Log.e("Status", "Set Successful!");
                    Intent intent = new Intent(diet_information.this,decision_making.class);
                    intent.putExtra("mode",2);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    private void onCreatefoodselection()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View foodView = layoutInflater.inflate(R.layout.food_selection, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(foodView);

        final EditText food_name=foodView.findViewById(R.id.food_name);
        final EditText food_weight= foodView.findViewById(R.id.food_weight);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if ((food_name.getText().toString().isEmpty())||(food_weight.getText().toString().isEmpty())){
                                    Toast.makeText(diet_information.this,"Nothing in Food item!",Toast.LENGTH_LONG).show();
                                }else{
                                    food_name_string=food_name.getText().toString();
                                    food_weight_string=Integer.parseInt(food_weight.getText().toString());
                                    food food=new food(food_name_string,food_weight_string);
                                    my_adapter.addFood(food);
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }
    public class my_adapter_food_diet_information extends BaseAdapter {
        private Context context;
        private ArrayList<food> data_adapter=new ArrayList<food>();

        public my_adapter_food_diet_information(Context context,ArrayList<food> data) {
            this.context=context;
            this.data_adapter=data;
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addFood(food food){
            data.add(food);
            notifyDataSetChanged();
            }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.food_select_list, parent, false);
            final TextView name = (TextView) convertView.findViewById(R.id.food_name_on_list);
            TextView weight = (TextView) convertView.findViewById(R.id.food_weight_on_list);
            Button delete=(Button)convertView.findViewById(R.id.list_delete_item);
            food food=data.get(position);
            name.setTag(position);
            name.setText(food.getName());
            weight.setText(String.valueOf(food.getWeight())+" g");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(Integer.parseInt(String.valueOf(name.getTag())));
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }




}