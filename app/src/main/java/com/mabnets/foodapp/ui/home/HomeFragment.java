package com.mabnets.foodapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mabnets.foodapp.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

public class HomeFragment extends Fragment  {

    private HomeViewModel homeViewModel;
    SliderLayout sliderLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout=(SliderLayout)root.findViewById(R.id.imageSliderHome);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.COLOR);
        sliderLayout.setScrollTimeInSec(1);
        setSliderViews();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;



    }

    private void setSliderViews() {

        for (int i = 0; i <= 3; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(getContext());

            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://www.famatec.mabnets.com/foodapp/images/IMG20200420-103016.jpg");
                    sliderView.setDescription("Amani Rentals, Nakuru");
                    break;
                case 1:
                    sliderView.setImageUrl("https://www.famatec.mabnets.com/foodapp/images/IMG20200420-103820.jpg");
                    break;
                case 2:
                    sliderView.setImageUrl("https://www.famatec.mabnets.com/foodapp/images/IMG20200420-103830.jpg");
                    break;
                case 3:
                    sliderView.setImageUrl("https://www.famatec.mabnets.com/foodapp/images/IMG20200420-103928.jpg");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("Welcome to Hungry."+"\t"+"Best Online Food ordering.");
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(getContext(), (sliderView.getDescription()), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }
}
