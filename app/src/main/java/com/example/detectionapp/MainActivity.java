package com.example.detectionapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    private View btnCamera, btnInfo, btnListar;
    private TextView txtListar;
    private boolean rotate = false;
    private static final int pic_id = 1;
    private FirebaseFirestore firestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        final FloatingActionButton fabCam = findViewById(R.id.fab_cam);
        final FloatingActionButton fabInfo = findViewById(R.id.fab_info);
        final FloatingActionButton fabAdd = findViewById(R.id.fab_add);

        btnCamera = findViewById(R.id.btn_camera);
        btnInfo = findViewById(R.id.btn_info);
        btnListar = findViewById(R.id.btn_listar);
        txtListar = findViewById(R.id.txt_listar);

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
                sendImage(view);
            }
        });

        fabInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Informacoes sobre o App", Toast.LENGTH_SHORT).show();
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Listar", Toast.LENGTH_SHORT).show();
                retornarDados();

            }
        });
    }

    void retornarDados(){
        firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("animal").document("01");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String informativo = String.valueOf(document.getData());
                        txtListar.setText(informativo);
                        Log.d(TAG, "DocumentSnapshot data: " + informativo);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendImage(View view){
        // Launch camera if we have permission
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, pic_id);

        } else {
            //Request camera permission if we don't have it.
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == pic_id && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("image", image);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}