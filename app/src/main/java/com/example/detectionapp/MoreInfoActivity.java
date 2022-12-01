package com.example.detectionapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MoreInfoActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    List<Image> imageList;
    SliderAdapter sliderAdapter;
    TextView txtTitle, txtInformation;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();;

    /*String imgUrl1 = "https://static.mundoeducacao.uol.com.br/mundoeducacao/2022/01/onca-pintada.jpg";
    String imgUrl2 = "https://v5j9q4b5.rocketcdn.me/wp-content/uploads/2021/02/onca-pintada-saiba-tudo-sobre-o-felino-mais-poderoso-das-americas-960x679.jpg";
    String imgUrl3 = "https://greenbond.com.br/wp-content/uploads/2021/09/unnamed-1.jpg";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        DocumentReference animalRef = firestore.collection("animal").document("0");
        animalRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String information = document.getString("information");
                        String title = document.getString("name");
                        String url_img1 = document.getString("url_img1");
                        String url_img2 = document.getString("url_img2");
                        Log.d(TAG, "****INFORMACOES DO ANIMAL****" + information);
                        Log.d(TAG, "****URL****" + url_img1);
                        Log.d(TAG, "****URL****" + url_img2);
                        sliderImage(title, information, url_img1, url_img2);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    private void sliderImage(String title, String information, String imgUrl1, String imgUrl2){
        viewPager2 = findViewById(R.id.viewPager);
        txtTitle = findViewById(R.id.mi_title);
        txtInformation = findViewById(R.id.mi_information);

        txtTitle.setText(title);
        txtInformation.setText(information);

        imageList = new ArrayList<>();
        imageList.add(new Image(imgUrl1));
        imageList.add(new Image(imgUrl2));

        sliderAdapter = new SliderAdapter(imageList,this);
        viewPager2.setAdapter(sliderAdapter);
    }
}