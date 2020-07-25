package cc.shuozi.uidesign;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class my_adapter_diet extends BaseAdapter {
    private Context context;
    private food[][] food_data ;
    private String[][] information;

    public my_adapter_diet(Context context, food[][] fooddata, String[][] information) {
        this.context = context;
        this.food_data = fooddata;
        this.information = information;
    }

    @Override
    public int getCount() {
        return information.length;
    }

    @Override
    public Object getItem(int position) {
        return information[position][0];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_meal_item, parent, false);
        final TextView diet_time = (TextView) convertView.findViewById(R.id.diet_time);
        Button view = (Button) convertView.findViewById(R.id.view_diet);
        final Button update = (Button) convertView.findViewById(R.id.update_diet);

        if (information==null)
        {
            view.setVisibility(View.GONE);
            update.setVisibility(View.GONE);
            diet_time.setText("No data here");
        }else {
            diet_time.setTag(information[position][0]);
            diet_time.setText(information[position][2] + "/" + information[position][3] + "/" + information[position][1]);
            update.setTag(information[position][4]);

            final View finalConvertView = convertView;
            view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCreatefoodinformation(position);

                    }

                });


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(parent.getContext(), diet_information.class);
                        intent.putExtra("status", 1);
                        intent.putExtra("time_id", Integer.parseInt(diet_time.getTag().toString()));
                        parent.getContext().startActivity(intent);
                        ((Activity)context).finish();
                    }
                });

            }
             return convertView;
        }

    private void onCreatefoodinformation(int poistion) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View checkView = layoutInflater.inflate(R.layout.food_list, null);
        ListView listView = checkView.findViewById(R.id.food_listview);
        ArrayList<String> viewlist = new ArrayList<String>();

        for (int i=0;i>=0;i++)
        {
            if (food_data[poistion][i]!=null)
            {
                viewlist.add("Food Name:"+food_data[poistion][i].getName()+"\n"+"Food Weight:"+food_data[poistion][i].getWeight()+"g");
            }else{
                break;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(checkView.getContext(), android.R.layout.simple_list_item_1, viewlist);
        listView.setAdapter(adapter);
        View testcheckView = layoutInflater.inflate(R.layout.list_meal, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(checkView.getContext());
        alertDialogBuilder.setView(checkView);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }
    /*
             * information[][0]->time uid
             * information[][1]->year
             * information[][2]->month
             * information[][3]->day
             * information[][4]->type
             * */


    }

