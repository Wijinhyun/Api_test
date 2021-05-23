package com.prodoc.api_test;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class StartActivity extends AppCompatActivity {

    TextView t1;
    LinearLayout linear;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

//        t1 = findViewById(R.id.start_text);
//        linear = findViewById(R.id.start_linear);
//        Animation c = AnimationUtils.loadAnimation(StartActivity.this,R.anim.alphaex);
//        linear.startAnimation(c);

        animationView = (LottieAnimationView) findViewById(R.id.start_lottie);
        animationView.playAnimation();

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animationView.setVisibility(View.INVISIBLE);
                Intent intent1 = new Intent(getApplicationContext(), MainActivity_Ver2.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
                finish();
                overridePendingTransition(0,0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        /*
        c.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                t1.setText("");
                Intent intent1 = new Intent(getApplicationContext(), MainActivity_Ver2.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
                finish();
                overridePendingTransition(0,0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
*/


    }
}
