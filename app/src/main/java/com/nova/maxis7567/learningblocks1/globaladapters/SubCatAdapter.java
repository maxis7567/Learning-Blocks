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

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.ViewHolder> {
    private List<Category> categoryList;
    private Context context;

    public SubCatAdapter(Context context, List<Category> categoryList)  {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_sub_cat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.title.setText(categoryList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SubCatActivity.class);
                intent.putExtra("cat",categoryList.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        public ViewHolder(@NonNull View v) {
            super(v);
            title=v.findViewById(R.id.ItemSubCatTitle);

        }

    }
}
