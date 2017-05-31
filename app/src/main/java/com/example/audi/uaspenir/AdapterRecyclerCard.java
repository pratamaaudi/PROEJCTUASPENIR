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

public class AdapterRecyclerCard extends RecyclerView.Adapter<AdapterRecyclerCard.ViewHolder> {
    Context context;
    ArrayList<image> images;
    OnRecyclerItemClickListener onRecyclerItemClickListener;

    public AdapterRecyclerCard(Context context, ArrayList<image> images, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.context = context;
        this.images = images;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public AdapterRecyclerCard.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_content_inflate, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerItemClickListener.onItemClick(v, mViewHolder.getPosition());
            }
        });

        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(AdapterRecyclerCard.ViewHolder holder, int position) {
        holder.title.setText(images.get(position).getImagename());
        URL url = null;
        try {
            url = new URL("http://103.52.146.34/penir/penir13/IMAGE/" + images.get(position).getImagename() + images.get(position).getEkstensi());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.imageView.setImageBitmap(bmp);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_card);
            imageView = (ImageView) itemView.findViewById(R.id.image_card);
        }
    }
}
