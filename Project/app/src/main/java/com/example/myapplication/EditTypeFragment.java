package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import Model.Type_Job;

public class EditTypeFragment  extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_type,container,false);
        MainActivity.btnCreate.setVisibility(View.GONE);
        MainActivity.indext = 0;
        EditText edtTitle = view.findViewById(R.id.edtEditTypeTitle);
        Button btnSave = view.findViewById(R.id.btnEditTypeSave);
        Button btnClose = view.findViewById(R.id.btnEditTypeClose);
        Bundle bundle = getArguments();
        int i = bundle.getInt("key");
        Type_Job typeJob = MainActivity.db.getTypeById(i);
        edtTitle.setText(typeJob.getTitle());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTitle.getText().toString().isEmpty()){
                    edtTitle.setError("Yêu cầu nhập trường");
                    return;
                }
                typeJob.setTitle(edtTitle.getText().toString());
                MainActivity.db.updateTypeJob(typeJob);
                TableTypeFragment fragment = new TableTypeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.mainFragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
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
