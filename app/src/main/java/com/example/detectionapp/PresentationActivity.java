package com.example.detectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PresentationActivity extends AppCompatActivity {

    private View backDrop, btnCamera, btnInfo;
    private boolean rotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        backDrop = findViewById(R.id.back);

        final FloatingActionButton fabCam = findViewById(R.id.fab_cam);
        final FloatingActionButton fabInfo = findViewById(R.id.fab_info);
        final FloatingActionButton fabAdd = findViewById(R.id.fab_add);

        btnCamera = findViewById(R.id.btn_camera);
        btnInfo = findViewById(R.id.btn_info);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}