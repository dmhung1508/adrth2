package com.example.th2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StatisticsActivity extends AppCompatActivity {

    private TextView tvTotalProducts, tvAoCount, tvQuanCount, tvPhukienCount;
    private ImageView imgBestProduct;
    private TextView tvBestProductName, tvBestProductStock, tvBestProductPrice;
    private RatingBar ratingBestProduct;
    private ListView listViewAllProducts;
    
    private List<Product> productList;
    private StatisticsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        initViews();
        setupToolbar();
        loadData();
        displayStatistics();
    }

    private void initViews() {
        tvTotalProducts = findViewById(R.id.tvTotalProducts);
        tvAoCount = findViewById(R.id.tvAoCount);
        tvQuanCount = findViewById(R.id.tvQuanCount);
        tvPhukienCount = findViewById(R.id.tvPhukienCount);
        
        imgBestProduct = findViewById(R.id.imgBestProduct);
        tvBestProductName = findViewById(R.id.tvBestProductName);
        tvBestProductStock = findViewById(R.id.tvBestProductStock);
        tvBestProductPrice = findViewById(R.id.tvBestProductPrice);
        ratingBestProduct = findViewById(R.id.ratingBestProduct);
        
        listViewAllProducts = findViewById(R.id.listViewAllProducts);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        // Nhận dữ liệu từ Intent
        productList = new ArrayList<>();
        if (getIntent().hasExtra("productList")) {
            ArrayList<Product> receivedList = (ArrayList<Product>) getIntent().getSerializableExtra("productList");
            if (receivedList != null) {
                productList.addAll(receivedList);
            }
        }
    }

    private void displayStatistics() {
        if (productList.isEmpty()) {
            tvTotalProducts.setText("0");
            tvAoCount.setText("0");
            tvQuanCount.setText("0");
            tvPhukienCount.setText("0");
            findViewById(R.id.cardBestProduct).setVisibility(View.GONE);
            return;
        }

        // Tính toán thống kê
        int totalProducts = productList.size();
        int aoCount = 0, quanCount = 0, phukienCount = 0;
        Product bestRatedProduct = null;
        float maxRating = 0;

        for (Product product : productList) {
            switch (product.getCategory()) {
                case "Áo":
                    aoCount++;
                    break;
                case "Quần":
                    quanCount++;
                    break;
                case "Phụ kiện":
                    phukienCount++;
                    break;
            }

            if (product.getRating() > maxRating) {
                maxRating = product.getRating();
                bestRatedProduct = product;
            }
        }

        // Hiển thị tổng quan
        tvTotalProducts.setText(String.valueOf(totalProducts));
        tvAoCount.setText(String.valueOf(aoCount));
        tvQuanCount.setText(String.valueOf(quanCount));
        tvPhukienCount.setText(String.valueOf(phukienCount));

        // Hiển thị sản phẩm đánh giá cao nhất
        if (bestRatedProduct != null) {
            NumberFormat formatter = NumberFormat.getInstance(new Locale.Builder().setLanguage("vi").setRegion("VN").build());
            
            imgBestProduct.setImageResource(bestRatedProduct.getImageResource());
            tvBestProductName.setText(bestRatedProduct.getName());
            tvBestProductStock.setText("Số lượng: " + bestRatedProduct.getStockQuantity());
            tvBestProductPrice.setText(formatter.format(bestRatedProduct.getPrice()) + "đ");
            ratingBestProduct.setRating(bestRatedProduct.getRating());
        }

        // Hiển thị danh sách tất cả sản phẩm
        adapter = new StatisticsAdapter(this, productList);
        listViewAllProducts.setAdapter(adapter);
        
        // Fix ListView height trong ScrollView
        setListViewHeightBasedOnChildren(listViewAllProducts);
    }

    // Helper method để fix ListView trong ScrollView
    private void setListViewHeightBasedOnChildren(ListView listView) {
        StatisticsAdapter adapter = (StatisticsAdapter) listView.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}

