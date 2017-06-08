package com.example.audi.uaspenir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class detail_comment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comment);

        android.transition.Transition a = TransitionInflater.from(this).inflateTransition(R.transition.slide_bottom);
        getWindow().setEnterTransition(a);

        if(Main.login){

        } else {
            EditText pltcomment = (EditText) findViewById(R.id.pltComment);
            Button btnpost = (Button) findViewById(R.id.btnPost);
            pltcomment.setVisibility(View.GONE);
            btnpost.setVisibility(View.GONE);
        }
    }
}
