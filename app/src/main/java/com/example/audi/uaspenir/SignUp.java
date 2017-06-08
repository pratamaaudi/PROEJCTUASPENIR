package com.example.audi.uaspenir;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Transition a = TransitionInflater.from(this).inflateTransition(R.transition.slide_up);
        getWindow().setEnterTransition(a);
    }

    public void signup(View view){
        EditText fullname, username, password;

        fullname = (EditText) findViewById(R.id.input_full_name);
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);

        ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://103.52.146.34/penir/penir13/signup.php");

        try{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("fullname", fullname.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("username", username.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("password", password.getText().toString().trim()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
        } catch (Exception e){

        }
        progressDialog.dismiss();

    }
}
