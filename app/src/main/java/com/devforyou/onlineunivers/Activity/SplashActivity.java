package com.devforyou.onlineunivers.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devforyou.onlineunivers.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;
import com.hanks.htextview.fade.FadeTextView;
import com.hanks.htextview.line.LineTextView;
import com.hanks.htextview.typer.TyperTextView;


public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TyperTextView fadeTextView;
    private  Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_activity);

        fadeTextView = findViewById(R.id.line);

        mAuth = FirebaseAuth.getInstance();
        context = this;
        FirebaseUser user = mAuth.getCurrentUser();

fadeTextView.animate();

        fadeTextView.setAnimationListener(hTextView -> {
            Intent intent;
            if(user==null){

                intent = new Intent(context,AuthGoogleActivity.class);
            }else {

                intent = new Intent(context,MainActivity.class);
            }

            startActivity(intent);
            finish();
        });
      fadeTextView.animateText("EzTuT - сделанно для тебя");


    }
}
