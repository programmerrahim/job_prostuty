package com.ruksana;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ruksana.jobprostuti.R;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    QuestionAdapter adapter;
    List<Question> questionList;
    FirebaseFirestore db;

   String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        // Get data from Intent
        String imageUrl = getIntent().getStringExtra("imageUrl");
        category = getIntent().getStringExtra("category");

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
        CollectionReference questionsRef = db.collection("questionSets").document(category).collection("questions");

        questionsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                Question question = doc.toObject(Question.class);
                questionList.add(question);
            }
            adapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle error
        });
    }
}
