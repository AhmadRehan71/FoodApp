package com.mabnets.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity {

    private EditText editTextEmail,editTextCode;
    private Button buttonSendCode,buttonResetCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail=(EditText)findViewById(R.id.emailReset);
        editTextCode=(EditText)findViewById(R.id.code);

        buttonSendCode=(Button)findViewById(R.id.buttonRequestCode);
        buttonResetCode=(Button)findViewById(R.id.buttonReset);




    }
}
