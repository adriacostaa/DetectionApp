package com.example.detectionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MoreInfoActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    List<Image> imageList;
    SliderAdapter sliderAdapter;

    String imgUrl1 = "https://static.mundoeducacao.uol.com.br/mundoeducacao/2022/01/onca-pintada.jpg";
    String imgUrl2 = "https://v5j9q4b5.rocketcdn.me/wp-content/uploads/2021/02/onca-pintada-saiba-tudo-sobre-o-felino-mais-poderoso-das-americas-960x679.jpg";
    String imgUrl3 = "https://greenbond.com.br/wp-content/uploads/2021/09/unnamed-1.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        viewPager2 = findViewById(R.id.viewPager);

        imageList = new ArrayList<>();
        imageList.add(new Image(imgUrl1));
        imageList.add(new Image(imgUrl2));
        imageList.add(new Image(imgUrl3));

        sliderAdapter = new SliderAdapter(imageList,this);
        viewPager2.setAdapter(sliderAdapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
}