package com.example.meromall.adaptar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meromall.R;
import com.example.meromall.model.Category;

import java.util.List;

public class CategoryAdaptar extends RecyclerView.Adapter<CategoryAdaptar.ViewHolder> {
    private List<Category> categories;

    public CategoryAdaptar(List<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catergory_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptar.ViewHolder holder, int position) {
        String icon = categories.get(position).getCategoryImageUrl();
        String name = categories.get(position).getCategoryName();
        holder.setCategoryname(name);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_icon);

            textView = itemView.findViewById(R.id.category_text);
        }

        private void setCategoryIcon() {
            //to do
        }

        private void setCategoryname(String name) {
            //to do
            textView.setText("" + name);
        }
    }

}
