package com.example.audi.uaspenir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class gif extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        GifImageView g = (GifImageView) findViewById(R.id.gif);
        GifDrawable g2 = (GifDrawable) g.getDrawable();
        if(g2.isRunning()){
            g2.stop();
            g.setAlpha(100);
        }
    }

    public void playpause(View view){
        GifImageView g = (GifImageView) view;
        GifDrawable g2 = (GifDrawable) g.getDrawable();
        if(g2.isRunning()){
            g2.stop();
            g.setAlpha(100);
        } else {
            g2.start();
            g.setAlpha(255);
        }
    }
}
