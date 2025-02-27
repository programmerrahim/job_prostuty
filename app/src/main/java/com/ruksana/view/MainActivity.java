package com.ruksana.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ruksana.adapter.VerticalAdapter;
import com.ruksana.jobprostuti.R;
import com.ruksana.model.HorizontalItem;
import com.ruksana.model.VerticalItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView verticalRecyclerView;
    private VerticalAdapter verticalAdapter;
    private List<VerticalItem> verticalItemList;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Drawer Layout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Set up Navigation Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, // String resource for "open drawer" description
                R.string.navigation_drawer_close // String resource for "close drawer" description
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Handle Navigation Item Clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_privacy_policy_id) {
                // Handle item 1 click
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_terms_and_conditions_id) {
                // Handle item 2 click
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Initialize RecyclerView
        verticalRecyclerView = findViewById(R.id.rvVertical);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        verticalItemList = new ArrayList<>();
        verticalAdapter = new VerticalAdapter(verticalItemList, this);
        verticalRecyclerView.setAdapter(verticalAdapter);

        fetchDataFromFirebase();
    }

    private void fetchDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("verticalItems");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                verticalItemList.clear();
                for (DataSnapshot verticalSnapshot : snapshot.getChildren()) {
                    String title = verticalSnapshot.child("title").getValue(String.class);
                    List<HorizontalItem> horizontalItems = new ArrayList<>();
                    for (DataSnapshot horizontalSnapshot : verticalSnapshot.child("horizontalItems").getChildren()) {
                        String imageUrl = horizontalSnapshot.child("imageUrl").getValue(String.class);
                        String category = horizontalSnapshot.child("category").getValue(String.class);
                        horizontalItems.add(new HorizontalItem(imageUrl, category));
                    }
                    verticalItemList.add(new VerticalItem(title, horizontalItems));
                }
                verticalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Inflate the custom dialog layout
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null);

            // Initialize views
            Button btnCancel = dialogView.findViewById(R.id.btnCancel);
            Button btnExit = dialogView.findViewById(R.id.btnExit);

            // Create the dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            // Handle Cancel Button Click
            btnCancel.setOnClickListener(v -> {
                alertDialog.dismiss(); // Dismiss the dialog
            });

            // Handle Exit Button Click
            btnExit.setOnClickListener(v -> {
                super.onBackPressed(); // Close the app
            });

        }
    }
}