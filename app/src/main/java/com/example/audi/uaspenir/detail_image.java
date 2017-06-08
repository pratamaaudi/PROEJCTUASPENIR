package com.example.audi.uaspenir;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class detail_image extends AppCompatActivity {

    Integer imageid;

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
        imageid = extras.getInt("imageid");

        if(ekstensi_gambar.equals(".gif")){
            ImageView image = (ImageView) findViewById(R.id.image);
            Glide
                    .with(detail_image.this)
                    .asGif()
                    .load("http://103.52.146.34/penir/penir13/IMAGE/" + nama_gambar + ekstensi_gambar)
                    .into(image);
        } else {
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

            String transitionnamebutton = "btn"+imageTransitionName;

            ImageButton btncomment = (ImageButton) findViewById(R.id.imgcomment);
            btncomment.setTransitionName(transitionnamebutton);

            Transition a = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
            getWindow().setEnterTransition(a);
        }
        supportStartPostponedEnterTransition();
    }

    public void comment(View view){
        Intent i = new Intent(this, dummy_comment.class);
        i.putExtra("imageid", imageid);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(i, options.toBundle());
    }
}
