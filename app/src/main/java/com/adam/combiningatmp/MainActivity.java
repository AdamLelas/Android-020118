package com.adam.combiningatmp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    ImageView ivPic;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;

                case R.id.navigation_notifications:
                    SettingsFragment settingsFragment = new SettingsFragment();
                    android.app.FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.contentLayout,
                            settingsFragment,
                            settingsFragment.getTag()).commit();
                    return true;

                case R.id.navigation_camera:
                    findViewById(R.id.navigation_camera).setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                                dispatchTakenPictureIntent();
                            }
                            else{
                                if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                                    Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
                                }
                                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_RESULT);
                            }
                        }
                    });
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        ivPic = (ImageView)findViewById(R.id.ivPic);
    }



    /*
    * CODE FOR CAMERA STARTS HERE
    */
    private final int CAMERA_RESULT = 101;

    private void dispatchTakenPictureIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, CAMERA_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_RESULT){
                Bundle extras = data.getExtras();
                Bitmap imageData = (Bitmap) extras.get("data");

                Intent afterCapture = new Intent(this, AfterCapture.class);
                afterCapture.putExtra("name", imageData);
                startActivity(afterCapture);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_RESULT){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ){
                dispatchTakenPictureIntent();
            }
            else{
                Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
            }
        }
        else{
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }

    /*
    * CODE FOR CAMERA ENDS HERE
    */

}
