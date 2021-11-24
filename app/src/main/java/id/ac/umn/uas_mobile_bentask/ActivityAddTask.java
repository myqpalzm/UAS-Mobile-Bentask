package id.ac.umn.uas_mobile_bentask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityAddTask extends AppCompatActivity {

    EditText task_input,desc_input;
    Button add_task_button;
    String id,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        task_input = findViewById(R.id.task_input);
        desc_input = findViewById(R.id.desc_input);
        add_task_button = findViewById(R.id.add_task_button);
        add_task_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(ActivityAddTask.this);
                myDB.addTask(task_input.getText().toString().trim(),desc_input.getText().toString().trim(),id);
                Intent intent = new Intent(ActivityAddTask.this, TaskActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        getAndSetIntentData();
    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}