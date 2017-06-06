package com.example.audi.uaspenir;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockContentResolver;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class detail_image extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);
        supportPostponeEnterTransition();

        Bitmap bmp = null;

        Bundle extras = getIntent().getExtras();
        String imageTransitionName = extras.getString("transition_name");
        final String nama_gambar = extras.getString("nama_gambar");
        final String ekstensi_gambar = extras.getString("ekstensi_gambar");

        URL url = null;
        try {
            url = new URL("http://103.52.146.34/penir/penir13/IMAGE/" + nama_gambar + ekstensi_gambar);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        final ImageView image = (ImageView) findViewById(R.id.image);
        image.setTransitionName(imageTransitionName);
        image.setImageBitmap(bmp);

        Transition a = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
        getWindow().setEnterTransition(a);

        supportStartPostponedEnterTransition();


        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                if(ekstensi_gambar.equals(".gif")){
                    ImageView image = (ImageView) findViewById(R.id.image);
                    Glide
                            .with(detail_image.this)
                            .asGif()
                            .load("http://103.52.146.34/penir/penir13/IMAGE/" + nama_gambar + ekstensi_gambar)
                            .into(image);
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    public void comment(View view){
        Intent i = new Intent(this, dummy_comment.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(i, options.toBundle());
    }
}
