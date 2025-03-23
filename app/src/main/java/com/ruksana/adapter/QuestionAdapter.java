package com.ruksana.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruksana.jobprostuti.R;
import com.ruksana.model.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.bind(question, position);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        private TextView questionText, feedbackText, explanationText;
        private RadioGroup radioGroup;
        private RadioButton optionA, optionB, optionC, optionD;
        private boolean isAnswerSelected = false;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            feedbackText = itemView.findViewById(R.id.feedbackText);
            explanationText = itemView.findViewById(R.id.explanationText);
        }

        public void bind(Question question, int position) {
            // Set question and options
            questionText.setText((position + 1) + ". " + question.getQuestion());
            optionA.setText(question.getOptionA());
            optionB.setText(question.getOptionB());
            optionC.setText(question.getOptionC());
            optionD.setText(question.getOptionD());

            // Reset UI
            radioGroup.clearCheck();
            feedbackText.setText("");
            explanationText.setText("");
            explanationText.setVisibility(View.GONE);
            optionA.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            optionB.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            optionC.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            optionD.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            itemView.setBackgroundColor(itemView.getContext().getResources().getColor(android.R.color.white)); // Reset background
            isAnswerSelected = question.hasBeenAnswered();

            // Restore the selected answer if the question has been answered
            if (question.hasBeenAnswered()) {
                String selectedAnswer = question.getSelectedAnswer();
                if (selectedAnswer != null) {
                    switch (selectedAnswer) {
                        case "A":
                            radioGroup.check(R.id.optionA);
                            break;
                        case "B":
                            radioGroup.check(R.id.optionB);
                            break;
                        case "C":
                            radioGroup.check(R.id.optionC);
                            break;
                        case "D":
                            radioGroup.check(R.id.optionD);
                            break;
                    }
                    showFeedbackAndExplanation(question, selectedAnswer);
                }
            }

            // Set listener for option selection
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (checkedId != -1 && !isAnswerSelected) {
                    isAnswerSelected = true;
                    String selectedAnswer = "";
                    RadioButton selectedRadioButton = itemView.findViewById(checkedId);
                    if (selectedRadioButton == optionA) selectedAnswer = "A";
                    else if (selectedRadioButton == optionB) selectedAnswer = "B";
                    else if (selectedRadioButton == optionC) selectedAnswer = "C";
                    else if (selectedRadioButton == optionD) selectedAnswer = "D";

                    // Save the selected answer
                    question.setSelectedAnswer(selectedAnswer);

                    // Show feedback and explanation
                    showFeedbackAndExplanation(question, selectedAnswer);
                }
            });
        }

        private void showFeedbackAndExplanation(Question question, String selectedAnswer) {
            boolean isCorrect = selectedAnswer.equals(question.getCorrectAnswer());

            // Set background color based on correctness
            if (isCorrect) {
                itemView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.light_green));
                feedbackText.setText("Correct!");
                feedbackText.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
                getSelectedRadioButton(selectedAnswer).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
            } else {
                itemView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.light_red));
                feedbackText.setText("Incorrect!");
                feedbackText.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
                getSelectedRadioButton(selectedAnswer).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cross, 0);

                // Show the correct answer with a checkmark
                if (question.getCorrectAnswer().equals("A")) {
                    optionA.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else if (question.getCorrectAnswer().equals("B")) {
                    optionB.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else if (question.getCorrectAnswer().equals("C")) {
                    optionC.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                } else if (question.getCorrectAnswer().equals("D")) {
                    optionD.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                }
            }

            // Show explanation
            explanationText.setText("Explanation: " + question.getExplanation());
            explanationText.setVisibility(View.VISIBLE);
        }

        private RadioButton getSelectedRadioButton(String selectedAnswer) {
            switch (selectedAnswer) {
                case "A":
                    return optionA;
                case "B":
                    return optionB;
                case "C":
                    return optionC;
                case "D":
                    return optionD;
                default:
                    return null;
            }
        }
    }
}