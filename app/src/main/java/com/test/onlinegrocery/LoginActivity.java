package com.test.onlinegrocery;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.test.onlinegrocery.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        TextView txt_signup=findViewById(R.id.txt_signup);
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (LoginActivity.this,SignupActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailEditText.getText().toString().equals("")|| passwordEditText.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"Kindly Input all fields",Toast.LENGTH_LONG).show();
                }
                else{
                    RetroInterface mApiService = this.getInterfaceService();

                    Call<String> responseBodyCall = mApiService.Login(emailEditText.getText().toString(),passwordEditText.getText().toString());

                    responseBodyCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            if(response.body().contains("logged")){
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(LoginActivity.this, response.body(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }

            private RetroInterface getInterfaceService() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.2.27:8080")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final RetroInterface mInterfaceService = retrofit.create(RetroInterface.class);
                return mInterfaceService;
            }
        });
    }






}