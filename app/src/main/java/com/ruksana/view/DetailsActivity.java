package com.ruksana.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.ruksana.adapter.QuestionAdapter;
import com.ruksana.jobprostuti.R;
import com.ruksana.model.Question;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    QuestionAdapter adapter;
    List<Question> questionList;
    FirebaseFirestore db;

   String category,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        //int action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;


        // Get data from Intent
        category = getIntent().getStringExtra("categoryForDetails");
        name = getIntent().getStringExtra("name");




        //add back button
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(name);

        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        questionList = new ArrayList<>();
        adapter = new QuestionAdapter(this, questionList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // Load questions from set1 (you can change set1 to set2, set3, etc.)
        loadQuestionsFromSet(category);
    }

    private void loadQuestionsFromSet(String category) {
        CollectionReference questionsRef = db.collection("questionSets");

        // Add a query to filter questions by difficulty
        Query query = questionsRef.whereEqualTo("category",category);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            questionList.clear(); // Clear the list before adding new items
            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                Question question = doc.toObject(Question.class);
                questionList.add(question);
            }
            adapter.notifyDataSetChanged(); // Notify adapter of data change
        }).addOnFailureListener(e -> {
            // Handle error
            Log.e("FirestoreError", "Error loading questions: " + e.getMessage());
        });
    }
    //Back Override method
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//go previous activity, when back button of  actionbar clicked
        return super.onSupportNavigateUp();
    }
}
