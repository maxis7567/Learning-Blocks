package com.nova.maxis7567.tavan.main.dashboard;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.maxis7567.mstools.PersianFaker;
import com.hinext.maxis7567.mstools.PostTimeCal;
import com.nova.maxis7567.tavan.R;
import com.nova.maxis7567.tavan.activity.SingleActivity;
import com.nova.maxis7567.tavan.models.RecentActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecentActivityAdapter extends RecyclerView.Adapter<RecentActivityAdapter.ViewHolder> {
    private Context context;
    private List<RecentActivity> list;

    public RecentActivityAdapter(Context context, List<RecentActivity> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recent_activities, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date, title, desc;

        public ViewHolder(@NonNull View v) {
            super(v);
            date = v.findViewById(R.id.ItemRecentActDate);
            title = v.findViewById(R.id.ItemRecentActTitle);
            desc = v.findViewById(R.id.ItemRecentActDesc);
        }

        void bindItem(final RecentActivity item) {
            SpannableStringBuilder date = new SpannableStringBuilder();
            SpannableString dateNumber = new SpannableString(PostTimeCal.Calculator((new Date().getTime()-item.getLastViewedIn().getTime()) / 1000).replaceAll("[^-?0-9]+", "") + "\n");
            dateNumber.setSpan(new RelativeSizeSpan(1.8f), 0, dateNumber.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            date.append(dateNumber).append(PostTimeCal.Calculator((new Date().getTime()-item.getLastViewedIn().getTime()) / 1000).replaceAll("\\d", "") + " پیش");
            this.date.setText(date);
            title.setText(item.getName());
            desc.setText(item.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, SingleActivity.class);
                    intent.putExtra("id",item.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
