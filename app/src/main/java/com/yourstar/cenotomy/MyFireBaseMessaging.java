package com.yourstar.cenotomy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.prof.rssparser.Article;
import com.yourstar.cenotomy.Activities.Startup;
import com.yourstar.cenotomy.Activities.ViewPost;
import com.youstar.bloggerssport.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class MyFireBaseMessaging extends FirebaseMessagingService {
    private Bitmap bitmap;
    Startup startup;
    private String Title;
    private String link;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
         startup = (Startup) getApplicationContext();
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        //The message which i send will have keys named [message, image, AnotherActivity] and corresponding values.
        //You can change as per the requirement.

        //message will contain the Push Message
        String message = remoteMessage.getData().get("message");
        //imageUri will contain URL of the image to be displayed with Notification
        String imageUri = remoteMessage.getData().get("image");
        Title = remoteMessage.getData().get("title");
        //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
        //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.
      link = remoteMessage.getData().get("link");
      Startup startup  = (Startup) getApplicationContext();
      Article article  =new Article();
      article.setLink(link);
        startup.setArticle(article);
        bitmap = getBitmapfromUrl(imageUri);

        sendNotification(message);

    }

    /**
     * Create and show a simple notification containing the received FCM message.
     */

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, ViewPost.class);
        intent.putExtra("link",link);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MyFireBaseMessaging.this)
                .setSmallIcon(R.mipmap.glasses)
                .setContentTitle(Title)
                .setContentText(messageBody)
                .setLargeIcon(bitmap)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setChannelId("blogger");

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("blogger", "Blogger", NotificationManager.IMPORTANCE_HIGH));
        }

        notificationManager.notify(0 , notificationBuilder.build());
    }
    /*
     *To get a Bitmap image from the URL received
     * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
