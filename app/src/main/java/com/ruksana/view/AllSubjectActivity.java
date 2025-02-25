package com.ruksana.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.ruksana.adapter.adapter;
import com.ruksana.jobprostuti.R;
import com.ruksana.model.Model_Firestore_Database;

import java.util.ArrayList;

public class AllSubjectActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recview;

    ArrayList<Model_Firestore_Database> datalist;
    FirebaseFirestore db;

    adapter adapter;

    String button_name;
    String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subject);


        FirebaseApp.initializeApp(this);



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait...");
        progressDialog.show();

        category = getIntent().getStringExtra("CATEGORY");


        //int action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(button_name);


        //add back button
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        recview = findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(AllSubjectActivity.this));

        db = FirebaseFirestore.getInstance();

        datalist = new ArrayList<>();

        adapter = new adapter(datalist);


        recview.setAdapter(adapter);


        datalist.clear();
        loadData();


        swipeRefreshLayout = findViewById(R.id.main_swipe_layoutId);
        swipeRefreshLayout.setOnRefreshListener(() -> {

            datalist.clear();
            loadData();

            swipeRefreshLayout.setRefreshing(false);
        });
    }

    //onCreate End





    private void loadData() {
        db = FirebaseFirestore.getInstance();
        db.collection("data")
                .orderBy("category", Query.Direction.ASCENDING)
                .whereEqualTo("category",category)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<DocumentSnapshot> list = (ArrayList<DocumentSnapshot>) queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Model_Firestore_Database obj = d.toObject(Model_Firestore_Database.class);
                            datalist.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                });
    }


    //actionbar
    private void actionBar() {

    }


    //Back Override method
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//go previous activity, when back button of  actionbar clicked
        return super.onSupportNavigateUp();
    }
}