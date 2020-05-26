package cc.shuozi.uidesign;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class myvideo extends VideoView {
    public myvideo (Context context)
    {
        super(context);
    }
    public myvideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public myvideo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDefaultSize(0,widthMeasureSpec);
        int height = getDefaultSize(0,heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
