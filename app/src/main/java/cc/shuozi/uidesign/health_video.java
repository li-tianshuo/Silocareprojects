package cc.shuozi.uidesign;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.ArrayList;

public class health_video extends AppCompatActivity {
    public void listall(final storagecallback storagecallback)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://siloscare-and-design.appspot.com");
        StorageReference listRef = storage.getReference().child("heath_video");
        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them

                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                        }
                        storagecallback.onCallback(listResult);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_video);


        ListView health_video_list=findViewById(R.id.health_video_list);

        listall(new storagecallback() {
            @Override
            public void onCallback(ListResult listResult) {
                Object[] uri=listResult.getItems().toArray();
                String[] name=new String[uri.length];
                for (int i=0;i<uri.length;i++)
                {
                    name[i]=uri[i].toString().substring(50,uri[i].toString().length()-4);
                }
                ArrayAdapter<String> adapter =new ArrayAdapter<String>(health_video.this, android.R.layout.simple_list_item_1,name);
                health_video_list.setAdapter(adapter);
                health_video_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(health_video.this,watch_video.class);
                        intent.putExtra("uri",uri[(int) id].toString());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCallbackUrl(Uri url) {

            }
        });




    }
}
