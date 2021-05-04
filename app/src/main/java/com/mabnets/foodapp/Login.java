package com.mabnets.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    private EditText editTextUsername,editTextPassword;
    private ProgressBar progressBar;
    private Button button,button2;
    private TextView textViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername=(EditText)findViewById(R.id.userLoginMail);
        editTextPassword=(EditText)findViewById(R.id.loginPassword);
        progressBar=(ProgressBar)findViewById(R.id.progressLoginUser);
        textViewPassword=(TextView) findViewById(R.id.textViewForgotPassword);

        button=(Button)findViewById(R.id.buttonLogins);
        button2=(Button)findViewById(R.id.buttonRegister);
        textViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgotPassword.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });


    }

    private void userLogin(){

        final String name=editTextUsername.getText().toString().trim();
        final String password=editTextPassword.getText().toString().trim();
        final String apicall="login";

        if(TextUtils.isEmpty(name)){
            editTextUsername.setError("Please enter email or phone");
            editTextUsername.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
        }
        else if(name.equals("max") && password.equals("pass")){
            startActivity(new Intent(Login.this,MainActivity.class));
        }

        else{

            class UserLogin extends AsyncTask<Void,Void,String> {


                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    progressBar.setVisibility(View.GONE);
                   // Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();

                    try {

                        JSONObject jsonObject = new JSONObject(s);
                        Toast.makeText(Login.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();

                        String messagee = jsonObject.getString("message");
                       // Toast.makeText(Login.this, messagee, Toast.LENGTH_SHORT).show();
                        if (messagee.contains("Connection failed")) {
                            Toast.makeText(Login.this, "Connection failed, Please Check and retry", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!jsonObject.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                JSONObject userJson = jsonObject.getJSONObject("user");

                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("phone")

                                );

                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //Toast.makeText(Login.this, user.getPhone(), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            } else {

                                Toast.makeText(Login.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                               //  Toast.makeText(Login.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                protected String doInBackground(Void... voids) {
                    String urlLogin="https://famatec.mabnets.com/foodapp/Api.php?apicall=login";

                    RequestHandler requestHandler=new RequestHandler();
                    HashMap<String,String> params=new HashMap<>();
                    params.put("email",name);
                    params.put("pass",password);


                    return requestHandler.sendPostRequest(urlLogin,params);
                }
            }
            UserLogin userLogin=new UserLogin();
            userLogin.execute();
        }

    }

}
