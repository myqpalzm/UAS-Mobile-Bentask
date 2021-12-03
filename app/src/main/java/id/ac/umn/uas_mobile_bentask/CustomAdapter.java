package id.ac.umn.uas_mobile_bentask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList category_id, category_name;

    CustomAdapter(Activity activity, Context context, ArrayList category_id, ArrayList category_name){
        this.activity = activity;
        this.context = context;
        this.category_id = category_id;
        this.category_name = category_name;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.category_name_txt.setText(String.valueOf(category_name.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskActivity.class);
                intent.putExtra("id",String.valueOf(category_id.get(position)));
                intent.putExtra("title",String.valueOf(category_name.get(position)));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return category_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView category_id_txt, category_name_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            category_id_txt = itemView.findViewById(R.id.category_id_txt);
            category_name_txt = itemView.findViewById(R.id.category_name_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }

    }
}
