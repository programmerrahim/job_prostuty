package com.ruksana.jobprostuti;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ruksana.jobprostuti.bcs.BcsModelTestActivity;
import com.ruksana.jobprostuti.bcs.BcsPracticeActivity;
import com.ruksana.jobprostuti.bcs.BcsQuestionBankActivity;
import com.ruksana.jobprostuti.primary.PrimaryModelTestActivity;
import com.ruksana.jobprostuti.primary.PrimaryPracticeActivity;
import com.ruksana.jobprostuti.primary.PrimaryQuestionBankActivity;

public class MainActivity extends AppCompatActivity {


    private boolean isExpanded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        primaryExpandableButtonSection();
        bcsExpandableButtonSection();

        primaryButtonSection();
        bcsButtonSection();


    }

    private void primaryExpandableButtonSection() {
        LinearLayout primaryToggleButton = findViewById(R.id.primary_toggle_button);
        LinearLayout primaryCollapsibleView = findViewById(R.id.collapsible_view);
        ImageView primaryToggleIcon = findViewById(R.id.toggle_icon);


        primaryToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    primaryCollapsibleView.setVisibility(View.GONE);
                    primaryToggleIcon.setImageResource(R.drawable.right_arrow);

                } else {
                    primaryCollapsibleView.setVisibility(View.VISIBLE);
                    primaryToggleIcon.setImageResource(R.drawable.down_arrow);

                }
                isExpanded = !isExpanded;
            }
        });
    }

    private void primaryButtonSection() {
        Button primaryQuestionBankButton = findViewById(R.id.primary_question_bank_id);
        primaryQuestionBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PrimaryQuestionBankActivity.class));
            }
        });

        Button primaryPracticeButton = findViewById(R.id.primary_practice_id);
        primaryPracticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PrimaryPracticeActivity.class));
            }
        });

        Button primaryModelTestButton = findViewById(R.id.primary_model_test_id);
        primaryModelTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PrimaryModelTestActivity.class));
            }
        });
    }


    private void bcsExpandableButtonSection() {
        LinearLayout bcsToggleButton = findViewById(R.id.bcs_toggle_button);
        LinearLayout bcsCollapsibleView = findViewById(R.id.bcs_collapsible_view);
        ImageView bcsToggleIcon = findViewById(R.id.bcs_toggle_icon);


        bcsToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    bcsCollapsibleView.setVisibility(View.GONE);
                    bcsToggleIcon.setImageResource(R.drawable.right_arrow);

                } else {
                    bcsCollapsibleView.setVisibility(View.VISIBLE);
                    bcsToggleIcon.setImageResource(R.drawable.down_arrow);

                }
                isExpanded = !isExpanded;
            }
        });
    }

    private void bcsButtonSection() {
        Button bcsQuestionBankButton = findViewById(R.id.bcs_question_bank_id);
        bcsQuestionBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BcsQuestionBankActivity.class));
            }
        });

        Button bcsPracticeButton = findViewById(R.id.bcs_practice_id);
        bcsPracticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BcsPracticeActivity.class));
            }
        });

        Button bcsModelTestButton = findViewById(R.id.bcs_model_test_id);
        bcsModelTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BcsModelTestActivity.class));
            }
        });
    }
}
