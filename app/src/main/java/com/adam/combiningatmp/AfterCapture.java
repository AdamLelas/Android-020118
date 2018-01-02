package com.adam.combiningatmp;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class AfterCapture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_capture);

        Bitmap bitmap = getIntent().getExtras().getParcelable("name");
        ImageView view = (ImageView) findViewById(R.id.ivPicture);
        view.setImageBitmap(bitmap);
    }
}
