package com.example.quizapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizapp.R;
import com.example.quizapp.models.LeaderboardEntry;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private final Context context;
    private final List<LeaderboardEntry> list;

    public LeaderboardAdapter(Context context, List<LeaderboardEntry> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardEntry entry = list.get(position);

        holder.tvLeaderRank.setText(String.valueOf(entry.rank));
        holder.tvLeaderAvatar.setText(entry.avatarEmoji);
        holder.tvLeaderName.setText(entry.userName);
        holder.tvLeaderPoints.setText(entry.points + " pts");

        if (entry.isCurrentUser) {
            holder.layoutLeaderboardItem.setBackgroundResource(R.drawable.bg_option_selected);
            holder.tvLeaderBadge.setText("⭐ You (Current User)");
            holder.tvLeaderBadge.setTextColor(ContextCompat.getColor(context, R.color.purple_primary));
        } else {
            holder.layoutLeaderboardItem.setBackgroundResource(R.drawable.bg_card_main);
            holder.tvLeaderBadge.setText("DSA Solver");
            holder.tvLeaderBadge.setTextColor(ContextCompat.getColor(context, R.color.text_secondary));
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutLeaderboardItem;
        TextView tvLeaderRank, tvLeaderAvatar, tvLeaderName, tvLeaderBadge, tvLeaderPoints;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutLeaderboardItem = itemView.findViewById(R.id.layoutLeaderboardItem);
            tvLeaderRank = itemView.findViewById(R.id.tvLeaderRank);
            tvLeaderAvatar = itemView.findViewById(R.id.tvLeaderAvatar);
            tvLeaderName = itemView.findViewById(R.id.tvLeaderName);
            tvLeaderBadge = itemView.findViewById(R.id.tvLeaderBadge);
            tvLeaderPoints = itemView.findViewById(R.id.tvLeaderPoints);
        }
    }
}
