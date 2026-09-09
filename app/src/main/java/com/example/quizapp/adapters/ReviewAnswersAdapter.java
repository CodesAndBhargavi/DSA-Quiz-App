package com.example.quizapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizapp.R;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.QuizSessionItem;
import java.util.List;

public class ReviewAnswersAdapter extends RecyclerView.Adapter<ReviewAnswersAdapter.ViewHolder> {

    private final Context context;
    private final List<QuizSessionItem> items;

    public ReviewAnswersAdapter(Context context, List<QuizSessionItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizSessionItem item = items.get(position);
        Question q = item.question;

        holder.tvReviewQNum.setText("Question " + (position + 1) + " • " + q.difficulty);
        holder.tvReviewQuestion.setText(q.questionText);

        String correctText = getOptionText(q, q.correctOption);
        holder.tvReviewCorrectAnswer.setText("Correct: [" + q.correctOption + "] " + correctText);

        if (item.isCorrect) {
            holder.tvReviewStatus.setText("✔ Correct");
            holder.tvReviewStatus.setTextColor(ContextCompat.getColor(context, R.color.correct_green_border));
            holder.tvReviewStatus.setBackgroundResource(R.drawable.bg_option_correct);
            holder.tvReviewUserAnswer.setText("Your Answer: [" + item.selectedOption + "] " + correctText);
            holder.tvReviewUserAnswer.setTextColor(ContextCompat.getColor(context, R.color.correct_green_border));
        } else {
            holder.tvReviewStatus.setText("✖ Incorrect");
            holder.tvReviewStatus.setTextColor(ContextCompat.getColor(context, R.color.wrong_red_border));
            holder.tvReviewStatus.setBackgroundResource(R.drawable.bg_option_wrong);
            String userOptText = getOptionText(q, item.selectedOption);
            holder.tvReviewUserAnswer.setText("Your Answer: [" + item.selectedOption + "] " + userOptText);
            holder.tvReviewUserAnswer.setTextColor(ContextCompat.getColor(context, R.color.wrong_red_border));
        }

        holder.tvReviewExplanation.setText("💡 " + q.explanation);
    }

    private String getOptionText(Question q, String option) {
        if ("A".equalsIgnoreCase(option)) return q.optionA;
        if ("B".equalsIgnoreCase(option)) return q.optionB;
        if ("C".equalsIgnoreCase(option)) return q.optionC;
        if ("D".equalsIgnoreCase(option)) return q.optionD;
        return option != null ? option : "None";
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvReviewQNum, tvReviewStatus, tvReviewQuestion, tvReviewUserAnswer, tvReviewCorrectAnswer, tvReviewExplanation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReviewQNum = itemView.findViewById(R.id.tvReviewQNum);
            tvReviewStatus = itemView.findViewById(R.id.tvReviewStatus);
            tvReviewQuestion = itemView.findViewById(R.id.tvReviewQuestion);
            tvReviewUserAnswer = itemView.findViewById(R.id.tvReviewUserAnswer);
            tvReviewCorrectAnswer = itemView.findViewById(R.id.tvReviewCorrectAnswer);
            tvReviewExplanation = itemView.findViewById(R.id.tvReviewExplanation);
        }
    }
}
