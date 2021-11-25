package id.ac.umn.uas_mobile_bentask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateTaskActivity extends AppCompatActivity {
    EditText task_input,task_desc;
    Button update_task_button;
    String task_id,task_title,task_descr,id,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        task_input = findViewById(R.id.task_input2);
        task_desc = findViewById(R.id.desc_input2);
        update_task_button = findViewById(R.id.update_task_button);
        update_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateTaskActivity.this);
                task_title = task_input.getText().toString().trim();
                task_descr = task_desc.getText().toString().trim();
                myDB.updateDataTask(task_id, task_title,task_descr);
                Intent intent = new Intent(UpdateTaskActivity.this, TaskActivity.class);
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
        if(getIntent().hasExtra("task_id") && getIntent().hasExtra("task_title") && getIntent().hasExtra("task_desc")){
            task_id = getIntent().getStringExtra("task_id");
            task_title = getIntent().getStringExtra("task_title");
            task_descr = getIntent().getStringExtra("task_desc");
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            task_input.setText(task_title);
            task_desc.setText(task_descr);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}