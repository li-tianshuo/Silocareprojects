package cc.shuozi.uidesign;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;

public class my_adapter_activity extends BaseAdapter {
    private Context context;
    private String[][] data;
    private String documentid;
    private FirebaseAuth mAuth;
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

    public my_adapter_activity(Context context, String[][] data)
    {
        this.context= context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position][0];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        Arrays.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return Integer.parseInt(o2[0])-Integer.parseInt(o1[0]);
            }
        });

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_activity, parent, false);
        final TextView start_time = (TextView) convertView.findViewById(R.id.duration);
        final TextView end_time = (TextView) convertView.findViewById(R.id.list_start_time);
        final TextView event = (TextView) convertView.findViewById(R.id.activity_type);
        Button update=(Button)convertView.findViewById(R.id.update_activity);

        final Calendar start=Calendar.getInstance();
        Calendar end=Calendar.getInstance();
        event.setText(data[position][1]);
        event.setTag(data[position][0]);
        start_year=Integer.parseInt(data[position][2]);
        start_month=Integer.parseInt(data[position][3]);
        start_day=Integer.parseInt(data[position][4]);
        start_hour=Integer.parseInt(data[position][5]);
        start_minute=Integer.parseInt(data[position][6]);
        end_year=Integer.parseInt(data[position][7]);
        end_month=Integer.parseInt(data[position][8]);
        end_day=Integer.parseInt(data[position][9]);
        end_hour=Integer.parseInt(data[position][10]);
        end_minute=Integer.parseInt(data[position][11]);
        final int type=Integer.parseInt(data[position][12]);
        start.set(Calendar.YEAR,start_year);
        start.set(Calendar.MONTH,start_month);
        start.set(Calendar.DAY_OF_MONTH,start_day);
        start.set(Calendar.HOUR_OF_DAY,start_hour);
        start.set(Calendar.MINUTE,start_minute);
        end.set(Calendar.YEAR,start_year);
        end.set(Calendar.MONTH,start_month);
        end.set(Calendar.DAY_OF_MONTH,start_day);
        end.set(Calendar.HOUR_OF_DAY,start_hour);
        end.set(Calendar.MINUTE,start_minute);


        if (start_minute < 10){
            start_time.setText(start_month+"/"+start_day+"/"+start_year+"   "+start_hour+":"+"0"+start_minute);
        }else {
            start_time.setText(start_month+"/"+start_day+"/"+start_year+"   "+start_hour+":"+start_minute);
        }

        if (end_minute < 10){
            end_time.setText(end_month+"/"+end_day+"/"+end_year+"   "+end_hour+":"+"0"+end_minute);
        }else {
            end_time.setText(end_month+"/"+end_day+"/"+end_year+"   "+end_hour+":"+end_minute);
        }


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(parent.getContext(), event_selection.class);
                intent.putExtra("status",type);
                intent.putExtra("time_id",Integer.parseInt(event.getTag().toString()));
                parent.getContext().startActivity(intent);
                ((Activity)context).finish();
            }
        });
        return convertView;
    }
}
