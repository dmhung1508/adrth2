package com.example.th2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private List<Product> products;

    public ProductAdapter(@NonNull Context context, List<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            holder = new ViewHolder();
            holder.imgProduct = convertView.findViewById(R.id.imgProduct);
            holder.tvProductName = convertView.findViewById(R.id.tvProductName);
            holder.tvProductStock = convertView.findViewById(R.id.tvProductStock);
            holder.tvProductPrice = convertView.findViewById(R.id.tvProductPrice);
            holder.ratingProduct = convertView.findViewById(R.id.ratingProduct);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);

        holder.imgProduct.setImageResource(product.getImageResource());
        holder.tvProductName.setText(product.getName());
        holder.tvProductStock.setText("Số lượng: " + product.getStockQuantity());
        
        NumberFormat formatter = NumberFormat.getInstance(new Locale.Builder().setLanguage("vi").setRegion("VN").build());
        holder.tvProductPrice.setText(formatter.format(product.getPrice()) + "đ");
        
        holder.ratingProduct.setRating(product.getRating());

        return convertView;
    }

    static class ViewHolder {
        ImageView imgProduct;
        TextView tvProductName;
        TextView tvProductStock;
        TextView tvProductPrice;
        RatingBar ratingProduct;
    }
}

