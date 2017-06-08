package com.example.audi.uaspenir;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class detail_comment extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterRecyclerKomeng adapterRecyclerKomeng;
    public static ArrayList<comment> komeng;
    public static detail_comment instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comment);

        instance = this;
//ERWIN
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        android.transition.Transition a = TransitionInflater.from(this).inflateTransition(R.transition.slide_bottom);
        getWindow().setEnterTransition(a);
        komeng = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_comment);

        if (Main.login) {
        } else {
            EditText pltcomment = (EditText) findViewById(R.id.pltComment);
            Button btnpost = (Button) findViewById(R.id.btnPost);
            pltcomment.setVisibility(View.GONE);
            btnpost.setVisibility(View.GONE);
        }

        ReadDataKomeng readDataKomeng = new ReadDataKomeng(detail_comment.this);
        readDataKomeng.execute(OwnLibrary.url_komeng, "komeng");
    }

    public static void readDataFinish(Context context, String result, String type) {
        if (type.equalsIgnoreCase("komeng")) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray json2 = json.getJSONArray("post");
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject c = json2.getJSONObject(i);
                    int commentID = c.getInt("commentID");
                    String isicomment = c.getString("isicomment");
                    String fullname = c.getString("fullname");
                    komeng.add(new comment(commentID, isicomment, fullname));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        instance.setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapterRecyclerKomeng = new AdapterRecyclerKomeng(getApplicationContext(), komeng);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterRecyclerKomeng);
        recyclerView.setNestedScrollingEnabled(true);
    }
}
