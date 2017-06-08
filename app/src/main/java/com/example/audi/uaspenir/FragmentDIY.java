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
public class FragmentDIY extends Fragment {


    public FragmentDIY() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    AdapterRecyclerCard adapterRecyclerCard;

    public FragmentDIY mInstance(AdapterRecyclerCard adapterRecyclerCard){
        FragmentDIY fragmentDIY = new FragmentDIY();
        this.adapterRecyclerCard = adapterRecyclerCard;
        return fragmentDIY;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_diy, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_card_diy);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterRecyclerCard);
        recyclerView.setNestedScrollingEnabled(true);
        return view;
    }

}
