package cc.shuozi.uidesign;

import android.net.Uri;

import com.google.firebase.storage.ListResult;

public interface storagecallback {
    void onCallback(ListResult listResult);
    void onCallbackUrl(Uri url);
}
