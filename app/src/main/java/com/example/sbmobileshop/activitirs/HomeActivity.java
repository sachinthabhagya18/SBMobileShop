package com.example.sbmobileshop.activitirs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.sbmobileshop.R;
import com.example.sbmobileshop.adapter.MyAdapterHome;
import com.example.sbmobileshop.databinding.ActivityHomeBinding;
import com.example.sbmobileshop.databinding.ActivityMainBinding;
import com.example.sbmobileshop.model.DataClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
//    FloatingActionButton fab;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapterHome adapter;
    @NonNull ActivityMainBinding binding;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        ImageSlider imageSlider = findViewById(R.id.iamgeslider);
        ArrayList<SlideModel> slideModels = new ArrayList<SlideModel>();

        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image3, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels,ScaleTypes.FIT);


//        Button button = findViewById(R.id.account);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//            }
//        });
//
//        Button button2 = findViewById(R.id.cart);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, CartFragment.class));
//            }
//        });


        recyclerView = findViewById(R.id.recyclerView);
//        fab = findViewById(R.id.fab);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeActivity.this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        dataList = new ArrayList<>();
        adapter = new MyAdapterHome(HomeActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials");
        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this, UploadActivity.class);
//                startActivity(intent);
//            }
//        });


        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home){
                    Intent intent1 = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent1);
                }
                if (item.getItemId() == R.id.cart){
                    Intent intent2 = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent2);
                }
                if (item.getItemId() == R.id.profile){
                    Intent intent3 = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent3);
                }
                return false;
            }
        });



    }


    public void searchList(String text){
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass: dataList){
            if (dataClass.getDataTitle().toLowerCase().contains(text.toLowerCase())){
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }


//////////////// ..................Bottom Navigation.............../////////////////////////////////////



}