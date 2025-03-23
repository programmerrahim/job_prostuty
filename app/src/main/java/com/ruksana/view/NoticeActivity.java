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
import com.ruksana.adapter.adapterForNotice;
import com.ruksana.jobprostuti.R;
import com.ruksana.model.modelForNotice;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recview;

    ArrayList<modelForNotice> datalist;
    FirebaseFirestore db;

    com.ruksana.adapter.adapterForNotice madapter;

    String button_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_subject);


        FirebaseApp.initializeApp(this);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait...");
        progressDialog.show();


        //int action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;



        //add back button
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Notifications");


        recview = findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(NoticeActivity.this));

        db = FirebaseFirestore.getInstance();

        datalist = new ArrayList<>();

//        madapter = new adapter(datalist);

//        madapter = new adapterForNotice(datalist);

        madapter = new adapterForNotice(datalist);


        recview.setAdapter(madapter);


        datalist.clear();
        loadData();


        swipeRefreshLayout = findViewById(R.id.main_swipe_layoutId);
        swipeRefreshLayout.setOnRefreshListener(() -> {

            datalist.clear();
            loadData();

            swipeRefreshLayout.setRefreshing(false);
        });

    }

    private void loadData() {
        db = FirebaseFirestore.getInstance();
        db.collection("notice")
                .orderBy("category", Query.Direction.DESCENDING)
                .whereEqualTo("category", "notice")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<DocumentSnapshot> list = (ArrayList<DocumentSnapshot>) queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            modelForNotice obj = d.toObject(modelForNotice.class);
                            datalist.add(obj);
                        }
                        madapter.notifyDataSetChanged();
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