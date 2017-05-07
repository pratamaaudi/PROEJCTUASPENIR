package com.example.audi.uaspenir;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void animate(View view) {
        Intent i = new Intent(this, Main.class);
        Pair<View, String> p1 = Pair.create(findViewById(R.id.imglogo), "imglogo");
        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1);
        startActivity(i, option.toBundle());
    }
}
