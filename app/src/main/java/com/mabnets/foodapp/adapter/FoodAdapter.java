package com.mabnets.foodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mabnets.foodapp.MapsActivity;
import com.mabnets.foodapp.R;
import com.mabnets.foodapp.model.Food;

import java.util.List;

public class FoodAdapter  extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {


    private Context context;
    List <Food> foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.food_layout,null);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        final Food food=foodList.get(position);

        holder.textViewOne.setText(food.getName());
        holder.textViewPriceOne.setText("AED"+food.getPrice());


        Glide.with(context)
                .load(food.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView1);


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MapsActivity.class);
                intent.putExtra("foodid",String.format("%s",food.getId()));
                intent.putExtra("location",food.getLocation());
                intent.putExtra("price",food.getPrice());
                intent.putExtra("image",food.getImageURL());
                intent.putExtra("foodname",food.getName());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder{

        TextView textViewOne,textViewTwo,textViewPriceOne,textViewPriceTwo;
        ImageView imageView1,imageView2;
        Button button;



        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewOne=(TextView)itemView.findViewById(R.id.textFoodName1);
            textViewPriceOne=(TextView)itemView.findViewById(R.id.textFoodPrice1);
            imageView1=(ImageView)itemView.findViewById(R.id.image1);
            button=(Button)itemView.findViewById(R.id.buttonOrder);

        }
    }

}
