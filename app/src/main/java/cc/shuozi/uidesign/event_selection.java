package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

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
import java.util.HashMap;
import java.util.Map;

public class event_selection extends AppCompatActivity {
    private int start_year;
    private int start_month;
    private int start_day;
    private int start_hour;
    private int start_minute;
    private int end_year;
    private int end_month;
    private int end_day;
    private int end_hour;
    private int end_minute;
    private FirebaseAuth mAuth;
    private int time_id;
    private String documentid;
    private String uid;
    private String data[][]=new String[1][13];
    private void getpaid(final callback oncallback) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value = user.getUid();
        db.collection("physicalactivity")
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
    private void getmaid(final callback oncallback) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value = user.getUid();
        db.collection("mentalactivity")
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
    private void getmentalactivity(final callback oncallback) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value = user.getUid();
        db.collection("mentalactivity")
                .whereEqualTo("uid", value)
                .whereEqualTo("Time id",time_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                data[0][0]= String.valueOf(doc.get("Time id"));
                                data[0][1]=doc.getString("Event");
                                data[0][2]= String.valueOf(doc.get("Event start year"));
                                data[0][3]= String.valueOf(doc.get("Event start month"));
                                data[0][4]= String.valueOf(doc.get("Event start day"));
                                data[0][5]= String.valueOf(doc.get("Event start hour"));
                                data[0][6]= String.valueOf(doc.get("Event start minute"));
                                data[0][7]= String.valueOf(doc.get("Event end year"));
                                data[0][8]= String.valueOf(doc.get("Event end month"));
                                data[0][9]= String.valueOf(doc.get("Event end day"));
                                data[0][10]= String.valueOf(doc.get("Event end hour"));
                                data[0][11]= String.valueOf(doc.get("Event end minute"));
                            }
                            oncallback.onCallbackListstring(data);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });

    }
    private void getphysicalactivity(final callback oncallback) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value = user.getUid();
        db.collection("physicalactivity")
                .whereEqualTo("uid", value)
                .whereEqualTo("Time id",time_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                data[0][0]= String.valueOf(doc.get("Time id"));
                                data[0][1]=doc.getString("Event");
                                data[0][2]= String.valueOf(doc.get("Event start year"));
                                data[0][3]= String.valueOf(doc.get("Event start month"));
                                data[0][4]= String.valueOf(doc.get("Event start day"));
                                data[0][5]= String.valueOf(doc.get("Event start hour"));
                                data[0][6]= String.valueOf(doc.get("Event start minute"));
                                data[0][7]= String.valueOf(doc.get("Event end year"));
                                data[0][8]= String.valueOf(doc.get("Event end month"));
                                data[0][9]= String.valueOf(doc.get("Event end day"));
                                data[0][10]= String.valueOf(doc.get("Event end hour"));
                                data[0][11]= String.valueOf(doc.get("Event end minute"));
                            }
                            oncallback.onCallbackListstring(data);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(event_selection.this,decision_making.class);
        intent.putExtra("mode",3);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_selection);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uid=user.getUid();
        Button add_ma=findViewById(R.id.add_ma);
        final Button update_ma=findViewById(R.id.update_ma);
        Button add_pa=findViewById(R.id.add_pa);
        final Button update_pa=findViewById(R.id.update_pa);
        final TextView start_time=findViewById(R.id.start_time);
        final TextView end_time=findViewById(R.id.end_time);
        final TextView start_time_date=findViewById(R.id.start_time_date);
        final TextView end_time_date=findViewById(R.id.end_time_date);
        final Spinner ma_selection=findViewById(R.id.ma_type);
        final Spinner pa_selection=findViewById(R.id.pa_type);
        final Calendar calendar=Calendar.getInstance();
        start_hour=calendar.get(Calendar.HOUR_OF_DAY);
        start_minute=calendar.get(Calendar.MINUTE);
        start_year= calendar.get(Calendar.YEAR);
        start_month=calendar.get(Calendar.MONTH);
        start_day=calendar.get(Calendar.DAY_OF_MONTH);
        end_hour=calendar.get(Calendar.HOUR_OF_DAY);
        end_minute=calendar.get(Calendar.MINUTE);
        end_year= calendar.get(Calendar.YEAR);
        end_month=calendar.get(Calendar.MONTH);
        end_day=calendar.get(Calendar.DAY_OF_MONTH);
        int id = getIntent().getIntExtra("status",1);
        if (id == 0) //add mental activity
        {
            if (event_selection.this.start_minute < 10){
                start_time.setText(start_hour+":"+"0"+event_selection.this.start_minute);
            }else {
                start_time.setText(start_hour+":"+event_selection.this.start_minute);
            }

            if (event_selection.this.end_minute < 10){
                end_time.setText(end_hour+":"+"0"+event_selection.this.end_minute);
            }else {
                end_time.setText(end_hour+":"+event_selection.this.end_minute);
            }

            start_time_date.setText(start_month+"/"+start_day+"/"+start_year);
            end_time_date.setText(end_month+"/"+end_day+"/"+end_year);

            start_time_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(event_selection.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            event_selection.this.start_year=year;
                            event_selection.this.start_month=month;
                            start_day=dayOfMonth;
                            start_time_date.setText(start_month+"/"+start_day+"/"+start_year);
                        }
                    },start_year,start_month,start_day).show();
                }
            });
            start_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(event_selection.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            start_hour = hourOfDay;
                            start_minute=minute;
                            if (start_minute < 10){
                                start_time.setText(start_hour+":"+"0"+start_minute);
                            }else {
                                start_time.setText(start_hour+":"+start_minute);
                            }
                        }
                    }, start_hour, start_minute, true).show();
                }
            });
            end_time_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(event_selection.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            event_selection.this.end_year=year;
                            event_selection.this.end_month=month;
                            end_day=dayOfMonth;
                            end_time_date.setText(end_month+"/"+end_day+"/"+end_year);
                        }
                    },end_year,end_month,end_day).show();
                }
            });
            end_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(event_selection.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            end_hour = hourOfDay;
                            end_minute=minute;
                            if (end_minute < 10){
                                end_time.setText(end_hour+":"+"0"+end_minute);
                            }else {
                                end_time.setText(end_hour+":"+end_minute);
                            }
                        }
                    }, end_hour, end_minute, true).show();
                }
            });
            add_ma.setEnabled(false);
            add_ma.setClickable(false);
            update_ma.setVisibility(View.GONE);
            add_pa.setVisibility(View.GONE);
            update_pa.setVisibility(View.GONE);
            pa_selection.setVisibility(View.GONE);

            add_ma.setEnabled(true);
            add_ma.setClickable(true);
        }else if (id == 1) //update mental activity
        {
            add_ma.setVisibility(View.GONE);
            update_ma.setEnabled(false);
            update_ma.setClickable(false);
            add_pa.setVisibility(View.GONE);
            update_pa.setVisibility(View.GONE);
            pa_selection.setVisibility(View.GONE);
            time_id=getIntent().getIntExtra("time_id",0);
            getmaid(new callback() {
                @Override
                public void onCallback(String string) {
                    documentid=string;
                    getmentalactivity(new callback() {
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
                            update_ma.setEnabled(true);
                            update_ma.setClickable(true);
                            ma_selection.setSelection(((ArrayAdapter) ma_selection.getAdapter()).getPosition(data[0][1]));
                            start_year=Integer.parseInt(data[0][2]);
                            start_month=Integer.parseInt(data[0][3]);
                            start_day=Integer.parseInt(data[0][4]);
                            start_hour=Integer.parseInt(data[0][5]);
                            start_minute=Integer.parseInt(data[0][6]);
                            end_year=Integer.parseInt(data[0][7]);
                            end_month=Integer.parseInt(data[0][8]);
                            end_day=Integer.parseInt(data[0][9]);
                            end_hour=Integer.parseInt(data[0][10]);
                            end_minute=Integer.parseInt(data[0][11]);
                            if (event_selection.this.start_minute < 10){
                                start_time.setText(start_hour+":"+"0"+event_selection.this.start_minute);
                            }else {
                                start_time.setText(start_hour+":"+event_selection.this.start_minute);
                            }

                            if (event_selection.this.end_minute < 10){
                                end_time.setText(end_hour+":"+"0"+event_selection.this.end_minute);
                            }else {
                                end_time.setText(end_hour+":"+event_selection.this.end_minute);
                            }

                            start_time_date.setText(start_month+"/"+start_day+"/"+start_year);
                            end_time_date.setText(end_month+"/"+end_day+"/"+end_year);

                            start_time_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DatePickerDialog(event_selection.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            event_selection.this.start_year=year;
                                            event_selection.this.start_month=month;
                                            start_day=dayOfMonth;
                                            start_time_date.setText(start_month+"/"+start_day+"/"+start_year);
                                        }
                                    },start_year,start_month,start_day).show();
                                }
                            });
                            start_time.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new TimePickerDialog(event_selection.this, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            start_hour = hourOfDay;
                                            start_minute=minute;
                                            if (start_minute < 10){
                                                start_time.setText(start_hour+":"+"0"+start_minute);
                                            }else {
                                                start_time.setText(start_hour+":"+start_minute);
                                            }
                                        }
                                    }, start_hour, start_minute, true).show();
                                }
                            });
                            end_time_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DatePickerDialog(event_selection.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            event_selection.this.end_year=year;
                                            event_selection.this.end_month=month;
                                            end_day=dayOfMonth;
                                            end_time_date.setText(end_month+"/"+end_day+"/"+end_year);
                                        }
                                    },end_year,end_month,end_day).show();
                                }
                            });
                            end_time.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new TimePickerDialog(event_selection.this, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            end_hour = hourOfDay;
                                            end_minute=minute;
                                            if (end_minute < 10){
                                                end_time.setText(end_hour+":"+"0"+end_minute);
                                            }else {
                                                end_time.setText(end_hour+":"+end_minute);
                                            }
                                        }
                                    }, end_hour, end_minute, true).show();

                                }
                            });

                        }

                        @Override
                        public void onCallbacklistdiet(String[][] data, food[][] foods) {

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

        }else if (id == 2) //add physical activity
        {
            if (event_selection.this.start_minute < 10){
                start_time.setText(start_hour+":"+"0"+event_selection.this.start_minute);
            }else {
                start_time.setText(start_hour+":"+event_selection.this.start_minute);
            }

            if (event_selection.this.end_minute < 10){
                end_time.setText(end_hour+":"+"0"+event_selection.this.end_minute);
            }else {
                end_time.setText(end_hour+":"+event_selection.this.end_minute);
            }

            start_time_date.setText(start_month+"/"+start_day+"/"+start_year);
            end_time_date.setText(end_month+"/"+end_day+"/"+end_year);

            start_time_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(event_selection.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            event_selection.this.start_year=year;
                            event_selection.this.start_month=month;
                            start_day=dayOfMonth;
                            start_time_date.setText(start_month+"/"+start_day+"/"+start_year);
                        }
                    },start_year,start_month,start_day).show();
                }
            });
            start_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(event_selection.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            start_hour = hourOfDay;
                            start_minute=minute;
                            if (start_minute < 10){
                                start_time.setText(start_hour+":"+"0"+start_minute);
                            }else {
                                start_time.setText(start_hour+":"+start_minute);
                            }
                        }
                    }, start_hour, start_minute, true).show();
                }
            });
            end_time_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(event_selection.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            event_selection.this.end_year=year;
                            event_selection.this.end_month=month;
                            end_day=dayOfMonth;
                            end_time_date.setText(end_month+"/"+end_day+"/"+end_year);
                        }
                    },end_year,end_month,end_day).show();
                }
            });
            end_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(event_selection.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            end_hour = hourOfDay;
                            end_minute=minute;
                            if (end_minute < 10){
                                end_time.setText(end_hour+":"+"0"+end_minute);
                            }else {
                                end_time.setText(end_hour+":"+end_minute);
                            }
                        }
                    }, end_hour, end_minute, true).show();
                }
            });
            add_ma.setVisibility(View.GONE);
            update_ma.setVisibility(View.GONE);
            add_pa.setEnabled(false);
            add_pa.setClickable(false);
            update_pa.setVisibility(View.GONE);
            ma_selection.setVisibility(View.GONE);

            add_pa.setEnabled(true);
            add_pa.setClickable(true);
        }else if (id == 3) // update physical activity
        {
            time_id=getIntent().getIntExtra("time_id",0);
            add_ma.setVisibility(View.GONE);
            update_ma.setVisibility(View.GONE);
            add_pa.setVisibility(View.GONE);
            update_pa.setEnabled(false);
            update_pa.setClickable(false);
            ma_selection.setVisibility(View.GONE);

            getpaid(new callback() {
                @Override
                public void onCallback(String string) {
                    documentid=string;
                    getphysicalactivity(new callback() {
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
                            update_pa.setEnabled(true);
                            update_pa.setClickable(true);
                            pa_selection.setSelection(((ArrayAdapter) pa_selection.getAdapter()).getPosition(data[0][1]));
                            start_year=Integer.parseInt(data[0][2]);
                            start_month=Integer.parseInt(data[0][3]);
                            start_day=Integer.parseInt(data[0][4]);
                            start_hour=Integer.parseInt(data[0][5]);
                            start_minute=Integer.parseInt(data[0][6]);
                            end_year=Integer.parseInt(data[0][7]);
                            end_month=Integer.parseInt(data[0][8]);
                            end_day=Integer.parseInt(data[0][9]);
                            end_hour=Integer.parseInt(data[0][10]);
                            end_minute=Integer.parseInt(data[0][11]);
                            if (event_selection.this.start_minute < 10){
                                start_time.setText(start_hour+":"+"0"+event_selection.this.start_minute);
                            }else {
                                start_time.setText(start_hour+":"+event_selection.this.start_minute);
                            }

                            if (event_selection.this.end_minute < 10){
                                end_time.setText(end_hour+":"+"0"+event_selection.this.end_minute);
                            }else {
                                end_time.setText(end_hour+":"+event_selection.this.end_minute);
                            }

                            start_time_date.setText(start_month+"/"+start_day+"/"+start_year);
                            end_time_date.setText(end_month+"/"+end_day+"/"+end_year);

                            start_time_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DatePickerDialog(event_selection.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            event_selection.this.start_year=year;
                                            event_selection.this.start_month=month;
                                            start_day=dayOfMonth;
                                            start_time_date.setText(start_month+"/"+start_day+"/"+start_year);
                                        }
                                    },start_year,start_month,start_day).show();
                                }
                            });
                            start_time.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new TimePickerDialog(event_selection.this, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            start_hour = hourOfDay;
                                            start_minute=minute;
                                            if (start_minute < 10){
                                                start_time.setText(start_hour+":"+"0"+start_minute);
                                            }else {
                                                start_time.setText(start_hour+":"+start_minute);
                                            }
                                        }
                                    }, start_hour, start_minute, true).show();
                                }
                            });
                            end_time_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DatePickerDialog(event_selection.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            event_selection.this.end_year=year;
                                            event_selection.this.end_month=month;
                                            end_day=dayOfMonth;
                                            end_time_date.setText(end_month+"/"+end_day+"/"+end_year);
                                        }
                                    },end_year,end_month,end_day).show();
                                }
                            });
                            end_time.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new TimePickerDialog(event_selection.this, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            end_hour = hourOfDay;
                                            end_minute=minute;
                                            if (end_minute < 10){
                                                end_time.setText(end_hour+":"+"0"+end_minute);
                                            }else {
                                                end_time.setText(end_hour+":"+end_minute);
                                            }
                                        }
                                    }, end_hour, end_minute, true).show();
                                }
                            });

                        }

                        @Override
                        public void onCallbacklistdiet(String[][] data, food[][] foods) {

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

        add_pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_id=(int) System.currentTimeMillis();
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                String pa_event_selection=pa_selection.getSelectedItem().toString();
                Map<String, Object> pa_event = new HashMap<>();
                pa_event.put("Event start year",start_year);
                pa_event.put("Event start month", start_month);
                pa_event.put("Event start day", start_day);
                pa_event.put("Event start hour", start_hour);
                pa_event.put("Event start minute", start_minute);
                pa_event.put("Event end year",end_year);
                pa_event.put("Event end month", end_month);
                pa_event.put("Event end day", end_day);
                pa_event.put("Event end hour", end_hour);
                pa_event.put("Event end minute", end_minute);
                pa_event.put("Event",pa_event_selection);
                pa_event.put("uid", uid);
                pa_event.put("Time id", time_id);
                db.collection("physicalactivity")
                        .add(pa_event)
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
                Log.e("Status","Set Successful!");
                Intent intent = new Intent(event_selection.this,decision_making.class);
                intent.putExtra("mode",3);
                startActivity(intent);
                finish();
            }
        });
        add_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_id=(int) System.currentTimeMillis();
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                String ma_event_selection=ma_selection.getSelectedItem().toString();
                Map<String, Object> ma_event = new HashMap<>();
                ma_event.put("Event start year",start_year);
                ma_event.put("Event start month", start_month);
                ma_event.put("Event start day", start_day);
                ma_event.put("Event start hour", start_hour);
                ma_event.put("Event start minute", start_minute);
                ma_event.put("Event end year",end_year);
                ma_event.put("Event end month", end_month);
                ma_event.put("Event end day", end_day);
                ma_event.put("Event end hour", end_hour);
                ma_event.put("Event end minute", end_minute);
                ma_event.put("Event",ma_event_selection);
                ma_event.put("uid", uid);
                ma_event.put("Time id", time_id);
                db.collection("mentalactivity")
                        .add(ma_event)
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
                Log.e("Status","Set Successful!");
                Intent intent = new Intent(event_selection.this,decision_making.class);
                intent.putExtra("mode",4);
                startActivity(intent);
                finish();
            }
        });



        update_pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference updateref = db.collection("physicalactivity").document(documentid);
                updateref
                        .update("Event",pa_selection.getSelectedItem().toString())
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
                        .update("Event start year",start_year)
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
                        .update("Event start month",start_month)
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
                        .update("Event start day",start_day)
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
                        .update("Event start hour",start_hour)
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
                        .update("Event start minute",start_minute)
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
                        .update("Event end year",end_year)
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
                        .update("Event end month",end_month)
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
                        .update("Event end day",end_day)
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
                        .update("Event end hour",end_hour)
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
                        .update("Event end minute",end_minute)
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
                Intent intent = new Intent(event_selection.this,decision_making.class);
                intent.putExtra("mode",3);
                startActivity(intent);
                finish();
            }
        });

        update_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference updateref = db.collection("mentalactivity").document(documentid);
                updateref
                        .update("Event",ma_selection.getSelectedItem().toString())
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
                        .update("Event start year",start_year)
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
                        .update("Event start month",start_month)
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
                        .update("Event start day",start_day)
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
                        .update("Event start hour",start_hour)
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
                        .update("Event start minute",start_minute)
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
                        .update("Event end year",end_year)
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
                        .update("Event end month",end_month)
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
                        .update("Event end day",end_day)
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
                        .update("Event end hour",end_hour)
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
                        .update("Event end minute",end_minute)
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
                Intent intent = new Intent(event_selection.this,decision_making.class);
                intent.putExtra("mode",4);
                startActivity(intent);
                finish();
            }
        });
    }
}