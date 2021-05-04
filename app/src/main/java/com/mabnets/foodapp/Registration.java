package com.mabnets.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {


    EditText editTextFName,editTextLName,editTextPhone,editTextMail,editTextPassword,editTextConfirmPass;
    Button registerButton,loginButton;

    private ProgressBar progressBar;

    final String URLRegister="https://www.famatec.mabnets.com/foodapp/register.php?apicall=register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextConfirmPass=(EditText)findViewById(R.id.confirmPassword);
        editTextFName=(EditText)findViewById(R.id.Firstname);
        editTextPassword=(EditText)findViewById(R.id.userPassword);
        editTextPhone=(EditText)findViewById(R.id.userPhone);
        editTextMail=(EditText)findViewById(R.id.userMail);

        progressBar=(ProgressBar)findViewById(R.id.progressRegister);

        registerButton=(Button)findViewById(R.id.buttonRegisterUser);
        loginButton=(Button)findViewById(R.id.buttonLoginUser);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,Login.class));
                finish();
            }
        });
    }

    private void registerUser(){

        final String name=editTextFName.getText().toString().trim();
        final String phone=editTextPhone.getText().toString().trim();
        final String email=editTextMail.getText().toString().trim();
        final String pass=editTextPassword.getText().toString().trim();
        final String passw=editTextConfirmPass.getText().toString().trim();

        final String api="registration";

        if(name.isEmpty()){

            editTextFName.setError("Please enter your name");
            editTextFName.requestFocus();

        }else if(phone.isEmpty()){
            editTextPhone.setError("Enter your phone number");
            editTextPhone.requestFocus();
        }
        else if(phone.length()!=10) {
            editTextPhone.setError("Phone should be 10 digits");
            editTextPhone.requestFocus();
        }else if(email.isEmpty()){
            editTextMail.setError("Email is also required");
            editTextMail.requestFocus();
        }else if(pass.isEmpty()){
            editTextPassword.setError("Please make your account secure");
            editTextPassword.requestFocus();
        }
        else if(!pass.equals(passw)){

            editTextPassword.requestFocus();
            Toast.makeText(Registration.this, "Passwords mismatched", Toast.LENGTH_SHORT).show();
        }else{

            progressBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, URLRegister, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    progressBar.setVisibility(View.INVISIBLE);

                    //Toast.makeText(Registration.this, response, Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String message=jsonObject.getString("message");

                       // Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show();

                        if(message.trim().equals("Registration successful")) {

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Registration.this);
                            alertDialog.setMessage("Registration successful, Do you want to proceed to Login to your account?");
                            alertDialog.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    startActivity(new Intent(Registration.this, Login.class));
                                    Registration.this.finish();
                                }
                            });

                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                            alertDialog.create();
                            alertDialog.show();
                        }else{

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Registration.this);
                            alertDialog.setMessage(message.trim());
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();
                                }
                            });

                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                            alertDialog.create();
                            alertDialog.show();
                        }

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Toast.makeText(Registration.this, error.toString(), Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.INVISIBLE);

                    AlertDialog.Builder alert=new AlertDialog.Builder(Registration.this);
                    alert.setMessage("An error occurred, check your connection, Please try again");
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Registration.this,Login.class));
                        }
                    });
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    alert.create();
                    alert.show();
                }
            }){


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params=new HashMap<>();

                    params.put("firstname",name);
                    params.put("email",email);
                    params.put("phone",phone);
                    params.put("password",pass);



                    return  params;
                }

            };

            Volley.newRequestQueue(Registration.this).add(stringRequest);


        }

    }
}
