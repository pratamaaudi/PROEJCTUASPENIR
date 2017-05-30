package com.example.audi.uaspenir;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by X550D on 5/27/2017.
 */

public class AdapterRecyclerCard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<image> images;

    public AdapterRecyclerCard(Context context, ArrayList<image> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_content_inflate, parent, false);
        RecyclerView.ViewHolder vhold = new RecyclerView.ViewHolder(v) {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return vhold;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView title = (TextView) holder.itemView.findViewById(R.id.title_card);
        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.image_card);
        title.setText(images.get(position).getImagename());
        URL url = null;
        try {
            url = new URL("http://103.52.146.34/penir/penir13/IMAGE/" + images.get(position).getImagename() + images.get(position).getEkstensi());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageView.setImageBitmap(bmp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
