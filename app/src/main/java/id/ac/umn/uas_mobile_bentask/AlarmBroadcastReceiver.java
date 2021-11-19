package id.ac.umn.uas_mobile_bentask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    String title, desc, date, time;
    @Override
    public void onReceive(Context context, Intent intent) {

        title = intent.getStringExtra("TITLE");
        desc = intent.getStringExtra("DESC");
        date = intent.getStringExtra("DATE");
        time = intent.getStringExtra("TIME");

        Intent i = new Intent(context, AlarmActivity.class);
        i.putExtra("TITLE", title);
        i.putExtra("DESC", desc);
        i.putExtra("DATE", date);
        i.putExtra("TIME", time);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
