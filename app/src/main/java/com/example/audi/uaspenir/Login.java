package com.example.audi.uaspenir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    EditText txtusername, txtpassword;
    Transition transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_bottom);
        getWindow().setEnterTransition(transition);
    }

    public void login(View view){
        txtusername = (EditText) findViewById(R.id.input_username);
        txtpassword = (EditText) findViewById(R.id.input_password);

        new PostTask().execute(
                txtusername.getText().toString(),
                txtpassword.getText().toString()
        );
    }

    private class PostTask extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Login . . ");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... data) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://103.52.146.34/penir/penir13/login.php");

            try {
                //add data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", data[0]));
                nameValuePairs.add(new BasicNameValuePair("password", data[1]));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //execute http post
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
        }
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
