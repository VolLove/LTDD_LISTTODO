package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Model.Job;
import Model.Type_Job;

public class EditFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        MainActivity.btnCreate.setVisibility(View.GONE);
        MainActivity.indext = 0;
        EditText editTextTitle, editTextDescription, editTextDeadline;
        Calendar calendar;
        Spinner spinnerTypeJob;
        Button buttonSave, buttonSelectDateTime;
        spinnerTypeJob = view.findViewById(R.id.spinnerTypeJob);
        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        buttonSelectDateTime = view.findViewById(R.id.buttonSelectDateTime);
        editTextDeadline = view.findViewById(R.id.editTextDeadline);
        buttonSave = view.findViewById(R.id.buttonSave);
        Button btnClose = view.findViewById(R.id.buttonClose);
        calendar = Calendar.getInstance();
        ArrayList<Type_Job> typeJobs = MainActivity.db.getAllTypeJobs();
        ArrayAdapter<Type_Job> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, typeJobs);
        spinnerTypeJob.setAdapter(adapter);
        Bundle bundle = getArguments();
        int i = bundle.getInt("key");
        Job job = MainActivity.db.getJobById(i);
        editTextTitle.setText(job.getTitle());
        editTextDescription.setText(job.getDecription());
        editTextDeadline.setText(job.getDeadline());

        buttonSelectDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        (datePicker, year, month, dayOfMonth) -> {
                            calendar.set(year, month, dayOfMonth);
                            TimePickerDialog timePickerDialog = new TimePickerDialog(
                                    getContext(),
                                    (view1, hourOfDay, minute) -> {
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar.set(Calendar.MINUTE, minute);
                                        editTextDeadline.setText(MainActivity.getStringFromDate(calendar.getTime()));
                                    },
                                    calendar.get(Calendar.HOUR_OF_DAY),
                                    calendar.get(Calendar.MINUTE),
                                    true
                            );
                            timePickerDialog.show();
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String deadline = editTextDeadline.getText().toString().trim();
                // Kiểm tra xem có trường nào trống không
                if (title.isEmpty()) {
                    editTextTitle.setError("Yêu cầu nhập trường!");
                    return;
                }
                if (description.isEmpty()) {
                    editTextDescription.setError("Yêu cầu nhập trường!");
                    return;
                }

                if (deadline.isEmpty()) {
                    editTextDeadline.setError("Yêu cầu nhập trường!");
                    return;
                }
                Type_Job typeJob = typeJobs.get(spinnerTypeJob.getSelectedItemPosition());
                job.setType_id(typeJob.getId());
                job.setDeadline(deadline);
                job.setDecription(description);
                job.setTitle(title);
                MainActivity.db.updateJob(job);
                TableFragment fragment = new TableFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.mainFragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                // Hiển thị thông báo hoặc chuyển đến màn hình khác sau khi lưu
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        return view;
    }
}
