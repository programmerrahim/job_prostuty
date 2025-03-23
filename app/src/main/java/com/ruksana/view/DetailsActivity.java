package com.ruksana.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ruksana.adapter.QuestionAdapter;
import com.ruksana.jobprostuti.R;
import com.ruksana.model.Question;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuestionAdapter adapter;
    private List<Question> questionList = new ArrayList<>();
    private FirebaseFirestore db;
    private Toolbar toolbar;


    String category;

    private ProgressBar loadingIndicator; // Added loading indicator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup ActionBar (Toolbar)
        if (getSupportActionBar() != null) {
            setupActionBar(getSupportActionBar());
        }

        // Get data from Intent with null safety
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("categoryQ", "");
            String name = extras.getString("name", "");
            String data = extras.getString("data", "");

            // Set title if name exists
            if (!name.isEmpty()) {
                getSupportActionBar().setTitle(name);
            }
        }

        // Initialize Loading Indicator
        loadingIndicator = findViewById(R.id.loading_indicator);


        // Initialize UI elements
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuestionAdapter(questionList);
        recyclerView.setAdapter(adapter);

        // Fetch questions from Firestore
        fetchQuestions();
    }

    private void fetchQuestions() {
        loadingIndicator.setVisibility(View.VISIBLE);

        db.collection("questions")
                .orderBy("categoryQ", Query.Direction.ASCENDING)
                .whereEqualTo("categoryQ", category)
                .get()
                .addOnCompleteListener(task -> {
                    // Dismiss dialog regardless of success or failure
                    // Hide loading indicator when data is loaded
                    loadingIndicator.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        questionList.clear(); // Clear existing data to avoid duplicates
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String question = document.getString("question");
                            String optA = document.getString("optionA");
                            String optB = document.getString("optionB");
                            String optC = document.getString("optionC");
                            String optD = document.getString("optionD");
                            String correctAnswer = document.getString("correctAnswer");
                            String explanation = document.getString("explanation");

                            Question q = new Question(question, optA, optB, optC, optD, correctAnswer, explanation);
                            questionList.add(q);

                        }
                        if (!questionList.isEmpty()) {
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, "No questions found!", Toast.LENGTH_SHORT).show();
                            // Hide loading indicator when data is loaded
                            loadingIndicator.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(this, "Error fetching questions: " + task.getException(), Toast.LENGTH_SHORT).show();
                        // Hide loading indicator when data is loaded
                        loadingIndicator.setVisibility(View.GONE);
                    }
                });
    }

    private void setupActionBar(ActionBar actionBar) {
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}