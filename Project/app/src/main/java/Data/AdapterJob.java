package Data;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DetailFragment;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.TableFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Model.Job;

public class AdapterJob extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Job> jobs;


    public AdapterJob(Context context, int resource, ArrayList<Job> jobs) {
        super(context, resource, jobs);
        this.jobs = jobs;
        this.context = context;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvTitle, tvDeadline;
        Switch rank;
        ImageButton ibtnEdit, ibtnDelete, ibtnView;

        ibtnView = convertView.findViewById(R.id.ibtnView);
        tvTitle = convertView.findViewById(R.id.tvTitle);
        tvDeadline = convertView.findViewById(R.id.tvDeadline);
        rank = convertView.findViewById(R.id.swRank);
        ibtnEdit = convertView.findViewById(R.id.ibtnEdit);
        ibtnDelete = convertView.findViewById(R.id.ibtnDelete);
        Job job = jobs.get(position);
        if (job.getRank() != 0) {
            rank.setChecked(true);
        } else {
            rank.setChecked(false);
        }
        tvTitle.setText(job.getTitle());
        tvDeadline.setText(job.getDeadline());
        ibtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Bạn có chắc muốn xóa không?").setTitle("Xóa")
                        .setPositiveButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.db.deleteJob(job.getId());
                                // Load lại danh sách công việc
                                setData(MainActivity.db.getAllJobsSortedByStatusDescending(MainActivity.USER_ID));
                                notifyDataSetChanged();

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        ibtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("key", job.getId());
                DetailFragment fragment = new DetailFragment();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.mainFragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        ibtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        rank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    job.setRank(1);
                } else {
                    job.setRank(0);
                }

                // Cập nhật giá trị rank vào cơ sở dữ liệu
                MainActivity.db.updateJob(job);

                // Load lại danh sách công việc
                setData(MainActivity.db.getAllJobsSortedByStatusDescending(MainActivity.USER_ID));
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return jobs.size();
    }

    private void setData(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }
}
