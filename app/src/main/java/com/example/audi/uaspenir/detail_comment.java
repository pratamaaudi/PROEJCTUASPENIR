package com.example.audi.uaspenir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;

public class detail_comment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comment);

        android.transition.Transition a = TransitionInflater.from(this).inflateTransition(R.transition.slide_bottom);
        getWindow().setEnterTransition(a);
    }
}
