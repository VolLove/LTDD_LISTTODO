package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Date;

import Model.Job;

public class DetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        MainActivity.btnCreate.setVisibility(View.GONE);
        MainActivity.indext = 0;
        TextView tvTitle, tvDecription, tvDateCreate, tvDateFinish, tvDeadline, tvType, tvStart;
        tvDeadline = view.findViewById(R.id.tvDetailDeadline);
        tvDateCreate = view.findViewById(R.id.tvDetailDateCreate);
        tvDateFinish = view.findViewById(R.id.tvDetailDateFinish);
        LinearLayout linearLayout = view.findViewById(R.id.llDetailDateF);
        tvTitle = view.findViewById(R.id.tvDetailTitle);
        tvDecription = view.findViewById(R.id.tvDetailDes);
        tvType = view.findViewById(R.id.tvDetailType);
        tvStart = view.findViewById(R.id.tvDetailStatus);
        Button button = view.findViewById(R.id.btnDetailAccept);

        Bundle bundle = getArguments();
        int i = bundle.getInt("key");
        Job job = MainActivity.db.getJobById(i);

        tvDecription.setText(job.getDecription());
        tvDateCreate.setText(job.getDate_create());
        //nếu đã hoàn thành hoặc quá ha thì ẩn nút xác nhận
        //ẩn ngày hoàn thành khi chưa hoàn thành hoặc quá hạn
        if (job.getStatus() == 0) {
          linearLayout.setVisibility(View.GONE);
            tvStart.setText("Proceed");
        } else if (job.getStatus() == 1) {
            button.setVisibility(View.GONE);
            tvDateFinish.setText(job.getDate_finish());
            tvStart.setText("Complete");
        } else {
            button.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            tvStart.setText("Late");
        }
        tvTitle.setText(job.getTitle());
        tvDeadline.setText(job.getDeadline());
        tvType.setText(MainActivity.db.getTypeById(job.getType_id()).getTitle());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Xa nhận hoàn thành").setTitle("Xác nhận")
                        .setPositiveButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                job.setStatus(1);
                                job.setDate_finish(MainActivity.getStringFromDate(new Date()));
                                MainActivity.db.updateJob(job);
                                TableFragment fragment = new TableFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.mainFragment, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }
}
