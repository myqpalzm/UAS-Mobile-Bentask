package id.ac.umn.uas_mobile_bentask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DetailTaskActivity extends AppCompatActivity {

    TextView task_input2,task_desc2,task_date2, task_time2;
    Button submit_task_button;
    String task_id2,task_title2,task_descr2,task_dates2, task_times2,id,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        task_input2 = findViewById(R.id.task_input3);
        task_desc2 = findViewById(R.id.desc_input3);
        task_date2 = findViewById(R.id.date_input3);
        task_time2 = findViewById(R.id.time_input3);
        submit_task_button = findViewById(R.id.submit_task_button);
        submit_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailTaskActivity.this, TaskActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        getAndSetIntentData();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("task_id") && getIntent().hasExtra("task_title") && getIntent().hasExtra("task_desc")&& getIntent().hasExtra("task_date") && getIntent().hasExtra("task_time")){
            task_id2 = getIntent().getStringExtra("task_id");
            task_title2 = getIntent().getStringExtra("task_title");
            task_descr2 = getIntent().getStringExtra("task_desc");
            task_dates2 = getIntent().getStringExtra("task_date");
            task_times2 = getIntent().getStringExtra("task_time");
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            task_input2.setText(task_title2);
            task_desc2.setText(task_descr2);
            task_date2.setText(task_dates2);
            task_time2.setText(task_times2);
        }else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}