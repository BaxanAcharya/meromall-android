package com.example.meromall.adaptar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meromall.R;
import com.example.meromall.model.HorizontalProductScroll;

import java.util.List;

public class GridProductLayoutAdaptar extends BaseAdapter {
    List<HorizontalProductScroll> horizontalProductScrolls;

    public GridProductLayoutAdaptar(List<HorizontalProductScroll> horizontalProductScrolls) {
        this.horizontalProductScrolls = horizontalProductScrolls;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            ImageView productImage = view.findViewById(R.id.h_s_productImage);
            TextView productTitle = view.findViewById(R.id.h_s_productname);
            TextView productDescription = view.findViewById(R.id.h_s_product_description);
            TextView productPrice = view.findViewById(R.id.h_s_product_price);

            productImage.setImageResource(horizontalProductScrolls.get(position).getProductImage());
            productTitle.setText(horizontalProductScrolls.get(position).getProductName());
            productDescription.setText(horizontalProductScrolls.get(position).getProductDescription());
            productPrice.setText(horizontalProductScrolls.get(position).getProductPrice());
        } else {
            view = convertView;
        }

        return view;
    }
}
