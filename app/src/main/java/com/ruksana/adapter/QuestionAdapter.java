package com.ruksana.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ruksana.jobprostuti.R;
import com.ruksana.model.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private final Context context;
    private final List<Question> questionList;

    public QuestionAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);

        holder.questionText.setText(question.getQuestion());
        holder.option1Button.setText(question.getOption1());
        holder.option2Button.setText(question.getOption2());
        holder.option3Button.setText(question.getOption3());
        holder.option4Button.setText(question.getOption4());

        // Reset views and button colors for reuse
        holder.resultText.setVisibility(View.GONE);
        holder.explanationText.setVisibility(View.GONE);
        resetButtonColors(holder);

        // Set tags to identify each option
        holder.option1Button.setTag(1);
        holder.option2Button.setTag(2);
        holder.option3Button.setTag(3);
        holder.option4Button.setTag(4);

        // Handle option click
        View.OnClickListener optionClickListener = v -> {
            int selectedOption = (int) v.getTag();
            handleOptionSelection(holder, selectedOption, question.getCorrectOption(), question.getExplanation());
        };

        holder.option1Button.setOnClickListener(optionClickListener);
        holder.option2Button.setOnClickListener(optionClickListener);
        holder.option3Button.setOnClickListener(optionClickListener);
        holder.option4Button.setOnClickListener(optionClickListener);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    private void handleOptionSelection(QuestionViewHolder holder, int selectedOption, int correctOption, String explanation) {
        resetButtonColors(holder); // Reset colors to default before showing result

        Button selectedButton = getButtonByOption(holder, selectedOption);
        Button correctButton = getButtonByOption(holder, correctOption);

        if (selectedOption == correctOption) {
            selectedButton.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
            holder.resultText.setText("Correct!");
            holder.resultText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
        } else {
            assert selectedButton != null;
            selectedButton.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
            correctButton.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
            holder.resultText.setText("Wrong!");
            holder.resultText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        }

        holder.resultText.setVisibility(View.VISIBLE);
        holder.explanationText.setText("Explanation: " + explanation);
        holder.explanationText.setVisibility(View.VISIBLE);
    }

    private Button getButtonByOption(QuestionViewHolder holder, int option) {
        switch (option) {
            case 1:
                return holder.option1Button;
            case 2:
                return holder.option2Button;
            case 3:
                return holder.option3Button;
            case 4:
                return holder.option4Button;
            default:
                return null;
        }
    }

    private void resetButtonColors(QuestionViewHolder holder) {
        int defaultColor = ContextCompat.getColor(context, android.R.color.system_on_primary_light);
        holder.option1Button.setBackgroundColor(defaultColor);
        holder.option2Button.setBackgroundColor(defaultColor);
        holder.option3Button.setBackgroundColor(defaultColor);
        holder.option4Button.setBackgroundColor(defaultColor);
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionText, resultText, explanationText;
        Button option1Button, option2Button, option3Button, option4Button;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            option1Button = itemView.findViewById(R.id.option1Button);
            option2Button = itemView.findViewById(R.id.option2Button);
            option3Button = itemView.findViewById(R.id.option3Button);
            option4Button = itemView.findViewById(R.id.option4Button);
            resultText = itemView.findViewById(R.id.resultText);
            explanationText = itemView.findViewById(R.id.explanationText);
        }
    }
}
