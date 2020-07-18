package com.nova.maxis7567.learningblocks.search;

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
import com.hinext.maxis7567.mstools.RecycelerLoadMore;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.activity.SingleActivity;
import com.nova.maxis7567.learningblocks.authentication.ActiveActivity;
import com.nova.maxis7567.learningblocks.main.categories.subcat.SubCatActivity;
import com.nova.maxis7567.learningblocks.models.Activity;
import com.nova.maxis7567.learningblocks.models.Category;
import com.nova.maxis7567.learningblocks.tools.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private final RecycelerLoadMore loadMore;
    private List<Search> searches = new ArrayList<>();
    private boolean activity = true;

    public SearchAdapter(Context context, List<Activity> list, RecycelerLoadMore loadMore) {
        this.context = context;
        this.loadMore = loadMore;
        for (int i = 0; i < list.size(); i++) {
            searches.add(new Search(list.get(i).getId(), list.get(i).getTitle(), list.get(i).getDesc()));
        }
    }

    public SearchAdapter(List<Category> list, Context context, RecycelerLoadMore loadMore) {
        this.context = context;
        this.loadMore = loadMore;
        for (int i = 0; i < list.size(); i++) {
            searches.add(new Search(list.get(i).getId(), list.get(i).getName(), list.get(i).getDesc(), list.get(i).getImage(), list.get(i).getCounter()));
        }
        activity=false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(searches.get(position));
        if (searches.size() - 1 == position && loadMore != null) {
            loadMore.callBack();
        }
    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    public void addActivity(List<Activity> list) {
        for (int i = 0; i < list.size(); i++) {
            searches.add(new Search(list.get(i).getId(), list.get(i).getTitle(), list.get(i).getDesc()));
        }
        notifyItemRangeInserted(searches.size() - list.size(), searches.size());
    }

    public void addCategory(List<Category> list) {
        for (int i = 0; i < list.size(); i++) {
            searches.add(new Search(list.get(i).getId(), list.get(i).getName(), list.get(i).getDesc(), list.get(i).getImage(), list.get(i).getCounter()));
        }
        notifyItemRangeInserted(searches.size() - list.size(), searches.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, desc;

        public ViewHolder(@NonNull View v) {
            super(v);

            title = v.findViewById(R.id.ItemSearchTitle);
            desc = v.findViewById(R.id.ItemSearchDesc);
        }

        void bindItem(final Search item) {
            title.setText(item.title);
            desc.setText(item.desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (activity) {
                        intent = new Intent(context, SingleActivity.class);
                        intent.putExtra("id", item.id);
                        context.startActivity(intent);
                    } else {
                        intent = new Intent(context, SubCatActivity.class);
                        Category category = new Category(item.id, item.image, item.image, item.desc, item.count);
                        intent.putExtra("cat", category);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    private static class Search {
        int id;
        String title;
        String desc;
        String image;
        int count;

        public Search(int id, String title, String desc, String image, int count) {
            this.id = id;
            this.title = title;
            this.desc = desc;
            this.image = image;
            this.count = count;
        }

        public Search(int id, String title, String desc) {
            this.id = id;
            this.title = title;
            this.desc = desc;
        }
    }
}
