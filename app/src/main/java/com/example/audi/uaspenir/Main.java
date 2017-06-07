package com.example.audi.uaspenir;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.transition.Transition;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Base64;
import android.view.MenuItem;
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

    //viewpage untuk fragment dan tab
    public ViewPager vp;
    public TabLayout tabs;

    //untuk penanda waktu @onResume perlu nampilih dialog atau ndak (true=tampil)
    private boolean dialog;

    //kode untuk foto
    final int CAMERA_PIC_REQUEST = 1333;

    //arraylist untuk hasil data json
    public static ArrayList<category> categoryArrayList;
    public static ArrayList<image> imageArrayList;
    public static Main instance = null;

    //class untuk dialogfragment
    public post post;

    //nampung data gambarnya, karena setelah di foto ga langsung dipake, tapi dipake di @onResume
    public Bitmap image;

    //untuk menu slet kanan, biar bisa di coding bukatutup
    public DrawerLayout drawer;

    //untuk coding waku ada menu yang di pilih, termasuk ambil item apa yang di klik
    public NavigationView nv;

    //MOSES
    AdapterRecyclerCard adapterRecyclerCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ANIMASI - load slide_left untuk exit transition
        android.transition.Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_left);
        getWindow().setExitTransition(transition);

        //ERWIN
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //ben konek nang UI
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        vp = (ViewPager) findViewById(R.id.viewpager);
        tabs = (TabLayout) findViewById(R.id.tabs);

        //ERWIN
        instance = this;
        imageArrayList = new ArrayList<>();
        categoryArrayList = new ArrayList<>();

        //ERWIN
        imageArrayList.add(new image(1, "asd", "asd", 1));

        //load class + baca data category
        ReadData readCategory = new ReadData(this);
        readCategory.execute(OwnLibrary.url_category, "category");

        //load class + baca data category
        ReadData readImage = new ReadData(this);
        readImage.execute(OwnLibrary.url_image, "image");

        //codingan TAB
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //coding waktu Tab di pilih

                //pindah viewpager sesuai dengan position tab
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //codingan Viewpager
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

        //codingan navigationitem
        nv = (NavigationView) findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawer.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.itemLogin:
                        Intent i = new Intent(Main.this, Login.class);
                        Pair<View, String> p1 = Pair.create(findViewById(R.id.imglogo), "imglogo");
                        Pair<View, String> p2 = Pair.create(findViewById(R.id.txtjudul), "txtjudul");
                        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(Main.this, p1, p2);
                        startActivity(i, option.toBundle());
                        break;
                }
                return false;
            }
        });
    }

    //codingan setelah selesai intent explicit (moto)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            //codinga kalau abis moto

            if (data != null) {
                //coding kalau ada datanya

                //tampung foto tadi ke image
                image = (Bitmap) data.getExtras().get("data");

                //set dialog true biar buka dialogfragment
                dialog = true;
            }
        }
    }

    //codingan yang jalan setelah oncreate atau onactivityresult
    @Override
    protected void onResume() {
        super.onResume();
        //codingan yang jalan setelah oncreate atau onactivityresult

        //cek perlu buat dialog atau ndak
        if (dialog) {
            //codingan kalau dialog = true

            //bundle untuk kirim data
            Bundle b = new Bundle();

            //convert bitmap jadi array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imgBytes = byteArrayOutputStream.toByteArray();

            //masukkan array ke bundle
            b.putByteArray("image", imgBytes);

            //load fragmentdialog
            FragmentManager fm = getSupportFragmentManager();
            post = new post();
            post.setArguments(b);
            post.show(fm, "post new meme");

            //set dialog = false, biar nanti ga muncul lagi
            dialog = false;
        }
    }

    //ERWIN
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

    //ERWIN / MOSES
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


    //CODINGAN ONCLICK DARI UI
    public void pindahhalaman(View view) {
        //codingan waktu icon di tekan

        //buka menu slet kanan
        drawer.openDrawer(GravityCompat.START);
    }

    public void lordmoses(View view) {
        //ndak perlu di jelasin
        buatsnackbar("ALL HAIL LORD MOSES !!!!");
    }

    public void ambilfoto(View view) {
        //codingan waktu FAB foto di pilih

        //buka kamera
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }

    //CODINGAN DLL
    //biar ga balik ke splash
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void buatsnackbar(String text) {
        Snackbar.make(getWindow().getDecorView().getRootView(), text, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
