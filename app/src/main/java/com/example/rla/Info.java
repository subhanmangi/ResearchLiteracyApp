package com.example.rla;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Info extends AppCompatActivity {

    VideoView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        player = findViewById(R.id.player);

        String uriPath = "android.resource://"+getPackageName()+"/"+R.raw.rla;
        Uri uri = Uri.parse(uriPath);
        player.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(player);
        player.setMediaController(mediaController);
    }
}