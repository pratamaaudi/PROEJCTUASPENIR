package com.example.audi.uaspenir;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by X550D on 5/27/2017.
 */

public class AdapterRecyclerKomeng extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<comment> komeng;

    public AdapterRecyclerKomeng(Context context, ArrayList<comment> komeng) {
        this.context = context;
        this.komeng = komeng;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.isi_comment, parent, false);
        final RecyclerView.ViewHolder mViewHolder = new RecyclerView.ViewHolder(v){
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        TextView nama = (TextView) holder.itemView.findViewById(R.id.txtNama);
        TextView komen = (TextView) holder.itemView.findViewById(R.id.txtComment);
        nama.setText("nama user e nang kene");
        komen.setText(komeng.get(i).getIsiComment());
    }

    @Override
    public int getItemCount() {
        return komeng.size();
    }

}
