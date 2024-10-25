package com.example.fruitshopping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dtos.ProductDTO;
import model.Cart;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView productName, productDescription, productPrice, quantityText;
    private Button increaseButton, decreaseButton, addToCartButton, buyButton, back; // Thêm buyButton
    private ImageView productImage;

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Khởi tạo các thành phần giao diện
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        quantityText = findViewById(R.id.quantityText);
        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);
        addToCartButton = findViewById(R.id.addToCartButton);
        buyButton = findViewById(R.id.buyButton); // Khởi tạo buyButton
        productImage = findViewById(R.id.productImage);
        back = findViewById(R.id.back);
        // Lấy dữ liệu sản phẩm từ Intent
        ProductDTO product = (ProductDTO) getIntent().getSerializableExtra("product");
        if (product != null) {
            productName.setText(product.getName());
            productDescription.setText(product.getDescription());
            productPrice.setText(String.format("$%.2f", product.getPrice()));

            // Lấy ID tài nguyên từ tên hình ảnh
            int resId = getResources().getIdentifier(product.getImage(), "drawable", getPackageName());
            if (resId != 0) {
                productImage.setImageResource(resId);
            } else {
                // Hình ảnh không tồn tại, thiết lập hình ảnh mặc định
                productImage.setImageResource(R.drawable.ic_launcher_background); // Thay 'ic_launcher_background' bằng tên hình ảnh mặc định bạn muốn sử dụng
            }
        }

        // Xử lý sự kiện tăng giảm số lượng
        increaseButton.setOnClickListener(view -> {
            quantity++;
            quantityText.setText(String.valueOf(quantity));
        });

        decreaseButton.setOnClickListener(view -> {
            if (quantity > 1) {
                quantity--;
                quantityText.setText(String.valueOf(quantity));
            }
        });

        // Xử lý sự kiện "Add to Cart"
        addToCartButton.setOnClickListener(view -> {
            addToCart(product, quantity);
        });

        // Xử lý sự kiện "Buy Now"
        buyButton.setOnClickListener(view -> {
            // Chuyển đến trang xác nhận sản phẩm
            Intent intent = new Intent(ProductDetailActivity.this, ConfirmProductActivity.class);
            intent.putExtra("product", product);
            intent.putExtra("quantity", quantity);
            startActivity(intent);
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });
    }

    // Phương thức thêm sản phẩm vào giỏ hàng
    private void addToCart(ProductDTO product, int quantity) {
        if (product != null) {
            Cart.addItem(product, quantity); // Thêm sản phẩm vào giỏ hàng
            Log.d("Cart", "Added to cart: " + product.getName() + ", Quantity: " + quantity);
            // Có thể hiển thị thông báo cho người dùng ở đây

        }
    }


}
