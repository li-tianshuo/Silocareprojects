package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Setting extends AppCompatActivity {
    private static final int TAKE_PHOTO_PERMISSION_REQUEST_CODE = 0;
    private static final int WRITE_SDCARD_PERMISSION_REQUEST_CODE = 1;
    private static final int TAKE_PHOTO_REQUEST_CODE = 3;
    private static final int CHOICE_FROM_ALBUM_REQUEST_CODE = 4;
    private static final int CROP_PHOTO_REQUEST_CODE = 5;
    private Uri photoUri = null;
    private Uri photoOutputUri = null;
    private Button logout;
    private ListView information_setting;
    private ListView app_setting;
    private String datas1[]={"Modify avator", "Set Condition information", "Set Primary Doctor Information"};
    private String datas2[]={"Support Center", "Contact us", "About us"};

    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        information_setting=findViewById(R.id.setting_information);
        app_setting=findViewById(R.id.setting_app);
        adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas1);
        adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas2);

        //Uri photoOutputUri=Uri.parse("content:///data/data/cc.shuozi.uidesign/avator.jpg");
        if(ContextCompat.checkSelfPermission(Setting.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Setting.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_SDCARD_PERMISSION_REQUEST_CODE);
        }
    



        information_setting.setAdapter(adapter1);
        app_setting.setAdapter(adapter2);
        information_setting.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0 :
                        showPopMenu(view);
                        //startActivity(new Intent(Setting.this, account_information.class));
                        break;
                    case 1 :
                        startActivity(new Intent(Setting.this, pre_information.class));
                        break;
                    case 2 :
                        Intent intent=new Intent(Setting.this, doctor_information.class);
                        intent.putExtra("status","add_major");
                        startActivity(intent);
                        break;
                    default :

                }
            }
        });
        logout=findViewById(R.id.log_out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Setting.this, MainActivity.class));
            }
        });
    }
    private void showPopMenu(View v) {

        PopupMenu popup = new PopupMenu(Setting.this, v);
        popup.getMenuInflater().inflate(R.menu.avator_list, popup.getMenu());
        if (Build.VERSION.SDK_INT >= 30)
        {
            popup.getMenu().getItem(0).setVisible(false);
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.s_from_c:
                        if(ContextCompat.checkSelfPermission(Setting.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Setting.this,
                                    new String[]{Manifest.permission.CAMERA,}, TAKE_PHOTO_PERMISSION_REQUEST_CODE);
                        } else {
                            startCamera();
                        }
                        break;

                    case R.id.s_from_g:
                        choiceFromAlbum();
                        break;

                }

                return false;

            }
        });
        popup.show();


    }

    private void choiceFromAlbum() {
        Intent choiceFromAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        choiceFromAlbumIntent.setType("image/*");
        startActivityForResult(choiceFromAlbumIntent, CHOICE_FROM_ALBUM_REQUEST_CODE);
    }

    private void startCamera() {
        File file = new File(getExternalCacheDir(),"avator.jpg");
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(Build.VERSION.SDK_INT >= 24) {
            photoUri = FileProvider.getUriForFile(this, "cc.shuozi.uidesign.fileprovider", file);
        } else {
            photoUri = Uri.fromFile(file);
        }

            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);

    }
    private void cropPhoto(Uri inputUri) {

        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, inputUri);
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION| Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropPhotoIntent.putExtra("return-data", true);
        startActivityForResult(cropPhotoIntent, CROP_PHOTO_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case TAKE_PHOTO_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case WRITE_SDCARD_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "SD card write permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case TAKE_PHOTO_REQUEST_CODE:
                    cropPhoto(photoUri);
                    break;

                case CHOICE_FROM_ALBUM_REQUEST_CODE:

                    cropPhoto(data.getData());

                    break;

                case CROP_PHOTO_REQUEST_CODE:
                    File file = new File("/data/data/cc.shuozi.uidesign","avator.jpg");
                    InputStream is = null;
                    try {
                        is = getContentResolver().openInputStream(data.getData());
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        is.close();
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

}