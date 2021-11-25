package id.ac.umn.uas_mobile_bentask;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.MyViewHolder> implements Filterable {
    private Context context;
    private Activity activity;
    private ArrayList<String> task_id,task_name,task_desc;
    private ArrayList<String> taskList;
    String category_id;
    AdapterTask(Activity activity, Context context, ArrayList<String> task_id, ArrayList<String> task_name, ArrayList<String> task_desc, String category_id){
        this.activity = activity;
        this.context = context;
        this.task_id  = task_id;
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.category_id = category_id;
        taskList = new ArrayList<>();
        taskList.addAll(task_name);
        taskList.addAll(task_id);
        taskList.addAll(task_desc);
        taskList.add(category_id);
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

    @Override
    public Filter getFilter() {
        return taskFilter;
    }
    private Filter taskFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<String> filteredList = new ArrayList<>();
            if(keyword == null || keyword.length() == 0){
                filteredList.addAll(taskList);
            }else{
                String filter = keyword.toString().toLowerCase().trim();
                for(String dataTask : taskList){
                    if(dataTask.toLowerCase().contains(filter)){
                        filteredList.add(dataTask);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            task_name.clear();
            task_id.clear();
            task_desc.clear();
            task_name.addAll((Collection<? extends String>) results.values);
            task_id.addAll((Collection<? extends String>) results.values);
            task_desc.addAll((Collection<? extends String>) results.values);

            notifyDataSetChanged();
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView task_id_txt, task_name_txt;
        LinearLayout taskLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_id_txt = itemView.findViewById(R.id.task_id_txt);
            task_name_txt = itemView.findViewById(R.id.task_name_txt);
            taskLayout = itemView.findViewById(R.id.taskLayout);
        }
    }

}
