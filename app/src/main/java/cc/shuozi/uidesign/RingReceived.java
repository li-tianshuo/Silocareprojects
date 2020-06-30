package cc.shuozi.uidesign;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RingReceived extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("whatever",intent.getAction());
        if("cc.shuozi.uidesign.Ring".equals(intent.getAction())){
            Log.i("test","success");
            Intent intent1=new Intent(context,RingActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
