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

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ActivityAddTask extends AppCompatActivity {

    EditText task_input,desc_input,date_input,time_input;
    Button add_task_button;
    String id,title;
    ImageView cal;
    ImageView alarm;
    private int mDate,mMonth,mYear,t1Hour,t1Minute;
    private Calendar fullCalendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        task_input = findViewById(R.id.task_input);
        desc_input = findViewById(R.id.desc_input);
        date_input = findViewById(R.id.date_input);
        cal = findViewById(R.id.datepicker);
        time_input = findViewById(R.id.time_input);
        alarm = findViewById(R.id.timepicker);
        add_task_button = findViewById(R.id.add_task_button);
        createNotificationChannel();
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();
                mDate = cal.get(Calendar.DATE);
                mMonth = cal.get(Calendar.MONTH);
                mYear = cal.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAddTask.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        fullCalendar.set(Calendar.YEAR,year);
                        fullCalendar.set(Calendar.MONTH,(month+1));
                        fullCalendar.set(Calendar.DATE,date);
                        date_input.setText(date+"-"+(month+1)+"-"+year);
                    }
                },mYear,mMonth,mDate);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        alarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityAddTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        t1Hour = hourOfDay;
                        t1Minute = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,t1Hour, t1Minute);
                        fullCalendar.set(Calendar.HOUR_OF_DAY, t1Hour);
                        fullCalendar.set(Calendar.MINUTE, t1Minute);
                        time_input.setText(DateFormat.format("hh:mm aa", calendar));
                    }
                }, 12, 0, false

                );
                timePickerDialog.updateTime(t1Hour, t1Minute);
                timePickerDialog.show();
            }
        });
        add_task_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(ActivityAddTask.this);
                myDB.addTask(task_input.getText().toString().trim(),desc_input.getText().toString().trim(),date_input.getText().toString().trim(), time_input.getText().toString().trim(),id);
                Intent intent = new Intent(ActivityAddTask.this, TaskActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("title",title);
                startActivity(intent);
                if(time_input != null){
                    setAlarm();
                }
            }
        });
        getAndSetIntentData();
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
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
}