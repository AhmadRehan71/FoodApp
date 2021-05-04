package com.mabnets.foodapp.model;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Appx extends AppCompatActivity {
final String URLApp="https://www.famatec.mabnets.com/foodapp/app.php";

    public void checkAppInfo(){

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URLApp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        Volley.newRequestQueue(Appx.this).add(stringRequest);

    }
}
