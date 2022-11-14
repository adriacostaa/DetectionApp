package com.example.detectionapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PresentationActivity extends AppCompatActivity {

    private View btnCamera, btnInfo;
    private boolean rotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        final FloatingActionButton fabCam = findViewById(R.id.fab_cam);
        final FloatingActionButton fabInfo = findViewById(R.id.fab_info);
        final FloatingActionButton fabAdd = findViewById(R.id.fab_add);

        btnCamera = findViewById(R.id.btn_camera);
        btnInfo = findViewById(R.id.btn_info);

        ViewAnimation.initShowOut(btnCamera);
        ViewAnimation.initShowOut(btnInfo);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFabMode(view);
            }
        });

        fabCam.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        fabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Informacoes sobre o App", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleFabMode(View v){
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if(rotate){
            ViewAnimation.ShowIn(btnCamera);
            ViewAnimation.ShowIn(btnInfo);
        }else{
            ViewAnimation.ShowOut(btnCamera);
            ViewAnimation.ShowOut(btnInfo);
        }
    }
}