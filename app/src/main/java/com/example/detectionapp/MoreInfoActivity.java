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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
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

    TextView txtTitle, txtInformation;
    ImageSlider imgSlider;
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
                        confgSliderImage(title, information, url_img1, url_img2,url_img3);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                }
            };
            animalRef.addSnapshotListener(eventListener);
        }
    }

    private void confgSliderImage(String title, String info, String imgUrl1, String imgUrl2, String imgUrl3){
        imgSlider = findViewById(R.id.img_slider);
        txtTitle = findViewById(R.id.mi_title);
        txtInformation = findViewById(R.id.mi_information);

        txtTitle.setText(title);
        txtInformation.setText(info);

        ArrayList<SlideModel>sliderimgs = new ArrayList<>();
        sliderimgs.add(new SlideModel(imgUrl1,null));
        sliderimgs.add(new SlideModel(imgUrl2, null));
        sliderimgs.add(new SlideModel(imgUrl3, null));

        imgSlider.setImageList(sliderimgs, ScaleTypes.CENTER_CROP);
    }
}