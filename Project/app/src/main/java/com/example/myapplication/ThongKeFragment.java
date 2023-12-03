package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ThongKeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke, container, false);
        MainActivity.btnCreate.setVisibility(View.GONE);
        MainActivity.indext = 0;
        TextView tv1 = view.findViewById(R.id.tvCollum1);
        TextView tv2 = view.findViewById(R.id.tvCollum2);
        TextView tv3 = view.findViewById(R.id.tvCollum3);
        int tong = MainActivity.db.getAllJobs(MainActivity.USER_ID).size();

        int start1 = MainActivity.db.getJobsByStatus(0).size();
        int h1 = 2000 *start1 / tong;
        tv1.setText(String.valueOf(start1));
        tv1.setHeight(h1);

        int start2 = MainActivity.db.getJobsByStatus(1).size();
        int h2 = 2000 * start2 / tong;
        tv2.setText(String.valueOf(start2));
        tv2.setHeight(h2);

        int start3 = MainActivity.db.getJobsByStatus(2).size();
        int h3 = 2000 * start3 / tong;
        tv3.setText(String.valueOf(start3));
        tv3.setHeight(h3);

        return view;
    }
}
