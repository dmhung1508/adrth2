package com.example.th2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView listViewProducts;
    private Button btnAddProduct, btnStatistics;
    private TextView tvProductCount;
    
    private List<Product> productList;
    private ProductAdapter adapter;
    private Map<String, Integer> categoryCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
        setupListeners();
    }

    private void initViews() {
        listViewProducts = findViewById(R.id.listViewProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnStatistics = findViewById(R.id.btnStatistics);
        tvProductCount = findViewById(R.id.tvProductCount);
    }

    private void initData() {
        productList = new ArrayList<>();
        categoryCounter = new HashMap<>();
        categoryCounter.put("Áo", 0);
        categoryCounter.put("Quần", 0);
        categoryCounter.put("Phụ kiện", 0);

        // Thêm dữ liệu mẫu
        addSampleData();

        adapter = new ProductAdapter(this, productList);
        listViewProducts.setAdapter(adapter);
        updateProductCount();
    }

    private void addSampleData() {
        productList.add(new Product("A001", "Áo Cổ Trụ Nam", "Áo", 450000, 4, 4.0f));
        categoryCounter.put("Áo", 1);
        
        productList.add(new Product("Q001", "Quần Jeans Slim Fit", "Quần", 650000, 10, 4.5f));
        categoryCounter.put("Quần", 1);
        
        productList.add(new Product("P001", "Túi Xách Nữ", "Phụ kiện", 350000, 15, 5.0f));
        categoryCounter.put("Phụ kiện", 1);
    }

    private void setupListeners() {
        btnAddProduct.setOnClickListener(v -> showProductDialog(null, -1, false));
        btnStatistics.setOnClickListener(v -> openStatisticsActivity());
        
        // Click để sửa
        listViewProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product product = productList.get(position);
            showProductDialog(product, position, true);
        });
        
        // Swipe để xóa
        setupSwipeToDelete();
    }
    
    private void setupSwipeToDelete() {
        listViewProducts.setOnTouchListener(new SwipeToDeleteListener(listViewProducts) {
            @Override
            public void onSwipeLeft(int position) {
                if (position >= 0 && position < productList.size()) {
                    Product product = productList.get(position);
                    deleteProduct(product, position);
                }
            }
        });
    }
    
    private void deleteProduct(Product product, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm \"" + product.getName() + "\" không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    productList.remove(position);
                    adapter.notifyDataSetChanged();
                    updateProductCount();
                    Toast.makeText(this, "Đã xóa sản phẩm!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void openStatisticsActivity() {
        Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
        intent.putExtra("productList", new ArrayList<>(productList));
        startActivity(intent);
    }

    private void showProductDialog(Product product, int position, boolean isEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_product, null);
        builder.setView(dialogView);

        TextView tvDialogTitle = dialogView.findViewById(R.id.tvDialogTitle);
        TextView tvProductId = dialogView.findViewById(R.id.tvProductId);
        TextInputEditText edtProductName = dialogView.findViewById(R.id.edtProductName);
        Spinner spinnerCategory = dialogView.findViewById(R.id.spinnerCategory);
        TextInputEditText edtPrice = dialogView.findViewById(R.id.edtPrice);
        TextInputEditText edtStockQuantity = dialogView.findViewById(R.id.edtStockQuantity);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Setup Spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.product_categories, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        AlertDialog dialog = builder.create();

        if (isEdit && product != null) {
            tvDialogTitle.setText("Sửa thông tin sản phẩm");
            tvProductId.setVisibility(View.VISIBLE);
            tvProductId.setText("Mã sản phẩm: " + product.getProductId());
            edtProductName.setText(product.getName());
            
            int categoryPosition = 0;
            if (product.getCategory().equals("Quần")) categoryPosition = 1;
            else if (product.getCategory().equals("Phụ kiện")) categoryPosition = 2;
            spinnerCategory.setSelection(categoryPosition);
            spinnerCategory.setEnabled(false); // Không cho đổi loại sản phẩm khi sửa
            
            edtPrice.setText(String.valueOf(product.getPrice()));
            edtStockQuantity.setText(String.valueOf(product.getStockQuantity()));
            ratingBar.setRating(product.getRating());
        } else {
            tvDialogTitle.setText("Thêm sản phẩm mới");
            tvProductId.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(v -> {
            String name = edtProductName.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            String priceStr = edtPrice.getText().toString().trim();
            String stockStr = edtStockQuantity.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                int stock = Integer.parseInt(stockStr);

                if (price <= 0 || stock < 0) {
                    Toast.makeText(this, "Giá và số lượng phải là số dương!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isEdit && product != null) {
                    // Sửa sản phẩm
                    product.setName(name);
                    product.setPrice(price);
                    product.setStockQuantity(stock);
                    product.setRating(rating);
                    Toast.makeText(this, "Đã cập nhật sản phẩm!", Toast.LENGTH_SHORT).show();
                } else {
                    // Thêm sản phẩm mới
                    int count = categoryCounter.get(category);
                    String productId = Product.generateProductId(category, count);
                    Product newProduct = new Product(productId, name, category, price, stock, rating);
                    productList.add(newProduct);
                    categoryCounter.put(category, count + 1);
                    Toast.makeText(this, "Đã thêm sản phẩm mới!", Toast.LENGTH_SHORT).show();
                }

                adapter.notifyDataSetChanged();
                updateProductCount();
                dialog.dismiss();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showStatistics() {
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

        NumberFormat formatter = NumberFormat.getInstance(new Locale.Builder().setLanguage("vi").setRegion("VN").build());
        
        StringBuilder stats = new StringBuilder();
        stats.append("📊 THỐNG KÊ SẢN PHẨM\n\n");
        stats.append("━━━━━━━━━━━━━━━━━━━━━━\n\n");
        stats.append("📦 Tổng số sản phẩm: ").append(totalProducts).append("\n\n");
        stats.append("👕 Số lượng Áo: ").append(aoCount).append("\n");
        stats.append("👖 Số lượng Quần: ").append(quanCount).append("\n");
        stats.append("👜 Số lượng Phụ kiện: ").append(phukienCount).append("\n\n");
        
        if (bestRatedProduct != null) {
            stats.append("━━━━━━━━━━━━━━━━━━━━━━\n\n");
            stats.append("⭐ SẢN PHẨM ĐÁNH GIÁ CAO NHẤT\n\n");
            stats.append("Mã SP: ").append(bestRatedProduct.getProductId()).append("\n");
            stats.append("Tên: ").append(bestRatedProduct.getName()).append("\n");
            stats.append("Loại: ").append(bestRatedProduct.getCategory()).append("\n");
            stats.append("Giá: ").append(formatter.format(bestRatedProduct.getPrice())).append("đ\n");
            stats.append("Đánh giá: ").append(String.format("%.1f", bestRatedProduct.getRating())).append(" ⭐\n");
        }

        new AlertDialog.Builder(this)
                .setTitle("Thống kê")
                .setMessage(stats.toString())
                .setPositiveButton("Đóng", null)
                .show();
    }

    private void updateProductCount() {
        tvProductCount.setText("Danh sách sản phẩm (" + productList.size() + ")");
    }
}
