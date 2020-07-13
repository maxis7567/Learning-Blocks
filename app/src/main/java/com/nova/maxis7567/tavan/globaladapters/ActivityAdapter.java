package com.nova.maxis7567.tavan.globaladapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nova.maxis7567.tavan.R;
import com.nova.maxis7567.tavan.activity.SingleActivity;
import com.nova.maxis7567.tavan.models.Activity;
import com.nova.maxis7567.tavan.tools.Picasso;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private List<Activity> activityList;
    private Context context;

    public ActivityAdapter(Context context,List<Activity> activityList)  {
        this.activityList = activityList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_activity,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(activityList.get(position));
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView title,desc;
        public ViewHolder(@NonNull View v) {
            super(v);
            image=v.findViewById(R.id.ItemActivityImage);
            title=v.findViewById(R.id.ItemActivityTitle);
            desc=v.findViewById(R.id.ItemActivityDesc);
        }
        void bindItem(final Activity activity){
            Picasso.load(activity.getImage(),image,R.drawable.template_image1);
            title.setText(activity.getTitle());
            desc.setText(activity.getDesc());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, SingleActivity.class);
                    intent.putExtra("id",activity.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
