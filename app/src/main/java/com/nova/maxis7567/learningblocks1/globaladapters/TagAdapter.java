package com.nova.maxis7567.learningblocks1.globaladapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nova.maxis7567.learningblocks1.R;
import com.nova.maxis7567.learningblocks1.main.categories.subcat.SubCatActivity;
import com.nova.maxis7567.learningblocks1.models.Category;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {
    private Context context;
    private List<Category> tagList;

    public TagAdapter(Context context, List<Category> tagList) {
        this.context = context;
        this.tagList = tagList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_tag,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ((TextView) holder.itemView).setText(tagList.get(position).getName());
        if (tagList.get(position).getId()!=0){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, SubCatActivity.class);
                    intent.putExtra("cat",tagList.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
