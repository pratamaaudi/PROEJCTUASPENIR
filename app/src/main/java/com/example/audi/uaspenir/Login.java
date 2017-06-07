package com.example.audi.uaspenir;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Transition a = TransitionInflater.from(this).inflateTransition(R.transition.slide_bottom);
        getWindow().setEnterTransition(a);
    }

    public void daftar(View view){
        Intent i = new Intent(this, SignUp.class);
        Pair<View, String> p1 = Pair.create(findViewById(R.id.imglogo), "imglogo");
        Pair<View, String> p2 = Pair.create(findViewById(R.id.link_signup), "txtjudul");
        Pair<View, String> p3 = Pair.create(findViewById(R.id.input_password), "password");
        Pair<View, String> p4 = Pair.create(findViewById(R.id.btn_login), "button");
        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4);
        startActivity(i, option.toBundle());
    }
}
