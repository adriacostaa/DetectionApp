package com.example.detectionapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class MoreInfoActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    List<Image> imageList;
    SliderAdapter sliderAdapter;
    TextView txtTitle, txtInformation;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Bundle extras = getIntent().getExtras();
        String value;
        if (extras != null) {
            value = extras.getString("position");
            Log.d(TAG, "****VALOR POSICAO****" + value);

            DocumentReference animalRef = firestore.collection("animal").document(value);
            EventListener eventListener = new EventListener<DocumentSnapshot>(){

                @Override
                public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {
                    if (document.exists()) {
                        String information = document.getString("information");
                        String title = document.getString("name");
                        String url_img1 = document.getString("url_img1");
                        String url_img2 = document.getString("url_img2");
                        String url_img3 = document.getString("url_img3");

                        Log.d(TAG, "****INFORMACOES DO ANIMAL****" + information);
                        Log.d(TAG, "****URL****" + url_img1);
                        Log.d(TAG, "****URL****" + url_img2);
                        Log.d(TAG, "****URL****" + url_img3);
                        confgSliderImage(title, information, url_img1, url_img2,url_img3);

                    } else {
                        Log.d(TAG, "No such document");
                    }
                }
            };
            animalRef.addSnapshotListener(eventListener);
        }
    }

    private void confgSliderImage(String title, String information, String imgUrl1, String imgUrl2, String imgUrl3){
        viewPager2 = findViewById(R.id.viewPager);
        txtTitle = findViewById(R.id.mi_title);
        txtInformation = findViewById(R.id.mi_information);

        txtTitle.setText(title);
        txtInformation.setText(information);

        imageList = new ArrayList<>();
        imageList.add(new Image(imgUrl1));
        imageList.add(new Image(imgUrl2));
        imageList.add(new Image(imgUrl3));

        sliderAdapter = new SliderAdapter(imageList,this);
        viewPager2.setAdapter(sliderAdapter);
    }
}