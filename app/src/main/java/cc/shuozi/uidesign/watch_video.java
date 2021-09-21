package cc.shuozi.uidesign;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class watch_video extends AppCompatActivity {
    String uri;
    public void getUrL(storagecallback storagecallback,String uri_string)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://siloscare-and-design.appspot.com");
        StorageReference pathReference = storage.getReference().child("heath_video/"+uri_string);
        Log.e("!!!!!",pathReference.toString());
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                storagecallback.onCallbackUrl(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_video);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent=getIntent();
        String uri=intent.getStringExtra("uri");
        Log.e("!?",uri);
        uri=uri.substring(50);
        VideoView watch_video_video=findViewById(R.id.watch_video_video);

        getUrL(new storagecallback() {
            @Override
            public void onCallback(ListResult listResult) {

            }

            @Override
            public void onCallbackUrl(Uri url) {
                watch_video_video.setVideoURI(url);
                MediaController mediaController = new MediaController(watch_video.this);
                mediaController.setAnchorView(watch_video_video);
                mediaController.setMediaPlayer(watch_video_video);
                watch_video_video.setMediaController(mediaController);
                watch_video_video.start();
            }
        },uri);

        /*
        try {
            File localFile = File.createTempFile("test", "mp4");

            pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        } catch ( IOException e) {
            e.printStackTrace();
        }
        */
    }
}
