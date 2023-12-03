package Data;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.myapplication.EditFragment;
import com.example.myapplication.EditTypeFragment;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import Model.Job;
import Model.Type_Job;

public class AdapterTypeJob extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Type_Job> typeJobs;


    public AdapterTypeJob(@NonNull Context context, int resource, ArrayList<Type_Job> typeJobs) {
        super(context, resource, typeJobs);
        this.context = context;
        this.resource = resource;
        this.typeJobs = typeJobs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvIDType = convertView.findViewById(R.id.tvIDType);
        TextView tvTitleType = convertView.findViewById(R.id.tvTitleType);
        Button btnDelete = convertView.findViewById(R.id.btnTypeDelete);
        Button btnEdit = convertView.findViewById(R.id.btnTypeEdit);
        Type_Job typeJob = typeJobs.get(position);
        tvTitleType.setText(typeJob.getTitle());
        tvIDType.setText(String.valueOf(typeJob.getId()));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lấy id truyền vào fragment
                Bundle bundle = new Bundle();
                bundle.putInt("key", typeJob.getId());
                EditTypeFragment fragment = new EditTypeFragment();
                fragment.setArguments(bundle);
                //
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.mainFragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc muốn xóa  không?").setTitle("Xóa")
                        .setPositiveButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.db.deleteTypeJob(typeJob.getId());
                                setData(MainActivity.db.getAllTypeJobs());
                                notifyDataSetChanged();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return typeJobs.size();
    }

    public void setData(ArrayList<Type_Job> newData) {
        typeJobs = newData;
    }
}
