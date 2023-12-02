package Data;

import android.content.Context;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.TableFragment;

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
        Job job = new Job();
        job = jobs.get(position);
        if (job.getRank() != 0) {
            rank.setChecked(true);
        } else {
            rank.setChecked(false);
        }
        tvTitle.setText(job.getTitle() + "/" + position);
        tvDeadline.setText(job.getDeadline());

        ibtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ibtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                Job job = new Job();
                job= jobs.get(position);
                if (isChecked) {
                    job.setRank(1);
                } else {
                    job.setRank(0);
                }

                // Cập nhật giá trị Rank vào cơ sở dữ liệu
                MainActivity.db.updateJob(job);

                // Load lại danh sách công việc
                setData(MainActivity.db.getAllJobsSortedByStatusDescending(MainActivity.USER_ID));
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private void setData(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }
}
