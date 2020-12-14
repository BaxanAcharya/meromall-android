package com.example.meromall.adaptar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.meromall.R;
import com.example.meromall.model.Slider;

import java.util.List;

public class SliderAdaptar extends PagerAdapter {
    private List<Slider> sliderList;

    public SliderAdaptar(List<Slider> sliderList) {
        this.sliderList = sliderList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_layout, container, false);
        ConstraintLayout bannerContainer=view.findViewById(R.id.banner_container);
        bannerContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sliderList.get(position).getBackgroundColor())));
        ImageView imageView=view.findViewById(R.id.banner_slide);
        imageView.setImageResource(sliderList.get(position).getBanner());
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return sliderList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
