package com.example.audi.uaspenir;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by GeolseuDei on 5/25/2017.
 */

public class OwnLibrary {
    public static String url_category = "http://103.52.146.34/penir/penir13/category.php";
    public static String url_image ="http://103.52.146.34/penir/penir13/image.php";
    public static String url_gaming = "http://103.52.146.34/penir/penir13/Gaming.php";
    public static String url_animal = "http://103.52.146.34/penir/penir13/Animal.php";
    public static String url_nsfw = "http://103.52.146.34/penir/penir13/NSFW.php";

    public void toastShort(Context context, String content){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public void toastLong(Context context, String content){
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
