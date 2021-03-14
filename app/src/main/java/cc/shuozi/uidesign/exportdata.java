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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static final int PERMISSION_REQUEST_CODE = 200;
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
        final Calendar start=Calendar.getInstance();
        start.set(start_year,start_month,start_day,0,0);
        timestampstart=start.getTimeInMillis();
        final Calendar end=Calendar.getInstance();
        start.set(end_year,end_month,end_day,23,59);
        timestampend=end.getTimeInMillis();
        Log.e(String.valueOf(timestampstart),"timestamp start");
        Log.e(String.valueOf(timestampend),"timestamp end");

        if (timestampstart>timestampend)
        {
            Toast.makeText(this, "Something wrong with this part...", Toast.LENGTH_SHORT).show();
        }else
        {
            getdata();

        }

    }

    private void getdata() {


        createpdf();
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

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        // draw something on the page
        Paint title=new Paint();
        Canvas canvas = page.getCanvas();

        canvas.drawText("This is a test page", 209, 100, title);
        canvas.drawText("Test", 209, 80, title);
        // finish the page
        document.finishPage(page);

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