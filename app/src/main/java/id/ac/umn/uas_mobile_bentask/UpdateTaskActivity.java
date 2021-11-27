package id.ac.umn.uas_mobile_bentask;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateTaskActivity extends AppCompatActivity {
    EditText task_input,task_desc,task_date;
    Button update_task_button,delete_task_button;
    String task_id,task_title,task_descr,task_dates,id,title;
    ImageView cal2;
    private int mDate2,mMonth2,mYear2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        task_input = findViewById(R.id.task_input2);
        task_desc = findViewById(R.id.desc_input2);
        task_date = findViewById(R.id.date_input2);
        cal2 = findViewById(R.id.datepicker2);
        update_task_button = findViewById(R.id.update_task_button);
        delete_task_button = findViewById(R.id.delete_task_button);
        update_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateTaskActivity.this);
                task_title = task_input.getText().toString().trim();
                task_descr = task_desc.getText().toString().trim();
                task_dates = task_date.getText().toString().trim();
                myDB.updateDataTask(task_id, task_title,task_descr,task_dates);
                Intent intent = new Intent(UpdateTaskActivity.this, TaskActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
        cal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();
                mDate2 = cal.get(Calendar.DATE);
                mMonth2 = cal.get(Calendar.MONTH);
                mYear2 = cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTaskActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        task_date.setText(date+"-"+month+"-"+year);
                    }
                },mYear2,mMonth2,mDate2);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        delete_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateTaskActivity.this);
                myDB.deleteTask(task_id);
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
        if(getIntent().hasExtra("task_id") && getIntent().hasExtra("task_title") && getIntent().hasExtra("task_desc")&& getIntent().hasExtra("task_date")){
            task_id = getIntent().getStringExtra("task_id");
            task_title = getIntent().getStringExtra("task_title");
            task_descr = getIntent().getStringExtra("task_desc");
            task_dates = getIntent().getStringExtra("task_date");
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            task_input.setText(task_title);
            task_desc.setText(task_descr);
            task_date.setText(task_dates);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}