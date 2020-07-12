package cc.shuozi.uidesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class goal_adapter extends RecyclerView.Adapter<goal_adapter.MyViewHolder> implements IItemHelper{
    private Context context;
    private final LayoutInflater mInflater;
    private List<goal_like_event> mDatas=new ArrayList<>();
    private RecyclerView mRecyclerView;

    public goal_adapter(Context context,List<goal_like_event> mDates,RecyclerView recyclerView) {
        this.context = context;
        this.mDatas = mDates;
        mInflater = LayoutInflater.from(context);
        this.mRecyclerView = recyclerView;
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
    public void onBindViewHolder(@NonNull goal_adapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void itemMoved(int oldPosition, int newPosition) {

    }

    @Override
    public void itemDismiss(int position) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Button goal_update;
        Button goal_view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.goal_title);
            goal_update=itemView.findViewById(R.id.goals_update);
            goal_view=itemView.findViewById(R.id.goals_view);
        }
    }
}
