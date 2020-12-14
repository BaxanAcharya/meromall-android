package com.example.meromall.adaptar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meromall.R;
import com.example.meromall.model.HorizontalProductScroll;

import java.util.ArrayList;
import java.util.List;

public class HorizontalProductScrollAdaptar extends RecyclerView.Adapter<HorizontalProductScrollAdaptar.ViewHolder> {

    private List<HorizontalProductScroll> horizontalProductScrolls;

    public HorizontalProductScrollAdaptar(List<HorizontalProductScroll> horizontalProductScrolls) {
        this.horizontalProductScrolls = horizontalProductScrolls;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdaptar.ViewHolder holder, int position) {
        int resource = horizontalProductScrolls.get(position).getProductImage();
        String name = horizontalProductScrolls.get(position).getProductName();
        String desc = horizontalProductScrolls.get(position).getProductDescription();
        String price = horizontalProductScrolls.get(position).getProductPrice();
        holder.setproductImage(resource);
        holder.setProductText(name, desc, price);

    }

    @Override
    public int getItemCount() {
        return horizontalProductScrolls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productname, productDescription, productprice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.h_s_productImage);
            productname = itemView.findViewById(R.id.h_s_productname);
            productDescription=itemView.findViewById(R.id.h_s_product_description);
            productprice = itemView.findViewById(R.id.h_s_product_price);
        }

        private void setproductImage(int resource) {
            productImage.setImageResource(resource);
        }

        private void setProductText(String name, String desc, String price) {
            productname.setText(name);
//            productDescription.setText(desc);
            productprice.setText(price);
        }
    }
}
