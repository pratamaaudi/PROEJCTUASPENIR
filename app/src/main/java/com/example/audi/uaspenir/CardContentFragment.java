package com.example.audi.uaspenir;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardContentFragment extends Fragment {


    public CardContentFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    AdapterRecyclerCard adapterRecyclerCard;

    public CardContentFragment mInstance(Context context, ArrayList<image> images){
        CardContentFragment cardContentFragment = new CardContentFragment();
        adapterRecyclerCard = new AdapterRecyclerCard(context, images);
        return cardContentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_content, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_card);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterRecyclerCard);
		return view;

    }

}
