package com.nova.maxis7567.learningblocks1.main.categories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.maxis7567.mstools.PersianFaker;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nova.maxis7567.learningblocks1.R;
import com.nova.maxis7567.learningblocks1.main.categories.subcat.SubCatActivity;
import com.nova.maxis7567.learningblocks1.models.Category;
import com.nova.maxis7567.learningblocks1.tools.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {
    private Context context;
    private List<Category> list;

    public CatAdapter(Context context, @Nullable List<Category> list) {
        this.context = context;
        if (list!=null){
            this.list=list;
        }else {
            this.list=new ArrayList<>();
            for (int i = 0; i <10; i++) {
                this.list.add(new Category(i, PersianFaker.getImageUrl(100,180),PersianFaker.getName(),PersianFaker.getAddress(),15));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_category,parent,false);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView image;
        private TextView title,counter;
        public ViewHolder(@NonNull View v) {
            super(v);
            image=v.findViewById(R.id.ItemCategoryImage);
            title=v.findViewById(R.id.ItemCategoryTitle);
            counter=v.findViewById(R.id.ItemCategoryCounter);
        }
        void bindItem(final Category item){
            Picasso.load(item.getImage(),image,R.drawable.image_holder_spinner);
            title.setText(item.getName());
            counter.setText(String.valueOf(item.getCounter()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, SubCatActivity.class);
                    intent.putExtra("cat",item);
                    context.startActivity(intent);
                }
            });
        }
    }
}
