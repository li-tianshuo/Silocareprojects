package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
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

public class add_goal extends AppCompatActivity {
    private String goal_description_string;
    private String goal_note_string;
    private ArrayList<String> description=new ArrayList<>();
    private ArrayList<String> note= new ArrayList<>();
    private my_adapter_goal my_adapter=new my_adapter_goal(add_goal.this,description,note);
    private int hour;
    private int minute;
    private int year;
    private int month;
    private int day;
    private FirebaseAuth mAuth;
    private String uid;
    private String data[][];
    private int a;
    private int priority;
    private String documentid;
    private int i;

    private void getevent(final callback oncallbackString) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("goal").document(documentid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                            data[0][0] = String.valueOf(document.get("name"));
                            data[0][1] = String.valueOf(document.get("year"));
                            data[0][2] = String.valueOf(document.get("month"));
                            data[0][3] = String.valueOf(document.get("day"));
                            data[0][4] = String.valueOf(document.get("hour"));
                            data[0][5] = String.valueOf(document.get("minute"));
                            data[0][6] = String.valueOf(document.get("priority"));
                            data[0][7] = String.valueOf(document.get("color"));
                            for (int b = 0; b >= 0; b++) {
                                if (document.getString("Description " + b) == null) {
                                    Log.e("success", "break");
                                    break;
                                } else {
                                    data[0][9 + b] = String.valueOf(document.get("Description " + b));
                                    data[0][10 + b] = String.valueOf(document.get("Note " + b));
                                }
                            }
                            i++;
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    oncallbackString.onCallbackListstring(data);
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });

        }


    private void getsymptospriority(final callback oncallbackString)
    {
        a=0;
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("goal")
                .whereEqualTo("uid", value)
                .whereEqualTo("type","Symptom")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                a++;
                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            oncallbackString.onCallbacknumber(a);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void getgoalspriority(final callback oncallbackString)
    {
        a=0;
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("goal")
                .whereEqualTo("uid", value)
                .whereEqualTo("type","Goal")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                a++;
                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            oncallbackString.onCallbacknumber(a);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void onCreategoalselection()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View foodView = layoutInflater.inflate(R.layout.goal_selection, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(foodView);

        final EditText goal_description=foodView.findViewById(R.id.goal_description);
        final EditText goal_note= foodView.findViewById(R.id.goal_note);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if ((goal_description.getText().toString().isEmpty())||(goal_note.getText().toString().isEmpty())){
                                    Toast.makeText(add_goal.this,"Nothing in Title!",Toast.LENGTH_LONG).show();
                                }else{
                                    goal_description_string=goal_description.getText().toString();
                                    goal_note_string=goal_note.getText().toString();
                                    my_adapter.add_description(goal_description_string,goal_note_string);
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
    public class my_adapter_goal extends BaseAdapter {
        private Context context;
        private ArrayList<String> description;
        private ArrayList<String> note;

        public my_adapter_goal(Context context,ArrayList<String> description,ArrayList<String> note) {
            this.context=context;
            this.description=description;
            this.note=note;
        }
        @Override
        public int getCount() {
            return description.size();
        }

        @Override
        public Object getItem(int position) {
            return description.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void add_description(String description_string, String note_string){
            description.add(description_string);
            note.add(note_string);
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.food_select_list, parent, false);
            final TextView description_text = (TextView) convertView.findViewById(R.id.food_name_on_list);
            TextView note_text = (TextView) convertView.findViewById(R.id.food_weight_on_list);
            Button delete=(Button)convertView.findViewById(R.id.list_delete_item);
            description_text.setTag(position);
            description_text.setText(description.get(position));
            note_text.setText(note.get(position));
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    description.remove(Integer.parseInt(String.valueOf(description_text.getTag())));
                    note.remove(Integer.parseInt(String.valueOf(description_text.getTag())));
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);
        ListView listview=findViewById(R.id.goal_thing);
        listview.setAdapter(my_adapter);
        FloatingActionButton add_goal_fab=findViewById(R.id.add_goal_fab);
        final Button symptoms=findViewById(R.id.symptoms_button);
        final Button goal=findViewById(R.id.goal_button);
        final EditText name=findViewById(R.id.goal_name);
        final TextView time=findViewById(R.id.goal_time);
        final TextView date=findViewById(R.id.goal_date);
        final Calendar calendar=Calendar.getInstance();
        hour=calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);
        year= calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        data=new String[1][99];

        add_goal_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreategoalselection();
            }
        });
        final int id = getIntent().getIntExtra("status",0);
        documentid=getIntent().getStringExtra("documentid");
        if (id==0) //add symptoms
        {

            if (minute < 10){
                time.setText(hour+":"+"0"+minute);
            }else {
                time.setText(hour+":"+minute);
            }

            date.setText(month+"/"+day+"/"+year);

            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(add_goal.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            hour = hourOfDay;
                            add_goal.this.minute=minute;
                            if (minute < 10){
                                time.setText(hour+":"+"0"+minute);
                            }else {
                                time.setText(hour+":"+minute);
                            }
                        }
                    }, hour, minute, true).show();
                }
            });
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(add_goal.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            add_goal.this.year=year;
                            add_goal.this.month=month;
                            day=dayOfMonth;
                            date.setText(month+"/"+day+"/"+year);
                        }
                    },year,month,day).show();
                }
            });


            symptoms.setClickable(false);
            symptoms.setEnabled(false);
            goal.setVisibility(View.GONE);
            getsymptospriority(new callback() {
                @Override
                public void onCallback(String string) {

                }

                @Override
                public void onCallbacknumber(int i) {
                    priority=i;
                    symptoms.setClickable(true);
                    symptoms.setEnabled(true);
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
        }else if (id==1) //add goal
        {

            if (minute < 10){
            time.setText(hour+":"+"0"+minute);
        }else {
            time.setText(hour+":"+minute);
        }

            date.setText(month+"/"+day+"/"+year);

            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(add_goal.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            hour = hourOfDay;
                            add_goal.this.minute=minute;
                            if (minute < 10){
                                time.setText(hour+":"+"0"+minute);
                            }else {
                                time.setText(hour+":"+minute);
                            }
                        }
                    }, hour, minute, true).show();
                }
            });
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(add_goal.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            add_goal.this.year=year;
                            add_goal.this.month=month;
                            day=dayOfMonth;
                            date.setText(month+"/"+day+"/"+year);
                        }
                    },year,month,day).show();
                }
            });
            goal.setClickable(false);
            goal.setEnabled(false);
            symptoms.setVisibility(View.GONE);
            getgoalspriority(new callback() {
                @Override
                public void onCallback(String string) {

                }

                @Override
                public void onCallbacknumber(int i) {
                    priority=i;
                    goal.setClickable(true);
                    goal.setEnabled(true);
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

        }else if (id==2) //update symptoms
        {

            symptoms.setClickable(false);
            symptoms.setEnabled(false);
            symptoms.setText("Update Symptom");
            goal.setVisibility(View.GONE);
            getevent(new callback() {
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
                    /*
            data[i][0] = String.valueOf(document.get("name"));
            data[i][1] = String.valueOf(document.get("year"));
            data[i][2] = String.valueOf(document.get("month"));
            data[i][3] = String.valueOf(document.get("day"));
            data[i][4] = String.valueOf(document.get("hour"));
            data[i][5] = String.valueOf(document.get("minute"));
            data[i][6] = String.valueOf(document.get("priority"));
            data[i][7] = String.valueOf(document.get("color"));

             */
                    name.setText(data[0][0]);
                    year=Integer.parseInt(data[0][1]);
                    month=Integer.parseInt(data[0][2]);
                    day=Integer.parseInt(data[0][3]);
                    hour=Integer.parseInt(data[0][4]);
                    minute=Integer.parseInt(data[0][5]);

                    if (minute < 10){
                        time.setText(hour+":"+"0"+minute);
                    }else {
                        time.setText(hour+":"+minute);
                    }

                    date.setText(month+"/"+day+"/"+year);

                    time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new TimePickerDialog(add_goal.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    hour = hourOfDay;
                                    add_goal.this.minute=minute;
                                    if (minute < 10){
                                        time.setText(hour+":"+"0"+minute);
                                    }else {
                                        time.setText(hour+":"+minute);
                                    }
                                }
                            }, hour, minute, true).show();
                        }
                    });
                    date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(add_goal.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    add_goal.this.year=year;
                                    add_goal.this.month=month;
                                    day=dayOfMonth;
                                    date.setText(month+"/"+day+"/"+year);
                                }
                            },year,month,day).show();
                        }
                    });
                    int z=9;
                    while(z<data[0].length)
                    {
                        if (data[0][z]!=null)
                        {
                            my_adapter.add_description(data[0][z],data[0][z+1]);
                            z=z+2;
                        }else {
                            break;
                        }
                    }

                    symptoms.setClickable(true);
                    symptoms.setEnabled(true);
                }

                @Override
                public void onCallbacklistdiet(String[][] data, food[][] foods) {

                }
            });

        }else if (id==3) //update goal
        {
            goal.setClickable(false);
            goal.setEnabled(false);
            goal.setText("Update Goal");
            symptoms.setVisibility(View.GONE);
            getevent(new callback() {
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
                    /*
            data[i][0] = String.valueOf(document.get("name"));
            data[i][1] = String.valueOf(document.get("year"));
            data[i][2] = String.valueOf(document.get("month"));
            data[i][3] = String.valueOf(document.get("day"));
            data[i][4] = String.valueOf(document.get("hour"));
            data[i][5] = String.valueOf(document.get("minute"));
            data[i][6] = String.valueOf(document.get("priority"));
            data[i][7] = String.valueOf(document.get("color"));

             */
                    name.setText(data[0][0]);
                    year=Integer.parseInt(data[0][1]);
                    month=Integer.parseInt(data[0][2]);
                    day=Integer.parseInt(data[0][3]);
                    hour=Integer.parseInt(data[0][4]);
                    minute=Integer.parseInt(data[0][5]);

                    if (minute < 10){
                        time.setText(hour+":"+"0"+minute);
                    }else {
                        time.setText(hour+":"+minute);
                    }

                    date.setText(month+"/"+day+"/"+year);

                    time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new TimePickerDialog(add_goal.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    hour = hourOfDay;
                                    add_goal.this.minute=minute;
                                    if (minute < 10){
                                        time.setText(hour+":"+"0"+minute);
                                    }else {
                                        time.setText(hour+":"+minute);
                                    }
                                }
                            }, hour, minute, true).show();
                        }
                    });
                    date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(add_goal.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    add_goal.this.year=year;
                                    add_goal.this.month=month;
                                    day=dayOfMonth;
                                    date.setText(month+"/"+day+"/"+year);
                                }
                            },year,month,day).show();
                        }
                    });
                    int z=9;
                    while(z<data[0].length)
                    {
                        if (data[0][z]!=null)
                        {
                            my_adapter.add_description(data[0][z],data[0][z+1]);
                            z=z+2;
                        }else {
                            break;
                        }
                    }

                    goal.setClickable(true);
                    goal.setEnabled(true);
                }

                @Override
                public void onCallbacklistdiet(String[][] data, food[][] foods) {

                }
            });
        }

        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == 0) {
                    if (description.size() == 0 || note.size() == 0) {
                        Toast.makeText(add_goal.this, "You have no description or note in your item!", Toast.LENGTH_LONG).show();
                    } else {
                        uid=mAuth.getUid();
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> event = new HashMap<>();
                        event.put("name",name.getText().toString());
                        event.put("year", year);
                        event.put("month", month);
                        event.put("day", day);
                        event.put("hour", hour);
                        event.put("minute", minute);
                        event.put("type", "Symptom");
                        event.put("uid", uid);
                        event.put("priority", priority);
                        event.put("color",0x000000);

                        for (int i = 0; i < description.size(); i++) {
                            event.put("Description " + i, description.get(i).toString());
                            event.put("Note " + i, note.get(i).toString());
                        }
                        db.collection("goal")
                                .add(event)
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
                        finish();
                    }
                }else if(id==2)
                {

                }
            }
        });

        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == 1) {
                    if (description.size() == 0 || note.size() == 0) {
                        Toast.makeText(add_goal.this, "You have no description or note in your item!", Toast.LENGTH_LONG).show();
                    } else {
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        uid=mAuth.getUid();
                        Map<String, Object> event = new HashMap<>();
                        event.put("name",name.getText().toString());
                        event.put("year", year);
                        event.put("month", month);
                        event.put("hour", hour);
                        event.put("minute", minute);
                        event.put("day", day);
                        event.put("type", "Goal");
                        event.put("uid", uid);
                        event.put("priority", priority);
                        event.put("color",0x000000);

                        for (int i = 0; i < description.size(); i++) {
                            event.put("Description " + i, description.get(i).toString());
                            event.put("Note " + i, note.get(i).toString());
                        }
                        db.collection("goal")
                                .add(event)
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
                        finish();
                    }
                }else if(id==3)
                {

                }
            }
        });

    }

}