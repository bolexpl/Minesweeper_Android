package com.example.bolek.minesweeper_android;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        Field[] fields = new Field[64];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new Field(getContext(), Field.ODKRYTE, i);
        }

        FieldAdapter fa = new FieldAdapter(getContext(), fields);

        GridView gridView = (GridView) getActivity().findViewById(R.id.grid);
//        gridView.setColumnWidth(800);
//        gridView.setNumColumns(8);
        gridView.setAdapter(fa);
    }
}
