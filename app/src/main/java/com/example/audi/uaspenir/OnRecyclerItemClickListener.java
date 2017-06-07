package com.example.audi.uaspenir;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by GeolseuDei on 5/22/2017.
 */

public interface OnRecyclerItemClickListener {
    void onItemClick(View v, int position, ImageView image_post, Button btncomment);
}
