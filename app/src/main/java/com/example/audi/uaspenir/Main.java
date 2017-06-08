package com.example.audi.uaspenir;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Main extends AppCompatActivity {

    //viewpage untuk fragment dan tab
    public ViewPager vp;
    public TabLayout tabs;

    //untuk penanda waktu @onResume perlu nampilih dialog atau ndak (true=tampil)
    private boolean dialog;

    //kode untuk foto kamera
    final int CAMERA_PIC_REQUEST = 1333;

    //arraylist untuk hasil data json
    public static ArrayList<category> categoryArrayList;
    public static ArrayList<image> imageArrayList;
    public static ArrayList<image> imagesGaming;
    public static ArrayList<image> imagesAnimal;
    public static ArrayList<image> imagesNSFW;
    public static ArrayList<image> imagesDIY;
    public static ArrayList<image> imagesStudying;

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

    //penanda login
    public static Boolean login = false;

    String nama = "";

    public static Integer userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (login.equals(true)) {
            Bundle extras = getIntent().getExtras();
            if(extras!=null){
                userid = extras.getInt("userid");
                nama = extras.getString("nama");

                buatsnackbar("selamat datang, " + nama);
            }


        } else {

        }

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

        refreshcontent();

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

        //codingan navigationitem OPO IKU NAVIGATION ITEM?
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

                    case R.id.itemRefresh:
                        Intent intent = new Intent(Main.this, Main.class);
                        startActivity(intent);
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

                //set dialog true biar buka dialogfragment saat @
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

    public void refreshcontent(){
        //ERWIN
        instance = this;
        imageArrayList = new ArrayList<>();
        categoryArrayList = new ArrayList<>();
        imagesGaming = new ArrayList<>();
        imagesAnimal = new ArrayList<>();
        imagesNSFW = new ArrayList<>();
        imagesDIY = new ArrayList<>();
        imagesStudying = new ArrayList<>();


        //load class + baca data category
        ReadDataMain readCategory = new ReadDataMain(this);
        readCategory.execute(OwnLibrary.url_category, "category");

        //load class + baca data category
        ReadDataMain readImageGaming = new ReadDataMain(this);
        readImageGaming.execute(OwnLibrary.url_gaming, "gaming");

        //load class + baca data category
        ReadDataMain readImageAnimal = new ReadDataMain(this);
        readImageAnimal.execute(OwnLibrary.url_animal, "animal");

        //load class + baca data category
        ReadDataMain readImageNSFW = new ReadDataMain(this);
        readImageNSFW.execute(OwnLibrary.url_nsfw, "nsfw");

        //load class + baca data category
        ReadDataMain readImageStudying = new ReadDataMain(this);
        readImageStudying.execute(OwnLibrary.url_studying, "studying");

        //load class + baca data category
        ReadDataMain readImageDIY = new ReadDataMain(this);
        readImageDIY.execute(OwnLibrary.url_diy, "diy");
    }

    //ERWIN
    public static void readDataFinish(Context context, String result, String type) {
        if (type.equalsIgnoreCase("category")) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray json2 = json.getJSONArray("post");
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject c = json2.getJSONObject(i);
                    String categoryId = c.getString("categoryID");
                    String categoryName = c.getString("categoryName");
                    categoryArrayList.add(new category(Integer.parseInt(categoryId), categoryName));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("gaming")) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray json2 = json.getJSONArray("post");
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject c = json2.getJSONObject(i);
                    String imageID = c.getString("imageID");
                    String imagename = c.getString("imagename");
                    String ekstensi = c.getString("ekstensi");
                    String imagetitle = c.getString("imagetitle");
                    String category_categoryID = c.getString("Category_categoryID");
                    imagesGaming.add(new image(Integer.parseInt(imageID), imagetitle, imagename, ekstensi, Integer.parseInt(category_categoryID)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("animal")) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray json2 = json.getJSONArray("post");
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject c = json2.getJSONObject(i);
                    String imageID = c.getString("imageID");
                    String imagename = c.getString("imagename");
                    String ekstensi = c.getString("ekstensi");
                    String imagetitle = c.getString("imagetitle");
                    String category_categoryID = c.getString("Category_categoryID");
                    imagesAnimal.add(new image(Integer.parseInt(imageID), imagetitle, imagename, ekstensi, Integer.parseInt(category_categoryID)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("nsfw")) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray json2 = json.getJSONArray("post");
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject c = json2.getJSONObject(i);
                    String imageID = c.getString("imageID");
                    String imagename = c.getString("imagename");
                    String ekstensi = c.getString("ekstensi");
                    String imagetitle = c.getString("imagetitle");
                    String category_categoryID = c.getString("Category_categoryID");
                    imagesNSFW.add(new image(Integer.parseInt(imageID), imagetitle, imagename, ekstensi, Integer.parseInt(category_categoryID)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("diy")) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray json2 = json.getJSONArray("post");
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject c = json2.getJSONObject(i);
                    String imageID = c.getString("imageID");
                    String imagename = c.getString("imagename");
                    String ekstensi = c.getString("ekstensi");
                    String imagetitle = c.getString("imagetitle");
                    String category_categoryID = c.getString("Category_categoryID");
                    imagesDIY.add(new image(Integer.parseInt(imageID), imagetitle, imagename, ekstensi, Integer.parseInt(category_categoryID)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equalsIgnoreCase("studying")) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray json2 = json.getJSONArray("post");
                for (int i = 0; i < json2.length(); i++) {
                    JSONObject c = json2.getJSONObject(i);
                    String imageID = c.getString("imageID");
                    String imagename = c.getString("imagename");
                    String ekstensi = c.getString("ekstensi");
                    String imagetitle = c.getString("imagetitle");
                    String category_categoryID = c.getString("Category_categoryID");
                    imagesStudying.add(new image(Integer.parseInt(imageID), imagetitle, imagename, ekstensi, Integer.parseInt(category_categoryID)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        instance.setupViewPager();
    }

    //ERWIN / MOSES
    private void setupViewPager() {
        FragmentNSFW fragmentNSFW = new FragmentNSFW();
        adapterRecyclerCard = new AdapterRecyclerCard(getApplicationContext(), imagesNSFW, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ImageView image_post, Button btncomment) {
                //do something here with the position

                Intent i = new Intent(Main.this, detail_image.class);
                i.putExtra("transition_name", ViewCompat.getTransitionName(image_post));
                i.putExtra("nama_gambar", imagesNSFW.get(position).getImagename());
                i.putExtra("ekstensi_gambar", imagesNSFW.get(position).getEkstensi());
                i.putExtra("imageid", imagesNSFW.get(position).getImageID());


                Pair<View, String> p1 = Pair.create((View) image_post, ViewCompat.getTransitionName(image_post));
                Pair<View, String> p2 = Pair.create((View) btncomment, "btn" + ViewCompat.getTransitionName(image_post));
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(Main.this, p1, p2);

                if (imagesNSFW.get(position).getEkstensi().equals(".gif")) {
                    startActivity(i);
                } else {
                    startActivity(i, option.toBundle());
                }
            }
        });
        fragmentNSFW.mInstance(adapterRecyclerCard);

        FragmentAnimal fragmentAnimal = new FragmentAnimal();
        adapterRecyclerCard = new AdapterRecyclerCard(getApplicationContext(), imagesAnimal, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ImageView image_post, Button btncomment) {
                //do something here with the position


                Intent i = new Intent(Main.this, detail_image.class);
                i.putExtra("transition_name", ViewCompat.getTransitionName(image_post));
                i.putExtra("nama_gambar", imagesAnimal.get(position).getImagename());
                i.putExtra("ekstensi_gambar", imagesAnimal.get(position).getEkstensi());
                i.putExtra("imageid", imagesAnimal.get(position).getImageID());

                Pair<View, String> p1 = Pair.create((View) image_post, ViewCompat.getTransitionName(image_post));
                Pair<View, String> p2 = Pair.create((View) btncomment, "btn" + ViewCompat.getTransitionName(image_post));
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(Main.this, p1, p2);

                if (imagesAnimal.get(position).getEkstensi().equals(".gif")) {
                    startActivity(i);
                } else {
                    startActivity(i, option.toBundle());
                }

                //startActivity(i, option.toBundle());
            }
        });
        fragmentAnimal.mInstance(adapterRecyclerCard);

        FragmentGaming fragmentGaming = new FragmentGaming();
        adapterRecyclerCard = new AdapterRecyclerCard(getApplicationContext(), imagesGaming, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ImageView image_post, Button btncomment) {
                //do something here with the position


                Intent i = new Intent(Main.this, detail_image.class);
                i.putExtra("transition_name", ViewCompat.getTransitionName(image_post));
                i.putExtra("nama_gambar", imagesGaming.get(position).getImagename());
                i.putExtra("ekstensi_gambar", imagesGaming.get(position).getEkstensi());
                i.putExtra("imageid", imagesGaming.get(position).getImageID());

                Pair<View, String> p1 = Pair.create((View) image_post, ViewCompat.getTransitionName(image_post));
                Pair<View, String> p2 = Pair.create((View) btncomment, "btn" + ViewCompat.getTransitionName(image_post));
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(Main.this, p1, p2);

                if (imagesGaming.get(position).getEkstensi().equals(".gif")) {
                    startActivity(i);
                } else {
                    startActivity(i, option.toBundle());
                }
            }
        });
        fragmentGaming.mInstance(adapterRecyclerCard);

        FragmentDIY fragmentDIY = new FragmentDIY();
        adapterRecyclerCard = new AdapterRecyclerCard(getApplicationContext(), imagesDIY, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ImageView image_post, Button btncomment) {
                //do something here with the position


                Intent i = new Intent(Main.this, detail_image.class);
                i.putExtra("transition_name", ViewCompat.getTransitionName(image_post));
                i.putExtra("nama_gambar", imagesDIY.get(position).getImagename());
                i.putExtra("ekstensi_gambar", imagesDIY.get(position).getEkstensi());
                i.putExtra("imageid", imagesDIY.get(position).getImageID());

                Pair<View, String> p1 = Pair.create((View) image_post, ViewCompat.getTransitionName(image_post));
                Pair<View, String> p2 = Pair.create((View) btncomment, "btn" + ViewCompat.getTransitionName(image_post));
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(Main.this, p1, p2);

                if (imagesDIY.get(position).getEkstensi().equals(".gif")) {
                    startActivity(i);
                } else {
                    startActivity(i, option.toBundle());
                }
            }
        });
        fragmentDIY.mInstance(adapterRecyclerCard);

        FragmentStudying fragmentStudying = new FragmentStudying();
        adapterRecyclerCard = new AdapterRecyclerCard(getApplicationContext(), imagesStudying, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ImageView image_post, Button btncomment) {
                //do something here with the position


                Intent i = new Intent(Main.this, detail_image.class);
                i.putExtra("transition_name", ViewCompat.getTransitionName(image_post));
                i.putExtra("nama_gambar", imagesStudying.get(position).getImagename());
                i.putExtra("ekstensi_gambar", imagesStudying.get(position).getEkstensi());
                i.putExtra("imageid", imagesStudying.get(position).getImageID());

                Pair<View, String> p1 = Pair.create((View) image_post, ViewCompat.getTransitionName(image_post));
                Pair<View, String> p2 = Pair.create((View) btncomment, "btn" + ViewCompat.getTransitionName(image_post));
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(Main.this, p1, p2);

                if (imagesStudying.get(position).getEkstensi().equals(".gif")) {
                    startActivity(i);
                } else {
                    startActivity(i, option.toBundle());
                }
            }
        });
        fragmentStudying.mInstance(adapterRecyclerCard);

        AdapterPager adapterPager = new AdapterPager(getSupportFragmentManager());
        adapterPager.addFragment(fragmentAnimal);
        adapterPager.addFragment(fragmentGaming);
        adapterPager.addFragment(fragmentStudying);
        adapterPager.addFragment(fragmentDIY);
        adapterPager.addFragment(fragmentNSFW);

        vp.setAdapter(adapterPager);
    }


    //CODINGAN ONCLICK DARI UI
    public void pindahhalaman(View view) {
        //codingan waktu icon di tekan

        //buka menu slet kanan
        drawer.openDrawer(GravityCompat.START);
    }

    //WTF IKI
    public void header(View view) {
        //ndak perlu di jelasin
        buatsnackbar("WELCOME TO 8 GAG");
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
