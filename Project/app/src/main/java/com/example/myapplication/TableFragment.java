package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import Data.AdapterJob;

public class TableFragment extends Fragment {
   public static ListView lv;
   public static AdapterJob adapterJob;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        MainActivity.btnCreate.setVisibility(View.VISIBLE);
        MainActivity.indext = 1;

        lv = view.findViewById(R.id.lvTable);

        adapterJob = new AdapterJob(requireContext(), R.layout.item_adapter, MainActivity.db.getAllJobsSortedByStatusDescending(MainActivity.USER_ID));
        lv.setAdapter(adapterJob);
        return view;
    }

}
