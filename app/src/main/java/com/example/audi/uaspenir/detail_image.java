package com.example.audi.uaspenir;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class detail_image extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);
        supportPostponeEnterTransition();

        Bitmap bmp = null;

        Bundle extras = getIntent().getExtras();
        String imageTransitionName = extras.getString("transition_name");
        String nama_gambar = extras.getString("nama_gambar");
        String ekstensi_gambar = extras.getString("ekstensi_gambar");

        URL url = null;
        try {
            url = new URL("http://103.52.146.34/penir/penir13/IMAGE/"+nama_gambar+ekstensi_gambar);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageView image = (ImageView) findViewById(R.id.image);
        image.setTransitionName(imageTransitionName);
        image.setImageBitmap(bmp);

        supportStartPostponedEnterTransition();
    }
}
