package com.yourstar.cenotomy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yourstar.cenotomy.Activities.ViewPost;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Share extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, intent.getStringExtra("Link"));
        sendIntent.setType("text/plain");
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (sendIntent.resolveActivity(context.getPackageManager()) != null) {
            Intent new_intent = Intent.createChooser(sendIntent, "Share via");
            new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(new_intent);
        } else {
            Toast.makeText(context, "Cant Share the image", Toast.LENGTH_SHORT).show();
        }
    }
}
