package cc.shuozi.uidesign;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class goal_adapter extends RecyclerView.Adapter<goal_adapter.MyViewHolder> implements IItemHelper{
    private Context context;
    private final LayoutInflater mInflater;
    private ArrayList<goal_like_event> mDatas=new ArrayList<>();
    private RecyclerView mRecyclerView;

    public goal_adapter(Context context,ArrayList<goal_like_event> mDates,RecyclerView recyclerView) {
        this.context = context;
        this.mDatas = (ArrayList<goal_like_event>) mDates.clone();
        mInflater = LayoutInflater.from(context);
        this.mRecyclerView = recyclerView;
        Collections.sort(mDatas, new Comparator<goal_like_event>() {
            @Override
            public int compare(goal_like_event o1, goal_like_event o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });

    }

    public void addDatas(List<goal_like_event> datas){
        mDatas.addAll(datas);
    }

    @NonNull
    @Override
    public goal_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.goals_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final goal_adapter.MyViewHolder holder, final int position) {

        TextView title=holder.getView(R.id.goal_title);
        title.setText(mDatas.get(position).getName());
        RelativeLayout goal_background=holder.getView(R.id.goal_background);
        int num=position%4;
        switch (num)
        {
            case 0:
                goal_background.setBackgroundColor(Color.parseColor("#aa2e25"));
                title.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 1:
                goal_background.setBackgroundColor(Color.parseColor("#e91e63"));
                title.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 2:
                goal_background.setBackgroundColor(Color.parseColor("#9c27b0"));
                title.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 3:
                goal_background.setBackgroundColor(Color.parseColor("#673ab7"));
                title.setTextColor(Color.parseColor("#ffffff"));
                break;
        }

        final Button view=holder.getView(R.id.goals_view);
        Button update=holder.getView(R.id.goals_update);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onStartDragListener.startDrag(holder);
                return true;
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas.get(position).getType().equals("Symptom")) {
                    Intent intent = new Intent(view.getContext(), add_goal.class);
                    intent.putExtra("status", 2);
                    intent.putExtra("documentid", mDatas.get(position).getDocumentid());
                    view.getContext().startActivity(intent);
                    ((Activity)context).finish();
                }else if (mDatas.get(position).getType().equals("Goal"))
                {
                    Intent intent = new Intent(view.getContext(), add_goal.class);
                    intent.putExtra("status", 3);
                    intent.putExtra("documentid", mDatas.get(position).getDocumentid());
                    view.getContext().startActivity(intent);
                    ((Activity)context).finish();
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas.get(position).get_description_array().isEmpty())
                {
                    Toast.makeText(v.getContext(),"Nothing here",Toast.LENGTH_LONG).show();
                }else {

                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    View checkView = layoutInflater.inflate(R.layout.food_list, null);
                    ListView listView = checkView.findViewById(R.id.food_listview);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(checkView.getContext(), android.R.layout.simple_list_item_1, mDatas.get(position).get_description_array());
                    listView.setAdapter(adapter);
                    View testcheckView = layoutInflater.inflate(R.layout.symptoms_goals_fragment, null);
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

            }
        });


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void itemMoved(int oldPosition, int newPosition) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference updateref = db.collection("goal").document(mDatas.get(oldPosition).getDocumentid());
        updateref
                .update("priority",newPosition)
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
        updateref = db.collection("goal").document(mDatas.get(newPosition).getDocumentid());
        updateref
                .update("priority",oldPosition)
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

        mDatas.get(newPosition).setPriority(oldPosition);
        mDatas.get(oldPosition).setPriority(newPosition);

        Collections.sort(mDatas, new Comparator<goal_like_event>() {
            @Override
            public int compare(goal_like_event o1, goal_like_event o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });

        notifyItemMoved(oldPosition,newPosition);
        notifyItemChanged(oldPosition);
        notifyItemChanged(newPosition);
    }

    @Override
    public void itemDismiss(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Button goal_update;
        Button goal_view;
        private SparseArray<View> mViews;

        public MyViewHolder(View itemView) {
            super(itemView);
            mViews=new SparseArray();
        }
        public <T extends View> T getView(@IdRes int id){
            View view= mViews.get(id);

            if(view==null){
                view =itemView.findViewById(id);
                mViews.put(id,view);
            }

            return (T) view;
        }


         /*
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.goal_title);
            goal_update=itemView.findViewById(R.id.goals_update);
            goal_view=itemView.findViewById(R.id.goals_view);
        }



          */
    }
    private OnStartDragListener onStartDragListener;

    public interface OnStartDragListener{
        void startDrag(RecyclerView.ViewHolder holder);
    }

    public void setOnStartDragListener(OnStartDragListener onStartDragListener){
        this.onStartDragListener = onStartDragListener;
    }
}
