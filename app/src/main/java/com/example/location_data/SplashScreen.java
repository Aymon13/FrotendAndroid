package com.example.location_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {

    ImageView img1,img2;
    Animation top,bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_design);

        img1=(ImageView)findViewById(R.id.Logo);
        img2=(ImageView)findViewById(R.id.SubLogo);
        Glide.with(this).load(R.drawable.loc2).into(img1);


        top= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mainlogoanimation);
        top= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sublogoanimation);

        img1.setAnimation(top);
        img2.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),phone_authentication.class));
            }
        },5000);  //7100 for loc
    }
}