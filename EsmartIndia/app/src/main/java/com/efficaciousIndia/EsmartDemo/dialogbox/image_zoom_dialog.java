package com.efficaciousIndia.EsmartDemo.dialogbox;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.common.ConnectionDetector;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;

public class image_zoom_dialog extends Activity {
    ImageView imageView;
    ImageView callimage, messageimage, videcallimage;
    String Path;
    ConnectionDetector cd;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.zoom_image);
        cd = new ConnectionDetector(getApplicationContext());
        try {
            Intent intent = getIntent();
            if (null != intent) {
                Path = intent.getStringExtra("path");
            }
        } catch (Exception ex) {

        }
        imageView = (ImageView) findViewById(R.id.imageView6);
        callimage = (ImageView) findViewById(R.id.imageView12);
        messageimage = (ImageView) findViewById(R.id.imageView16);
        videcallimage = (ImageView) findViewById(R.id.imageView18);
        callimage.setVisibility(View.GONE);
        messageimage.setVisibility(View.GONE);
        videcallimage.setVisibility(View.GONE);
        imageView.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;
        imageView.getLayoutParams().width= ViewGroup.LayoutParams.WRAP_CONTENT;
        try {
            String url = RetrofitInstance.Image_URL + Path;
            Glide.with(image_zoom_dialog.this)
                    .load(url)

                    .error(R.mipmap.profile)
                    .into(imageView);
        } catch (Exception ex) {

        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = RetrofitInstance.Image_URL + Path;
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } catch (Exception ex) {

                }

            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}