package id.ac.umn.uas_mobile_bentask;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class UpdateTaskActivity extends AppCompatActivity {
    EditText task_input,task_desc,task_date, task_time;
    Button update_task_button,delete_task_button;
    String task_id,task_title,task_descr,task_dates,task_times,id,title;
    ImageView cal2, alarm2;
    private int mDate2,mMonth2,mYear2,t1Hour2,t1Minute2;
    private Calendar fullCalendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        task_input = findViewById(R.id.task_input2);
        task_desc = findViewById(R.id.desc_input2);
        task_date = findViewById(R.id.date_input2);
        task_time = findViewById(R.id.time_input2);
        cal2 = findViewById(R.id.datepicker2);
        alarm2 = findViewById(R.id.timepicker2);
        update_task_button = findViewById(R.id.update_task_button);
        delete_task_button = findViewById(R.id.delete_task_button);
        createNotificationChannel();
        update_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateTaskActivity.this);
                task_title = task_input.getText().toString().trim();
                task_descr = task_desc.getText().toString().trim();
                task_dates = task_date.getText().toString().trim();
                task_times = task_time.getText().toString().trim();
                myDB.updateDataTask(task_id, task_title,task_descr,task_dates,task_times);
                Intent intent = new Intent(UpdateTaskActivity.this, TaskActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                startActivity(intent);
                if(task_times != null){
                    setAlarm();
                }
            }
        });
        cal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDate2 = fullCalendar.get(Calendar.DATE);
                mMonth2 = fullCalendar.get(Calendar.MONTH);
                mYear2 = fullCalendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateTaskActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        fullCalendar.set(Calendar.YEAR,year);
                        fullCalendar.set(Calendar.MONTH,month);
                        fullCalendar.set(Calendar.DATE,date);
                        task_date.setText(date+"-"+(month+1)+"-"+year);
                    }
                },mYear2,mMonth2,mDate2);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        alarm2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t1Hour2 = hourOfDay;
                        t1Minute2 = minute;
                        fullCalendar.set(fullCalendar.get(Calendar.YEAR),fullCalendar.get(Calendar.MONTH),fullCalendar.get(Calendar.DATE),t1Hour2, t1Minute2, 0);
                        task_time.setText(DateFormat.format("hh:mm aa", fullCalendar));
                    }
                }, 12, 0, false

                );
                timePickerDialog.updateTime(t1Hour2, t1Minute2);
                timePickerDialog.show();
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

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, fullCalendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "BentaskNotification";
            String description = "Channel for Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Bentask", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("task_id") && getIntent().hasExtra("task_title") && getIntent().hasExtra("task_desc")&& getIntent().hasExtra("task_date")){
            task_id = getIntent().getStringExtra("task_id");
            task_title = getIntent().getStringExtra("task_title");
            task_descr = getIntent().getStringExtra("task_desc");
            task_dates = getIntent().getStringExtra("task_date");
            task_times = getIntent().getStringExtra("task_time");
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            task_input.setText(task_title);
            task_desc.setText(task_descr);
            task_date.setText(task_dates);
            task_time.setText(task_times);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}