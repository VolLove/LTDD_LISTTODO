package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Model.Job;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        MainActivity.btnCreate.setVisibility(View.GONE);
        MainActivity.indext = 0;
        ArrayList<Job> jobs1 = MainActivity.db.getAllJobs(MainActivity.USER_ID);
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        for (int a = 0; a < jobs1.size(); a++) {
            Job job = jobs1.get(a);
            Date deadlineDate;
            deadlineDate = MainActivity.getDateFromString(job.getDeadline());

            // So sánh ngày giờ hiện tại với deadline
            if (currentDate.after(deadlineDate)) {
                // Nếu deadline đã vượt qua, thay đổi status của công việc thành 2
                job.setStatus(2);
                // Cập nhật status mới vào cơ sở dữ liệu
                MainActivity.db.updateJob(job);
            }
        }

        return view;
    }
}
