package com.examplmakecodeeasy.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.examplmakecodeeasy.twitterclone.databinding.ActivityMainBinding;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ParseUser.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);


        }


        binding.Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            ParseUser appUser = new ParseUser();
          appUser.setUsername(binding.edtUserSignUp.getText().toString());
        appUser.setPassword(binding.edtPasswordSignUp.getText().toString());
        appUser.setEmail(binding.edtSignUPemail.getText().toString());


        appUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    FancyToast.makeText(MainActivity.this, appUser.get("username")+"signUp Suceesfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(intent);
                }else
                {
                    FancyToast.makeText(MainActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
            }
        });

            }
        });

    }
}