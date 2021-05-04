package com.mabnets.foodapp;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class OrderFood extends AppCompatActivity {

    ImageView imageView;
    TextView textView1,textView2,textView3;
    Button button;

    String name,image,price,foodid;
    String phone,location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(this, Login.class));
            finish();
        }else{

            User user=SharedPrefManager.getInstance(this).getUser();
            phone=user.getPhone();
        }

        imageView=(ImageView)findViewById(R.id.orderImage);
        textView1=(TextView)findViewById(R.id.orderedFood);
        textView2=(TextView)findViewById(R.id.orderedAmount);
        textView3=(TextView)findViewById(R.id.receipt);
        button=(Button)findViewById(R.id.buttonOrder);

        Bundle bundle=getIntent().getExtras();

        if(bundle !=null){

             image=bundle.getString("image");
             name=bundle.getString("food");
             price=bundle.getString("price");
             foodid=bundle.getString("foodid");
             location=bundle.getString("location");


          //  Toast.makeText(this, location, Toast.LENGTH_SHORT).show();
          //  Toast.makeText(this, foodid, Toast.LENGTH_SHORT).show();


            //Toast.makeText(this, price, Toast.LENGTH_SHORT).show();

            Glide.with(this)
                    .load(image)
                    .into(imageView);

            textView2.setText("AED"+price);
            textView1.setText(name);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    generateReceipt();
                }
            });



        }else{

            Toast.makeText(this, "No data available for this item", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateReceipt(){

        final String URLOrder="https://www.famatec.mabnets.com/foodapp/order.php";

        textView3.setText("YOUR ORDER RECEIPT\n\n\n\n"+
                "\n"+"FOOD\t\t"+ name +"\n\n"+"PRICE\t\t"+price+"\n\n"+"");

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URLOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




                            startActivity(new Intent(OrderFood.this,FeedBack.class));
                            finish();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            //  Toast.makeText(OrderFood.this, error.toString(), Toast.LENGTH_SHORT).show();
                androidx.appcompat.app.AlertDialog.Builder builder=new AlertDialog.Builder(OrderFood.this);
                builder.setMessage("Error, Please check your internet connection");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                builder.create();
                builder.show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> params=new HashMap<>();

                User user=SharedPrefManager.getInstance(OrderFood.this).getUser();
                phone=user.getPhone();
                Bundle bundle=getIntent().getExtras();

                if(bundle !=null) {

                    image = bundle.getString("image");
                    name = bundle.getString("food");
                    price = bundle.getString("price");
                    foodid = bundle.getString("foodid");
                    location = bundle.getString("location");
                }

                params.put("foodid",foodid);
                params.put("customer",phone);
                params.put("location",location);
                return params;

            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }
}
