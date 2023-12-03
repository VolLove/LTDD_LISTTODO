package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import Data.AdapterTypeJob;

public class TableTypeFragment extends Fragment {

    public static ListView lv;
    public static AdapterTypeJob adapterTypeJob;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table_type, container, false);
        MainActivity.btnCreate.setVisibility(View.VISIBLE);
        MainActivity.indext = 2;
        lv = view.findViewById(R.id.lvTB);
        adapterTypeJob = new AdapterTypeJob(requireContext(),R.layout.item_adapter_type,MainActivity.db.getAllTypeJobs());
        lv.setAdapter(adapterTypeJob);
        return view;
    }
}
