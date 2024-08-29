package com.example.sbmobileshop.activitirs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sbmobileshop.R;
import com.example.sbmobileshop.adapter.MyAdapter;
import com.example.sbmobileshop.adapter.MyCartAdapter;
import com.example.sbmobileshop.databinding.ActivityCheckoutBinding;
import com.example.sbmobileshop.model.DataClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    ActivityCheckoutBinding binding;
    MyCartAdapter adapter;
    ArrayList<DataClass> products;
    double totalPrice = 0;
    final int tax = 7;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing...");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        products = new ArrayList<>();

        String str = "";
        int i=1;
        FirebaseFirestore dbroot = FirebaseFirestore.getInstance();


//        for(Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
//            DataClass product = (DataClass) item.getKey();
//            int quantity = item.getValue();
//            product.setQuantity(String.valueOf(quantity));
//
//            products.add(product);
//
//            str+=i+". "+product.getDataTitle()+'\n';
//            Log.i("saim",str);
//            i++;
//
//            HashMap hashMap = new HashMap();
//            hashMap.put("Products",str);
//
//            String email = user.getEmail();
//            dbroot.collection("metamart").document(email+"orderedProduct")
//                    .set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                        }
//                    });
//        }

//        adapter = new MyCartAdapter(this, products, new MyCartAdapter.ViewHolder() {
//            public void onQuantityChanged() {
//                binding.subtotal.setText(String.format("$ %.2f",cart.getTotalPrice())+"$");
//            }
//        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(adapter);

//        binding.subtotal.setText(String.format("$ %.2f",cart.getTotalPrice()));
//
//        totalPrice = (cart.getTotalPrice().doubleValue() * tax / 100) + cart.getTotalPrice().doubleValue();
//        binding.total.setText("$" + totalPrice);

        String taxText = tax + "%";
        binding.tax.setText(taxText);

        //Data fetching from firestore....

        if (user != null) {
            String email = user.getEmail();
            dbroot.collection("metamart").document(email)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                String name = documentSnapshot.getString("Name");
                                String phone = documentSnapshot.getString("Phone");
                                String address = documentSnapshot.getString("Address");

                                binding.nameBox.setText(name);
                                binding.phoneBox.setText(phone);
                                binding.addressBox.setText(address);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CheckoutActivity.this, "No Such Data!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.nameBox.getText().toString().isEmpty() || binding.phoneBox.getText().toString().isEmpty() || binding.addressBox.getText().toString().isEmpty()){
                    Toast.makeText(CheckoutActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }
                else processOrder();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Check Out");
    }

    int i=0;
    int val = (int)(Math.random()*(1000000-100000)+10000000);

    void processOrder() {
//        progressDialog.show();
//        progressDialog.dismiss();
        new AlertDialog.Builder(CheckoutActivity.this)
                .setTitle("Order Successful")
                .setCancelable(false)
                .setMessage("Your order number is: " + val)
                .setPositiveButton("Pay Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                        intent.putExtra("orderCode", ""+val);
                        intent.putExtra("total",totalPrice);
                        startActivity(intent);
                    }
                }).show();}

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}