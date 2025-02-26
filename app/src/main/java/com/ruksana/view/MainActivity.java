package com.ruksana.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ruksana.adapter.VerticalAdapter;
import com.ruksana.model.VerticalItem;
import com.ruksana.jobprostuti.R;
import com.ruksana.model.HorizontalItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    private RecyclerView verticalRecyclerView;
    private VerticalAdapter verticalAdapter;
    private List<VerticalItem> verticalItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verticalRecyclerView = findViewById(R.id.rvVertical);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        verticalItemList = new ArrayList<>();
        verticalAdapter = new VerticalAdapter(verticalItemList, this); // Pass 'this' as the context
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
                        String category = horizontalSnapshot.child("category").getValue(String.class); // Fetch the text
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


}