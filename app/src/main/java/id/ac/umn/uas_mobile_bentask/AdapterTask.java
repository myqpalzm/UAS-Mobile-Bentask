package id.ac.umn.uas_mobile_bentask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList task_id,task_name,task_desc;
    String category_id;

    AdapterTask(Activity activity, Context context, ArrayList task_id, ArrayList task_name,String category_id){
        this.activity = activity;
        this.context = context;
        this.task_id  = task_id;
        this.task_name = task_name;
        this.category_id = category_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_task_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.task_name_txt.setText(String.valueOf(task_name.get(position)));
    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView task_id_txt, task_name_txt,task_desc_txt;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_id_txt = itemView.findViewById(R.id.task_id_txt);
            task_name_txt = itemView.findViewById(R.id.task_name_txt);
        }

    }
}
