package com.example.audi.uaspenir;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.transition.Transition;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {

    //viewpage
    public ViewPager vp;
    public TabLayout tabs;

    private boolean dialog;

    final int CAMERA_PIC_REQUEST = 1333;

    public static ArrayList<category> categoryArrayList;
    public static ArrayList<image> imageArrayList;
    public static Main instance = null;

    public post post;

    public Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        instance = this;
        imageArrayList = new ArrayList<>();
        categoryArrayList = new ArrayList<>();

        imageArrayList.add(new image(1, "asd", "asd", 1));



        ReadData readCategory = new ReadData(this);
        readCategory.execute(OwnLibrary.url_category, "category");

        ReadData readImage = new ReadData(this);
        readImage.execute(OwnLibrary.url_image, "image");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int height = this.getResources().getDisplayMetrics().heightPixels;

        //viewpage
        vp = (ViewPager) findViewById(R.id.viewpager);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = tabs.getTabAt(position);
                tab.select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });

        android.transition.Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_left);
        getWindow().setExitTransition(transition);

    }

    public static void readDataFinish(Context context, String result, String type) {
        if (type.equalsIgnoreCase("category")) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray json2 = json.getJSONArray("post");
                categoryArrayList = new ArrayList<>();
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject c = json2.getJSONObject(i);
                    String categoryId = c.getString("categoryID");
                    String categoryName = c.getString("categoryName");
                    categoryArrayList.add(new category(Integer.parseInt(categoryId), categoryName));
                }
                new OwnLibrary().toastLong(context, "Size Arraylist category : " + categoryArrayList.size());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("image")) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray json2 = json.getJSONArray("post");
                imageArrayList = new ArrayList<>();
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject c = json2.getJSONObject(i);
                    String imageID = c.getString("imageID");
                    String imagename = c.getString("imagename");
                    String ekstensi = c.getString("ekstensi");
                    String category_categoryID = c.getString("Category_categoryID");
                    imageArrayList.add(new image(Integer.parseInt(imageID), imagename, ekstensi, Integer.parseInt(category_categoryID)));
                }
                new OwnLibrary().toastLong(context, "Size Arraylist image : " + imageArrayList.size());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        instance.setupViewPager();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void ambilfoto(View view) {
        //buatsnackbar(view);

        //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //startActivity(intent);

        //Intent Intent3=new   Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        //startActivity(Intent3);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }

    AdapterRecyclerCard adapterRecyclerCard;

    private void setupViewPager() {
        CardContentFragment cardContentFragment = new CardContentFragment();
        adapterRecyclerCard = new AdapterRecyclerCard(getApplicationContext(), imageArrayList, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ImageView image_post) {
                //do something here with the position
                new OwnLibrary().toastShort(getApplicationContext(), "Position : " + position);

                Intent i = new Intent(Main.this, detail_image.class);
                i.putExtra("transition_name", ViewCompat.getTransitionName(image_post));
                i.putExtra("nama_gambar", imageArrayList.get(position).getImagename());
                i.putExtra("ekstensi_gambar", imageArrayList.get(position).getEkstensi());

                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(Main.this, image_post, ViewCompat.getTransitionName(image_post));
                startActivity(i, option.toBundle());
            }
        });
        cardContentFragment.mInstance(adapterRecyclerCard);

        AdapterPager adapterPager = new AdapterPager(getSupportFragmentManager());
        adapterPager.addFragment(new ListContentFragment());
        adapterPager.addFragment(new TileContentFragment());
        adapterPager.addFragment(cardContentFragment);

        vp.setAdapter(adapterPager);
    }

    public void buatsnackbar(String text) {
        Snackbar.make(getWindow().getDecorView().getRootView(), text, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (data != null) {
                image = (Bitmap) data.getExtras().get("data");
                //ImageView imageview = (ImageView) findViewById(R.id.imglogo);
                //imageview.setImageBitmap(image);

                //new PostTask().execute(imageToString(image), "test");
                //buatsnackbar("Uploading image . . .");

                dialog = true;
            }
        }
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private class PostTask extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Main.this);
            pDialog.setMessage("Uploading Photo. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... data) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://103.52.146.34/penir/penir13/upload.php");

            try {
                //add data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("image", data[0]));
                nameValuePairs.add(new BasicNameValuePair("name", data[1]));
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

    public void pindahhalaman(View view) {
        Intent i = new Intent(this, gif.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (dialog) {
            Bundle b = new Bundle();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imgBytes = byteArrayOutputStream.toByteArray();

            b.putByteArray("image", imgBytes);

            FragmentManager fm = getSupportFragmentManager();
            post = new post();
            post.setArguments(b);

            post.show(fm, "post new meme");

            dialog = false;
        }
    }
}
