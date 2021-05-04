package com.mabnets.foodapp.ui.gallery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mabnets.foodapp.MainActivity;
import com.mabnets.foodapp.OrderFood;
import com.mabnets.foodapp.R;
import com.mabnets.foodapp.SharedPrefManager;
import com.mabnets.foodapp.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class GalleryFragment extends Fragment {
    SharedPrefManager sharedPrefManager;

    private TextView textView1,textView2,textView3;
    String name,phone,email;

   private ProgressBar progressBar;
   private Button buttonLog;


    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        textView1=(TextView)root.findViewById(R.id.textName);
        textView2=(TextView)root.findViewById(R.id.textPhone);
        textView3=(TextView)root.findViewById(R.id.textFoodEmail);
        progressBar=(ProgressBar)root.findViewById(R.id.progressAccount);
        buttonLog=(Button)root.findViewById(R.id.buttonLogout);

        if(SharedPrefManager.getInstance(getContext()).isLoggedIn()){

            User user=SharedPrefManager.getInstance(getContext()).getUser();


            phone=user.getPhone();
            textView2.setText("0"+phone);
            getUserDetails();

        }else{

            SharedPrefManager.getInstance(getContext()).logout();
        }

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(getContext());

                builder.setMessage("Sure to logout?");
                builder.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SharedPrefManager.getInstance(getContext()).logout();

                    }
                });

                builder.setPositiveButton("Remain in app", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        ((Activity) getContext()).finish();
                    }
                });
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        startActivity(new Intent(getContext(), MainActivity.class));
                        ((Activity) getContext()).finish();
                    }
                });

                builder.create();
                builder.show();
            }
        });


        //final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        return root;
    }

    private void getUserDetails(){

        progressBar.setVisibility(View.VISIBLE);
        final String URLUser="https://famatec.mabnets.com/foodapp/user.php?phone="+phone;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URLUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);
               //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray personArray=new JSONArray(response);
                    for(int i=0;i<personArray.length();i++){

                        JSONObject personObject=personArray.getJSONObject(i);

                        name=personObject.getString("name");
                        email=personObject.getString("email");

                        textView1.setText(name);
                        textView3.setText(email);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                androidx.appcompat.app.AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
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
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);


    }
}
