package com.example.audi.uaspenir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Transition a = TransitionInflater.from(this).inflateTransition(R.transition.slide_up);
        getWindow().setEnterTransition(a);
    }
}
