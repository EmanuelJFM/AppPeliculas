package com.example.proyectofinal.Elementos;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.proyectofinal.databinding.ActivityLoginFormBinding;

public class LoginForm extends AppCompatActivity {
    ActivityLoginFormBinding login;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        login = ActivityLoginFormBinding.inflate(getLayoutInflater());
        setContentView(login.getRoot());

}
}