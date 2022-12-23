package com.example.ronlulwi_205857394;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textview.MaterialTextView;

public class FragmentMap extends Fragment {

    private MaterialTextView map_LBL_title;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        findViews(view);
        initViews();

        return view;
    }
    private void findViews(View view) {
        map_LBL_title = view.findViewById(R.id.map_LBL_title);
    }

    private void initViews() {

    }


    public void zoom(double lat, double lon) {
        //TODO display map
        map_LBL_title.setText(lat + "\n" + lon);
    }
}
