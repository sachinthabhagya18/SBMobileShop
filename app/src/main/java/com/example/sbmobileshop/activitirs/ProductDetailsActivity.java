package com.example.sbmobileshop.activitirs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.sbmobileshop.MainActivity;
import com.example.sbmobileshop.R;
import com.example.sbmobileshop.databinding.ActivityProductDetailsBinding;
import com.example.sbmobileshop.model.DataClass;
import com.example.sbmobileshop.model.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView productDescription;

    ActivityProductDetailsBinding binding;
    DataClass currentProduct;

    Toolbar toolbar;
     int totalQuntity = 1;
     int totalPrice = 1;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ImageView detailedImg;
    TextView price,description;
    TextView quentity,title;
    Button removeItem,addItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        quentity =  findViewById(R.id.quantity);
        detailedImg =  findViewById(R.id.productImage);
        title =  findViewById(R.id.title);
        addItem = findViewById(R.id.plusBtn);
        removeItem = findViewById(R.id.minusBtn);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuntity < 10){
                    totalQuntity++;
                    quentity.setText(String.valueOf(totalQuntity));
                    totalPrice = Integer.parseInt(currentProduct.getPrice()) * totalQuntity;
                }
            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuntity > 0){
                    totalQuntity--;
                    quentity.setText(String.valueOf(totalQuntity));
                    totalPrice = Integer.parseInt(currentProduct.getPrice()) * totalQuntity;
                }
            }
        });

        String dataTitle = getIntent().getStringExtra("Title");
        String dataDesc = getIntent().getStringExtra("Description");
        String dataPrice = getIntent().getStringExtra("Price");
        String dataQty = getIntent().getStringExtra("Qty");
        String dataImage = getIntent().getStringExtra("Image");

        getSupportActionBar().setTitle(dataTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productDescription = findViewById(R.id.productDescription);
        title.setText(dataTitle);
        productDescription.setText(dataDesc);
        binding.productPrice.setText("Price: $ " + dataPrice);
        Glide.with(this).load(dataImage).into(binding.productImage);
        currentProduct = new DataClass(dataTitle, dataDesc, dataQty,dataPrice, dataImage);
        totalPrice = Integer.parseInt(currentProduct.getPrice()) * totalQuntity;
        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();

            }
        });
    }

    public void addToCart(){

        String saveCurrentDate,saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate =  new SimpleDateFormat("MM:dd:yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime =  new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        String title = currentProduct.getDataTitle();
        String price = currentProduct.getPrice();

        currentTime = currentTime;

        MyCartModel dataClass = new MyCartModel(String.valueOf(title),saveCurrentDate,saveCurrentTime,String.valueOf(totalQuntity),String.valueOf(totalPrice));
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        FirebaseDatabase.getInstance().getReference("Cart data").child(saveCurrentDate +":"+ saveCurrentTime)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ProductDetailsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProductDetailsActivity.this, HomeActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductDetailsActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

//        final HashMap<String,Object> cartMap =new HashMap<>();
//
//
//        cartMap.put("productName",currentProduct.getDataTitle());
//        cartMap.put("productPrice",currentProduct.getPrice());
//        cartMap.put("totalQuntity",quentity.getText().toString());
////        cartMap.put("totalPrice",totalPrice);
//        cartMap.put("currentDate",saveCurrentDate);
//        cartMap.put("currentTime",saveCurrentTime);
//
//        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
//                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                        Toast.makeText(ProductDetailsActivity.this, "Product added to Cart", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(ProductDetailsActivity.this, HomeActivity.class));
//
//                        finish();
//                    }
//                });
    }

}