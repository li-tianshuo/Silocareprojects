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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class alarm_information extends AppCompatActivity {

    private int minute;
    private int hour;
    private int year;
    private int month;
    private int day;
    private String uid;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = mAuth.getCurrentUser();
        uid=user.getUid();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_information);
        Button add_alarm=findViewById(R.id.add_alarm);
        Button update_alarm=findViewById(R.id.update_alarm);
        final TextView px_time=findViewById(R.id.px_time);
        final AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        final TextView px_date=findViewById(R.id.date_picker);

        final Calendar calendar=Calendar.getInstance();
        hour=calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);
        year= calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        if (alarm_information.this.minute < 10){
            px_time.setText(hour+":"+"0"+alarm_information.this.minute);
        }else {
            px_time.setText(hour+":"+alarm_information.this.minute);
        }

        int id = getIntent().getIntExtra("status", 1);
        if (id == 0) {
            add_alarm.setVisibility(View.GONE);
        }else if (id == 1) {
            update_alarm.setVisibility(View.GONE);
        }
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
                },year,month,day);
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


        add_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView note=findViewById(R.id.px_note);
                String note_string= (String) note.getText();
                Calendar c=Calendar.getInstance();
                c.set(calendar.YEAR,year);
                c.set(Calendar.MONTH,month);
                c.set(Calendar.DAY_OF_MONTH,day);
                c.set(Calendar.HOUR_OF_DAY,hour);
                c.set(Calendar.MINUTE,minute);
                c.set(Calendar.SECOND,0);
                Intent intent=new Intent(alarm_information.this,RingReceived.class);
                intent.setAction("cc.shuozi.uidesign.Ring");
                PendingIntent pendingIntent= PendingIntent.getBroadcast(alarm_information.this,0,intent,0);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                } else if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                }
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> px_event = new HashMap<>();
                px_event.put("Event note", note);
                px_event.put("Event year",year);
                px_event.put("Event month", month);
                px_event.put("Event day", day);
                px_event.put("Event hour", hour);
                px_event.put("Event minute", minute);
                px_event.put("uid", uid);
                db.collection("user")
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
            }
        });

    }
}