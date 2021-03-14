package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class exportdata extends AppCompatActivity {
    private int start_year;
    private int start_month;
    private int start_day;
    private int end_year;
    private int end_month;
    private int end_day;
    private long timestampstart;
    private long timestampend;
    private Calendar begin;
    private Calendar end;
    private Calendar temp;
    private String diet;
    private String goal;
    private String symptoms;
    private String mentalactivity;
    private String physicalactivity;
    private String px;
    private String user_information;
    private FirebaseAuth mAuth;
    private int i;

    private static final int PERMISSION_REQUEST_CODE = 200;
    private void getsymptoms(final callback oncallbackString)
    {
        symptoms="Symptoms Record:"+"\r\n";
        i=0;
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("goal")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                if ("Symptom".equals(doc.getString("type"))) {
                                    temp = Calendar.getInstance();
                                    temp.set(Integer.parseInt(doc.get("year").toString()), Integer.parseInt(doc.get("month").toString()), Integer.parseInt(doc.get("day").toString()));
                                    if (!temp.after(begin) && !temp.before(end)) {
                                        symptoms=symptoms+String.valueOf(String.valueOf(doc.get("month"))+"/"+String.valueOf(doc.get("day"))+"/"+doc.get("year"))
                                                +" "+doc.get("hour")+":"+doc.get("minute")
                                                +"\r\n"+"Note:"+"\r\n";
                                        i++;
                                        for (int b = 0; b >= 0; b++) {
                                            if (doc.getString("Description " + b) == null) {
                                                Log.e("success", "break");
                                                if (i==0)
                                                {
                                                    symptoms=symptoms+"None";
                                                }
                                                break;
                                            } else {
                                                symptoms=symptoms+"Note title:"+doc.getString("Description " + b)+
                                                        "\r\n"+"Note:" + String.valueOf(doc.get("Note " + b)+"\r\n");
                                            }
                                        }

                                    }
                                }
                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            if (i==0)
                            {
                                symptoms=symptoms+"None";
                            }
                            symptoms=symptoms+"\r\n";
                            oncallbackString.onCallback(symptoms);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void getgoal(final callback oncallbackString)
    {
        goal="Goal Record:"+"\r\n";
        i=0;
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("goal")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                if ("Goal".equals(doc.getString("type"))) {
                                    temp = Calendar.getInstance();
                                    temp.set(Integer.parseInt(doc.get("year").toString()), Integer.parseInt(doc.get("month").toString()), Integer.parseInt(doc.get("day").toString()));
                                    if (!temp.after(begin) && !temp.before(end)) {
                                        goal=goal+String.valueOf(String.valueOf(doc.get("month"))+"/"+String.valueOf(doc.get("day"))+"/"+doc.get("year"))
                                                +" "+doc.get("hour")+":"+doc.get("minute")
                                                +"\r\n"+"Note:"+"\r\n";
                                        i++;
                                        for (int b = 0; b >= 0; b++) {
                                            if (doc.getString("Description " + b) == null) {
                                                Log.e("success", "break");
                                                if (i==0)
                                                {
                                                    goal=goal+"None";
                                                }
                                                break;
                                            } else {
                                                goal=goal+"Note title:"+doc.getString("Description " + b)+
                                                        "\r\n"+"Note:" + String.valueOf(doc.get("Note " + b)+"\r\n");
                                            }
                                        }

                                    }
                                }
                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            if (i==0)
                            {
                                goal=goal+"None";
                            }
                            goal=goal+"\r\n";
                            oncallbackString.onCallback(goal);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void getpx(final callback oncallbackString)
    {
        i=0;
        px="Prescription Record:"+"\r\n";
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("px")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                temp=Calendar.getInstance();
                                temp.set(Integer.parseInt(doc.get("Event year").toString()), Integer.parseInt(doc.get("Event month").toString()), Integer.parseInt( doc.get("Event day").toString()));

                                if (!temp.after(begin) && !temp.before(end)) {

                                    px=px+"Prescription:"+"Time:"+String.valueOf(String.valueOf(doc.get("Event month"))+"/"+String.valueOf(doc.get("Event day"))+"/"+doc.get("Event year"))
                                            +" "+doc.get("Event hour")+":"+doc.get("Event minute") +"\r\n"+doc.getString("Event note")+"\r\n";

                                    i++;
                                }

                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            if (i==0)
                            {
                                px=px+"None";
                            }
                            px=px+"\r\n";
                            oncallbackString.onCallback(px);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void getpa(final callback oncallbackString)
    {
        i=0;
        physicalactivity="Physical Ativity Record:"+"\r\n";
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("physicalactivity")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                temp=Calendar.getInstance();
                                temp.set(Integer.parseInt(doc.get("Event start year").toString()), Integer.parseInt(doc.get("Event start month").toString()), Integer.parseInt( doc.get("Event start day").toString()));

                                if (!temp.after(begin) && !temp.before(end)) {

                                    physicalactivity=physicalactivity+"Menal Activity:"+doc.getString("Event")+"\r\n"+"Start Time:"+String.valueOf(String.valueOf(doc.get("Event start month"))+"/"+String.valueOf(doc.get("Event start day"))+"/"+doc.get("Event start year"))
                                            +" "+doc.get("Event start hour")+":"+doc.get("Event start minute")
                                            +"-"+"End Date:"+String.valueOf(String.valueOf(doc.get("Event end month"))+"/"+String.valueOf(doc.get("Event end day"))+"/"+doc.get("Event end year"))
                                            +" "+doc.get("Event start hour")+":"+doc.get("Event start minute")
                                            +"\r\n";

                                    i++;
                                }

                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            if (i==0)
                            {
                                physicalactivity=physicalactivity+"None";
                            }
                            physicalactivity=physicalactivity+"\r\n";
                            oncallbackString.onCallback(physicalactivity);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void getma(final callback oncallbackString)
    {
        i=0;
        mentalactivity="Mentala Ativity Record:"+"\r\n";
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("mentalactivity")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                temp=Calendar.getInstance();
                                temp.set(Integer.parseInt(doc.get("Event start year").toString()), Integer.parseInt(doc.get("Event start month").toString()), Integer.parseInt( doc.get("Event start day").toString()));

                                if (!temp.after(begin) && !temp.before(end)) {

                                    mentalactivity=mentalactivity+"Menal Activity:"+doc.getString("Event")+"\r\n"+"Start Time:"+String.valueOf(String.valueOf(doc.get("Event start month"))+"/"+String.valueOf(doc.get("Event start day"))+"/"+doc.get("Event start year"))
                                            +" "+doc.get("Event start hour")+":"+doc.get("Event start minute")
                                            +"-"+"End Date:"+String.valueOf(String.valueOf(doc.get("Event end month"))+"/"+String.valueOf(doc.get("Event end day"))+"/"+doc.get("Event end year"))
                                            +" "+doc.get("Event start hour")+":"+doc.get("Event start minute")
                                            +"\r\n";

                                    i++;
                                }

                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            if (i==0)
                            {
                                mentalactivity=mentalactivity+"None";
                            }
                            mentalactivity=mentalactivity+"\r\n";
                            oncallbackString.onCallback(mentalactivity);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }


    public void getdiet(final callback oncallbackString)
    {
        i=0;
        diet="Diet record:" +"\r\n";;
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("diet")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                temp=Calendar.getInstance();
                                temp.set(Integer.parseInt(doc.get("Diet year").toString()), Integer.parseInt(doc.get("Diet month").toString()), Integer.parseInt( doc.get("Diet day").toString()));
                                if (!temp.after(begin) && !temp.before(end)) {
                                    diet=diet+String.valueOf(String.valueOf(doc.get("Diet month"))+"/"+String.valueOf(doc.get("Diet day"))+"/"+doc.get("Diet year"))
                                    +"\r\n"+doc.getString("Diet type")+"\r\n"+"Food:"+"\r\n";
                                    for (int b = 0;b>=0;b++) {
                                        if (doc.getString("Food name " + b)==null) {
                                            Log.e("success","break");
                                            if (b==0)
                                            {
                                                diet=diet+"None"+"\r\n";
                                            }
                                            break;
                                        }else{
                                            diet=diet+"Food name:"+doc.getString("Food name " + String.valueOf(b))+
                                                    "\r\n"+"Food Weight:" + String.valueOf(doc.get("Food Weight " + String.valueOf(b))+"\r\n");

                                        }
                                    }
                                    i++;
                                }

                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            if (i==0)
                            {
                                diet=diet+"None";
                            }
                            diet=diet+"\r\n";
                            oncallbackString.onCallback(diet);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }

    private void get_user_information(final callback oncallback)
    {
        mAuth = FirebaseAuth.getInstance();
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
                                user_information="Name:"+doc.getString("First Name")+" "+doc.getString("Last Name")+"\r\n"
                                        +"Condition 1:"+doc.getString("Condition 1")+"\r\n" +"Condition 2:"+doc.getString("Condition 2")+"\r\n"
                                        +"Condition 3:"+doc.getString("Condition 3")+"\r\n"+ "Condition 4:"+doc.getString("Condition 4")+"\r\n";
                            }
                            user_information=user_information+"\r\n";
                            oncallback.onCallback(user_information);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportdata);
        TextView export_start_time=findViewById(R.id.export_start_time);
        final TextView export_end_time=findViewById(R.id.export_end_time);
        final EditText  export_start_time_selection=findViewById(R.id.export_start_time_selection);
        final EditText  export_end_time_selection=findViewById(R.id.export_end_time_selection);

        Button export_data_button=findViewById(R.id.export_data_button);

        final Calendar calendar=Calendar.getInstance();
        start_year= calendar.get(Calendar.YEAR);
        start_month=calendar.get(Calendar.MONTH);
        start_day=calendar.get(Calendar.DAY_OF_MONTH);
        end_year= calendar.get(Calendar.YEAR);
        end_month=calendar.get(Calendar.MONTH);
        end_day=calendar.get(Calendar.DAY_OF_MONTH);
        if (export_start_time_selection.getText()==null)
        {
            export_end_time.setVisibility(View.INVISIBLE);
            export_end_time_selection.setVisibility(View.INVISIBLE);
        }else{
            export_end_time.setVisibility(View.GONE);
            export_end_time_selection.setVisibility(View.GONE);
        }

        export_start_time_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(exportdata.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        exportdata.this.start_year=year;
                        exportdata.this.start_month=month;
                        start_day=dayOfMonth;
                        export_start_time_selection.setText(start_month+1+"/"+start_day+"/"+start_year);
                        export_end_time.setVisibility(View.VISIBLE);
                        export_end_time_selection.setVisibility(View.VISIBLE);
                    }
                },start_year,start_month,start_day).show();
            }
        });
        export_end_time_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(exportdata.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        exportdata.this.end_year=year;
                        exportdata.this.end_month=month;
                        end_day=dayOfMonth;
                        export_end_time_selection.setText(end_month+1+"/"+end_day+"/"+end_year);

                    }
                },end_year,end_month,end_day).show();
            }
        });



        export_data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkdate();


            }
        });
    }

    private void checkdate() {
        begin=Calendar.getInstance();
        begin.set(start_year,start_month,start_day,0,0);
        timestampstart=begin.getTimeInMillis();
        end=Calendar.getInstance();
        begin.set(end_year,end_month,end_day,23,59);
        timestampend=end.getTimeInMillis();


        if (timestampstart>timestampend)
        {
            Toast.makeText(this, "Something wrong with this part...", Toast.LENGTH_SHORT).show();
        }else
        {
            getdata();

        }

    }

    private void getdata() {
        get_user_information(new callback() {
            @Override
            public void onCallback(String string) {
                user_information=string;
                getdiet(new callback() {
                    @Override
                    public void onCallback(String string) {
                        diet=string;
                        getma(new callback() {
                            @Override
                            public void onCallback(String string) {
                                mentalactivity=string;
                                getpa(new callback() {
                                    @Override
                                    public void onCallback(String string) {
                                        physicalactivity=string;
                                        getpx(new callback() {
                                            @Override
                                            public void onCallback(String string) {
                                                px=string;
                                                getgoal(new callback() {
                                                    @Override
                                                    public void onCallback(String string) {
                                                        goal=string;
                                                        getsymptoms(new callback() {
                                                            @Override
                                                            public void onCallback(String string) {
                                                                symptoms=string;
                                                                createpdf();
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


        //shareFile(this,"/storage/emulated/0/report.pdf");
    }

    public void shareFile(Context context, String filePath) throws ActivityNotFoundException {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM, filePath);
        sendIntent.setType("application/pdf");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    private void createpdf() {// create a new document
        PdfDocument document = new PdfDocument();

        // create a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();

        String total= user_information+goal+symptoms+diet+px+mentalactivity+physicalactivity;
        String[] lines = total.split("\r\n");
        boolean isfull=false;
        PdfDocument.Page page=document.startPage(pageInfo);
        int a=0;
        Paint title=new Paint();
        int i=0;
        while(a<lines.length)
        {

            Canvas canvas = page.getCanvas();
            if (!isfull){
                canvas.drawText(lines[a], 100, 80+20*i, title);
                a++;
                i++;
                if (80+i*20>=820)
                {
                    i=0;
                    isfull=true;
                }
            }else{
                document.finishPage(page);
                page=document.startPage(pageInfo);
                isfull=false;
            }

        }
        document.finishPage(page);
        // start a page


        // draw something on the page


        // finish the page


        // add more pages

        // write the document content
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        File file = new File(Environment.getExternalStorageDirectory(),"report.pdf");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            document.writeTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // close the document
        document.close();
        shareFile(this,"/storage/emulated/0/report.pdf");
    }


    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
        }

        private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }


}