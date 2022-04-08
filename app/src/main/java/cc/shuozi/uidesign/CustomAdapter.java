package cc.shuozi.uidesign;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>  {
    private String[] localDataSet=new String[]{"Main Menu", "Information", "Implementation","Decision Making","Symptoms and Goals","Data and Education","Setting"};

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder  {
        private final TextView textView;
        private final LinearLayout bg;
        private final ImageView image;
        private final int width;
        private final int height;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            bg=(LinearLayout) view.findViewById(R.id.ry_list_bg);
            image=(ImageView) view.findViewById(R.id.ry_list_icon);
            textView = (TextView) view.findViewById(R.id.ry_list_tx);
            WindowManager wm = (WindowManager)  view.getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
            ViewGroup.LayoutParams params=bg.getLayoutParams();
            params.width=width+600;
            params.height=(height-150)/7;
            bg.setLayoutParams(params);
        }

        public TextView getTextView() {
            return textView;
        }
        public LinearLayout getLinearLayout(){
            return bg;
        }
        public ImageView getImageView(){
            return image;
        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * by RecyclerView.
     */
    public CustomAdapter() {
        //localDataSet = dataSet;
    }
    public CustomAdapter(String[] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_list, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet[position]);

        switch (position) {
            case 0:
                viewHolder.getLinearLayout().setBackgroundColor(Color.parseColor("#F76E11"));
                //viewHolder.getImageView().setImageDrawable();
                break;
            case 1:
                viewHolder.getLinearLayout().setBackgroundColor(Color.parseColor("#FF9F45"));
                //viewHolder.getImageView().setImageDrawable();
                break;
            case 2:
                viewHolder.getLinearLayout().setBackgroundColor(Color.parseColor("#FFBC80"));
                //viewHolder.getImageView().setImageDrawable();
                break;
            case 3:
                viewHolder.getLinearLayout().setBackgroundColor(Color.parseColor("#FC4F4F"));
                //viewHolder.getImageView().setImageDrawable();
                break;
            case 4:
                viewHolder.getLinearLayout().setBackgroundColor(Color.parseColor("#D3ECA7"));
                //viewHolder.getImageView().setImageDrawable();
                break;
            case 5:
                viewHolder.getLinearLayout().setBackgroundColor(Color.parseColor("#A1B57D"));
                //viewHolder.getImageView().setImageDrawable();
                break;
            case 6:
                viewHolder.getLinearLayout().setBackgroundColor(Color.parseColor("#B33030"));
                //viewHolder.getImageView().setImageDrawable();
                break;

        }
        setAnimation(viewHolder.itemView, position);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        Context context = viewToAnimate.getContext();
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);

        animation.setDuration(position * 200 + 200);
        viewToAnimate.startAnimation(animation);

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}