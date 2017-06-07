package com.example.audi.uaspenir;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGaming extends Fragment {

    public FragmentGaming() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    AdapterRecyclerCard adapterRecyclerCard;

    public FragmentGaming mInstance(AdapterRecyclerCard adapterRecyclerCard){
        FragmentGaming fragmentGaming = new FragmentGaming();
        this.adapterRecyclerCard = adapterRecyclerCard;
        return fragmentGaming;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gaming, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_card_gaming);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterRecyclerCard);
        recyclerView.setNestedScrollingEnabled(true);
        return view;
    }
}
