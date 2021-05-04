package com.mabnets.foodapp.ui.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mabnets.foodapp.R;
import com.mabnets.foodapp.adapter.FoodAdapter;
import com.mabnets.foodapp.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private OrderViewModel galleryViewModel;

    List<Food> foods;
    List<Food> foodList;
    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    FoodAdapter foodAdapter2;

    EditText editTextSearch;
    ImageView imageView;

    ProgressBar progressBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        progressBar=(ProgressBar)root.findViewById(R.id.progressLoadFood);

        foods=new ArrayList<>();
        foodList=new ArrayList<>();
        recyclerView=(RecyclerView)root.findViewById(R.id.recyclerViewFoods);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        editTextSearch=(EditText)root.findViewById(R.id.editSearch);
        imageView=(ImageView)root.findViewById(R.id.imageSearch);


        getFoodStuffs();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFood();
            }
        });

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    private void getFoodStuffs(){

        progressBar.setVisibility(View.VISIBLE);
        //Toast.makeText(getContext(), "tee", Toast.LENGTH_SHORT).show();

        final String foodURL="https://www.famatec.mabnets.com/foodapp/foods.php";


        StringRequest stringRequest=new StringRequest(Request.Method.GET, foodURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);

                try {
                    JSONArray foodArray=new JSONArray(response);

                    for (int i=0;i<foodArray.length();i++){

                        JSONObject foodObject=foodArray.getJSONObject(i);

                        int id=foodObject.getInt("id");
                        String name=foodObject.getString("name");
                        String price=foodObject.getString("price");
                        String location=foodObject.getString("location");
                        String image=foodObject.getString("photo");

                        //Toast.makeText(getContext(), image, Toast.LENGTH_SHORT).show();
                       String imageURL="https://www.famatec.mabnets.com/foodapp/images/"+image;

                        Food food=new Food(id,name,imageURL,price,location);
                        foods.add(food);
                        foodAdapter=new FoodAdapter(getContext(),foods);
                        recyclerView.setAdapter(foodAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                alert.setMessage("Error! Please check your internet connection");
                alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFoodStuffs();
                    }
                });

                alert.create();
                alert.show();
            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    private void searchFood()
    {


        //Toast.makeText(getContext(), "tee", Toast.LENGTH_SHORT).show();
        final String search=editTextSearch.getText().toString().trim();

        if(TextUtils.isEmpty(search)){
            editTextSearch.requestFocus();

        }else {

            progressBar.setVisibility(View.VISIBLE);
            final String foodURLs = "https://www.famatec.mabnets.com/foodapp/search.php?q=" + search;


            StringRequest stringRequest = new StringRequest(Request.Method.GET, foodURLs, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                  //  Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);

                    try {
                        JSONArray foodArray = new JSONArray(response);

                        for (int i = 0; i < foodArray.length(); i++) {

                            JSONObject foodObject = foodArray.getJSONObject(i);

                            int id = foodObject.getInt("id");
                            String name = foodObject.getString("name");
                            String price = foodObject.getString("price");
                            String location = foodObject.getString("location");
                            String image = foodObject.getString("photo");

                            //Toast.makeText(getContext(), image, Toast.LENGTH_SHORT).show();
                            String imageURL = "https://www.famatec.mabnets.com/foodapp/images/" + image;

                            Food food = new Food(id, name, imageURL, price, location);
                            foodList.add(food);
                            foodAdapter2 = new FoodAdapter(getContext(), foodList);
                            recyclerView.setAdapter(foodAdapter2);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage("Error! Please check your internet connection");
                    alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getFoodStuffs();
                        }
                    });

                    alert.create();
                    alert.show();
                }
            });

            Volley.newRequestQueue(getContext()).add(stringRequest);
        }
    }
}
