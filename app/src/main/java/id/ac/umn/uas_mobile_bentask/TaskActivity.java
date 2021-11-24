package id.ac.umn.uas_mobile_bentask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_task_button;
    String id,title;
    AdapterTask adapterTask;

    MyDatabaseHelper myDB;
    ArrayList<String> task_id,task_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        recyclerView = findViewById(R.id.recyclerView);
        add_task_button = findViewById(R.id.add_task_button);
        add_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, ActivityAddTask.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        myDB = new MyDatabaseHelper(TaskActivity.this);
        task_id = new ArrayList<>();
        task_name = new ArrayList<>();
        getAndSetIntentData();
        storeDataInArrays();
        adapterTask = new AdapterTask(TaskActivity.this,this,task_id,task_name,id);
        recyclerView.setAdapter(adapterTask);
        recyclerView.setLayoutManager(new LinearLayoutManager(TaskActivity.this));
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }
    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    void storeDataInArrays() {
        Cursor cursor = myDB.readTasks(id);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_name.add(cursor.getString(1));
            }
        }
    }
}