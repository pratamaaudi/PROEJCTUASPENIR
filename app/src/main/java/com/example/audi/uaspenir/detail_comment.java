package com.example.audi.uaspenir;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class detail_comment extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterRecyclerKomeng adapterRecyclerKomeng;
    public static ArrayList<comment> komeng;
    public static detail_comment instance = null;
    Integer imageid;
    EditText pltcomment;
    Button btnpost;
    LinearLayout llinsertcomment;

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
        pltcomment = (EditText) findViewById(R.id.pltComment);
        btnpost = (Button) findViewById(R.id.btnPost);
        llinsertcomment = (LinearLayout) findViewById(R.id.llinsertcomment);

        Bundle extras = getIntent().getExtras();
        imageid = extras.getInt("imageid");

        loadkomeng();

        if (Main.login) {
            btnpost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressDialog progressDialog = new ProgressDialog(detail_comment.this);
                    progressDialog.setMessage("Loading..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://103.52.146.34/penir/penir13/insert_comment.php");
                    try {
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("isicomment", pltcomment.getText().toString().trim()));
                        nameValuePairs.add(new BasicNameValuePair("userid", String.valueOf(Main.userid)));
                        nameValuePairs.add(new BasicNameValuePair("imageid", String.valueOf(imageid)));
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpclient.execute(httppost);
                        String respon = EntityUtils.toString(response.getEntity());
                        pltcomment.setText("");
                    } catch (Exception e) {

                    }
                    progressDialog.dismiss();
                    loadkomeng();
                }
            });
        } else {
            pltcomment.setVisibility(View.GONE);
            btnpost.setVisibility(View.GONE);
            llinsertcomment.setVisibility(View.GONE);
        }
    }

    private void setupRecyclerView() {
        adapterRecyclerKomeng = new AdapterRecyclerKomeng(getApplicationContext(), komeng);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterRecyclerKomeng);
        recyclerView.setNestedScrollingEnabled(true);
    }

    public void loadkomeng(){
        komeng = new ArrayList<>();
        ProgressDialog progressDialog = new ProgressDialog(detail_comment.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://103.52.146.34/penir/penir13/comment.php");
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("imageid", String.valueOf(imageid)));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            String respon = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(respon);
            JSONArray json2 = json.getJSONArray("post");
            for (int i = 0; i < json2.length(); i++) {
                JSONObject c = json2.getJSONObject(i);
                int commentID = c.getInt("commentID");
                String isicomment = c.getString("isicomment");
                String fullname = c.getString("fullname");
                komeng.add(new comment(commentID, isicomment, fullname));
            }
        } catch (Exception e) {

        }
        setupRecyclerView();
        progressDialog.dismiss();
    }
}
