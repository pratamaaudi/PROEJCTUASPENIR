package com.example.audi.uaspenir;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    final int CAMERA_PIC_REQUEST=1333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void logoclick(View view) {
        //buatsnackbar(view);

        //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //startActivity(intent);

        //Intent Intent3=new   Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        //startActivity(Intent3);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }

    public void buatsnackbar(View view) {
        Snackbar.make(view, "LOGO ditekan!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.imglogo);
            imageview.setImageBitmap(image);
        }
    }
}
