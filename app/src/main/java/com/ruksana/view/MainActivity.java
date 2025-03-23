package com.ruksana.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
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
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView verticalRecyclerView;
    private VerticalAdapter verticalAdapter;
    private List<VerticalItem> verticalItemList;
    private DrawerLayout drawerLayout;
    private Button joinBtn;
    private TextView joinTxt;
    private ProgressBar loadingIndicator; // Added loading indicator

    private String url;
    private String name;

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

        // Initialize Loading Indicator
        loadingIndicator = findViewById(R.id.loading_indicator);

        // Set up Navigation Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Handle Navigation Item Clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_privacy_policy_id) {
                CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                String url = "https://banglaserialandnatok.blogspot.com/p/privacy-policy.html";
                intent.launchUrl(MainActivity.this, Uri.parse(url));
            } else if (id == R.id.nav_terms_and_conditions_id) {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Join Text
        joinTxt = findViewById(R.id.joinTextId);

        // Get a reference to the database for course link
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("course/courseLink");

        // Show loading indicator while fetching course data
        loadingIndicator.setVisibility(View.VISIBLE);

        // Retrieve data once
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();
                    url = (String) userData.get("url");
                    name = (String) userData.get("name");
                    joinTxt.setText(name);
                }
                // Hide loading indicator when data is loaded
                loadingIndicator.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", "Database error: " + databaseError.getMessage());
                // Hide loading indicator on error
                loadingIndicator.setVisibility(View.GONE);
            }
        });

        // Join button
        joinBtn = findViewById(R.id.joinButtonId);
        joinBtn.setOnClickListener(view -> {
            CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
            intent.launchUrl(MainActivity.this, Uri.parse(url));
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

        // Show loading indicator while fetching data
        loadingIndicator.setVisibility(View.VISIBLE);
        verticalRecyclerView.setVisibility(View.GONE);

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
                        String name = horizontalSnapshot.child("name").getValue(String.class);
                        horizontalItems.add(new HorizontalItem(imageUrl, category, name));
                    }
                    verticalItemList.add(new VerticalItem(title, horizontalItems));
                }
                verticalAdapter.notifyDataSetChanged();

                // Hide loading indicator and show RecyclerView when data is loaded
                loadingIndicator.setVisibility(View.GONE);
                verticalRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Database error: " + error.getMessage());
                // Hide loading indicator on error
                loadingIndicator.setVisibility(View.GONE);
                verticalRecyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_exit, null);
            Button btnCancel = dialogView.findViewById(R.id.btnCancel);
            Button btnExit = dialogView.findViewById(R.id.btnExit);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            btnCancel.setOnClickListener(v -> alertDialog.dismiss());
            btnExit.setOnClickListener(v -> super.onBackPressed());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_notification) {
            startActivity(new Intent(MainActivity.this, NoticeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}