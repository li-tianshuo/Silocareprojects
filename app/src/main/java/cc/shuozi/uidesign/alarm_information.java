package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class alarm_information extends AppCompatActivity {
    private String documentid;
    private int minute;
    private int hour;
    private int year;
    private int month;
    private int day;
    private String uid;
    private FirebaseAuth mAuth;
    private int time_id;
    private String[][] data=new String[1][7];

    private void getid(final callback oncallback) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value = user.getUid();
        db.collection("px")
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
    private void getalarm(final callback oncallbackString)
    {
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("px")
                .whereEqualTo("uid", value)
                .whereEqualTo("Time id",time_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                data[0][0]= String.valueOf(doc.get("Time id"));
                                data[0][1]=doc.getString("Event note");
                                data[0][2]= String.valueOf(doc.get("Event year"));
                                data[0][3]= String.valueOf(doc.get("Event month"));
                                data[0][4]= String.valueOf(doc.get("Event day"));
                                data[0][5]= String.valueOf(doc.get("Event hour"));
                                data[0][6]= String.valueOf(doc.get("Event minute"));
                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            oncallbackString.onCallbackListstring(data);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uid=user.getUid();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_information);
        final TextView note=findViewById(R.id.px_note);
        Button add_alarm=findViewById(R.id.add_alarm);
        final Button update_alarm=findViewById(R.id.update_alarm);
        final TextView px_time=findViewById(R.id.px_time);
        final AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        final TextView px_date=findViewById(R.id.alarm_date);

        final Calendar calendar=Calendar.getInstance();
        hour=calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);
        year= calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);

        int id = getIntent().getIntExtra("status",1);
        if (id == 0) {
            add_alarm.setVisibility(View.GONE);
            update_alarm.setEnabled(false);
            update_alarm.setClickable(false);
            time_id=getIntent().getIntExtra("time_id",0);
            getid(new callback() {
                @Override
                public void onCallback(String string) {
                    documentid=string;
                    getalarm(new callback() {
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
                            update_alarm.setEnabled(true);
                            update_alarm.setClickable(true);
                            note.setText(data[0][1]);
                            year=Integer.parseInt(data[0][2]);
                            month=Integer.parseInt(data[0][3]);
                            day=Integer.parseInt(data[0][4]);
                            hour=Integer.parseInt(data[0][5]);
                            minute=Integer.parseInt(data[0][6]);
                            if (alarm_information.this.minute < 10){
                                px_time.setText(hour+":"+"0"+alarm_information.this.minute);
                            }else {
                                px_time.setText(hour+":"+alarm_information.this.minute);
                            }
                            px_date.setText(month+"/"+day+"/"+year);
                            px_date.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new DatePickerDialog(alarm_information.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            alarm_information.this.year=year;
                                            alarm_information.this.month=month;
                                            day=dayOfMonth;
                                            px_date.setText(month+"/"+day+"/"+year);
                                        }
                                    },year,month,day).show();
                                }
                            });

                            px_time.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new TimePickerDialog(alarm_information.this, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            hour = hourOfDay;
                                            alarm_information.this.minute=minute;
                                            if (alarm_information.this.minute < 10){
                                                px_time.setText(hour+":"+"0"+alarm_information.this.minute);
                                            }else {
                                                px_time.setText(hour+":"+alarm_information.this.minute);
                                            }
                                        }
                                    }, hour, minute, true).show();
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

        }else if (id == 1) {
            update_alarm.setVisibility(View.GONE);

            if (alarm_information.this.minute < 10){
                px_time.setText(hour+":"+"0"+alarm_information.this.minute);
            }else {
                px_time.setText(hour+":"+alarm_information.this.minute);
            }
            px_date.setText(month+"/"+day+"/"+year);
            px_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(alarm_information.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            alarm_information.this.year=year;
                            alarm_information.this.month=month;
                            day=dayOfMonth;
                            px_date.setText(month+"/"+day+"/"+year);
                        }
                    },year,month,day).show();
                }
            });

            px_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(alarm_information.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            hour = hourOfDay;
                            alarm_information.this.minute=minute;
                            if (alarm_information.this.minute < 10){
                                px_time.setText(hour+":"+"0"+alarm_information.this.minute);
                            }else {
                                px_time.setText(hour+":"+alarm_information.this.minute);
                            }
                        }
                    }, hour, minute, true).show();
                }
            });


        }

        update_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note_string= note.getText().toString();
                Calendar c=Calendar.getInstance();
                c.set(Calendar.YEAR,year);
                c.set(Calendar.MONTH,month);
                c.set(Calendar.DAY_OF_MONTH,day);
                c.set(Calendar.HOUR_OF_DAY,hour);
                c.set(Calendar.MINUTE,minute);
                c.set(Calendar.SECOND,0);
                Intent intent=new Intent(alarm_information.this,RingReceived.class);
                intent.setAction("cc.shuozi.uidesign.Ring");
                PendingIntent pendingIntent= PendingIntent.getBroadcast(alarm_information.this,time_id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                } else if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference updateref = db.collection("px").document(documentid);
                updateref
                        .update("Event note",note_string)
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
                        .update("Event year",year)
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
                        .update("Event month",month)
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
                        .update("Event day",day)
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
                        .update("Event hour",hour)
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
                        .update("Event minute",minute)
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
        add_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note_string= note.getText().toString();
                Calendar c=Calendar.getInstance();
                c.set(Calendar.YEAR,year);
                c.set(Calendar.MONTH,month);
                c.set(Calendar.DAY_OF_MONTH,day);
                c.set(Calendar.HOUR_OF_DAY,hour);
                c.set(Calendar.MINUTE,minute);
                c.set(Calendar.SECOND,0);
                Intent intent=new Intent(alarm_information.this,RingReceived.class);
                intent.setAction("cc.shuozi.uidesign.Ring");
                final int time_id = (int) System.currentTimeMillis();
                PendingIntent pendingIntent= PendingIntent.getBroadcast(alarm_information.this,time_id,intent,0);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                } else if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                }


                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> px_event = new HashMap<>();
                px_event.put("Event note", note_string);
                px_event.put("Event year",year);
                px_event.put("Event month", month);
                px_event.put("Event day", day);
                px_event.put("Event hour", hour);
                px_event.put("Event minute", minute);
                px_event.put("uid", uid);
                px_event.put("Time id", time_id);
                db.collection("px")
                        .add(px_event)
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
                finish();
            }
        });

    }
}