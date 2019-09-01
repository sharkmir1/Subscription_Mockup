package com.jayjung.subscription_mockup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private NotificationManager notificationManager;

    private RecyclerView recyclerView;
    private NotiContainerAdapter notiContainerAdapter;
    private ArrayList<NotiContainer> notiContainerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // User Permission
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Button b = findViewById(R.id.test_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerNotification();
            }
        });

        // RecyclerView Logic for notification card view
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notiContainerArrayList = new ArrayList<>();
        notiContainerAdapter = new NotiContainerAdapter(this, notiContainerArrayList);
        recyclerView.setAdapter(notiContainerAdapter);

        notiContainerArrayList.add(new NotiContainer("LALALA Title", "LALALA HEHEHE", "Social"));
        notiContainerArrayList.add(new NotiContainer("정원이 면상", "쟈기 바보", "Love"));

        notiContainerAdapter.notifyDataSetChanged();
        // Code block below is for notification add card view
//        String[] channels = new String[] {"Channel 1", "Channel 2", "Test Channel"};
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                MainActivity.this,
//                R.layout.dropdown_menu_item,
//                channels);

//        AutoCompleteTextView channelDropDown = findViewById(R.id.channel_dropdown);
//        channelDropDown.setAdapter(adapter);


    }


    public void triggerNotification() {
        PendingIntent snoozeIntent =
                PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                MainActivity.this, generateChannelId())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                .setContentTitle("Test Content Title")
                .setContentText("Test Content Text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(snoozeIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            String channelName = "Test Noti Channel";
            String description = "This channel is for test.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(generateChannelId(), channelName, importance);
            channel.setDescription(description);

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }

        notificationManager.notify(1234, builder.build());
    }

    private String generateChannelId() {
        return "10001";
    }
}
