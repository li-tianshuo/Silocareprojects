package cc.shuozi.uidesign;

import com.google.firebase.database.core.view.Event;

import java.util.ArrayList;
import java.util.List;

public interface callback {
    void onCallback(String string);
    void onCallbacknumber(int i);
    void onCallbackList(ArrayList<String> list);
    void onCallbackListstring(String[][] data);
    void onCallbacklistdiet(String[][] data,food[][] foods);
}
