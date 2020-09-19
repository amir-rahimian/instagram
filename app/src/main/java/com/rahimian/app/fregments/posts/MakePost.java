package com.rahimian.app.fregments.posts;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rahimian.app.R;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MakePost extends AppCompatActivity {

    private CropImageView img_post;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);

        //ui
        img_post = findViewById(R.id.post_crop);

         uri = (Uri) getIntent().getExtras().get("uri");

        img_post.setImageUriAsync(uri);
    }
}