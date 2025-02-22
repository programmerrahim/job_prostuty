package com.ruksana.jobprostuti.bcs;

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
import com.ruksana.adapter.adapter_for_practice;
import com.ruksana.jobprostuti.R;
import com.ruksana.model.Model_Firestore_Database;

import java.util.ArrayList;

public class BcsPracticeActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recview;

    ArrayList<Model_Firestore_Database> datalist;
    FirebaseFirestore db;

    com.ruksana.adapter.adapter_for_practice adapter_for_practice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bank);


        FirebaseApp.initializeApp(this);

        actionBar();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait...");
        progressDialog.show();


        recview = findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(BcsPracticeActivity.this));

        db = FirebaseFirestore.getInstance();

        datalist = new ArrayList<>();

        adapter_for_practice = new adapter_for_practice(datalist);


        recview.setAdapter(adapter_for_practice);


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


    //actionbar
    private void actionBar() {
        //int action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Bcs Practice");


        //add back button
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    private void loadData() {
        db = FirebaseFirestore.getInstance();
        db.collection("Bcs")
                .document("bcs")
                .collection("practice")
                .orderBy("category", Query.Direction.ASCENDING)
                .whereEqualTo("category", "practice")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<DocumentSnapshot> list = (ArrayList<DocumentSnapshot>) queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            Model_Firestore_Database obj = d.toObject(Model_Firestore_Database.class);
                            datalist.add(obj);
                        }
                        adapter_for_practice.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                });
    }


    //Back Override method
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//go previous activity, when back button of  actionbar clicked
        return super.onSupportNavigateUp();
    }
}