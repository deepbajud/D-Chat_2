package com.example.d_chat2bajud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;

import com.example.d_chat2bajud.model.UserModel;
import com.example.d_chat2bajud.utils.AndroidUtil;
import com.example.d_chat2bajud.utils.FirebaseUtil;


public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setStatusBarColor(getResources().getColor(R.color.my_primary));


        if(getIntent().getExtras()!=null){
            //from notification
            String userId = getIntent().getExtras().getString("userId");
            FirebaseUtil.allUserCollectionReference().document(userId).get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            UserModel model = task.getResult().toObject(UserModel.class);

                            Intent mainIntent = new Intent(this,MainActivity.class);
                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(mainIntent);

                            Intent intent = new Intent(this, MainActivity.class);
                            AndroidUtil.passUserModelAsIntent(intent,model);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });


        }else{
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    if(FirebaseUtil.isLoggedIn()){
                        startActivity(new Intent(Splash.this,MainActivity.class));
                    }else{
                        startActivity(new Intent(Splash.this, LoginPhoneNumber.class));
                    }
                    finish();
                }
            },1000);
        }
    }
}