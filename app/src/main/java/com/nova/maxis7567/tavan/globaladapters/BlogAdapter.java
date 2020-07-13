package com.nova.maxis7567.tavan.globaladapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.hinext.maxis7567.mstools.PersianFaker;
import com.hinext.maxis7567.mstools.PostTimeCal;
import com.hinext.maxis7567.mstools.RecycelerLoadMore;
import com.nova.maxis7567.tavan.R;
import com.nova.maxis7567.tavan.blog.SingleBlogActivity;
import com.nova.maxis7567.tavan.models.Blog;
import com.nova.maxis7567.tavan.tools.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {
    private Context context;
    private List<Blog> list;
    private RecycelerLoadMore loadMore;

    public BlogAdapter(Context context, @Nullable List<Blog> list, @Nullable RecycelerLoadMore loadMore) {
        this.context = context;
        this.loadMore = loadMore;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                this.list.add(new Blog(i, PersianFaker.getFullname(), PersianFaker.getImageUrl(300, 220), new Date().getTime() / 1000));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_blog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(list.get(position));
        if (list.size() - 1 == position && loadMore != null) {
            loadMore.callBack();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<Blog> tmp) {
        list.addAll(tmp);
        notifyItemRangeInserted(list.size() - tmp.size(), list.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, date;
        private ImageView image;

        public ViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.ItemBlogTitle);
            date = v.findViewById(R.id.ItemBlogDate);
            image = v.findViewById(R.id.ItemBlogImage);
        }

        void bindItem(final Blog item) {
            Picasso.load(item.getImage(), image, R.drawable.template_blog);
            title.setText(item.getTitle());
            date.setText(PostTimeCal.Calculator(new Date().getTime() / 1000 - item.getDate()) + " پیش");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SingleBlogActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("time", date.getText());
                    if (item.getImage()==null){
                        intent.putExtra("image", "http://tavanyaar.com/null");
                    }else
                    intent.putExtra("image", item.getImage());
                    context.startActivity(intent);
                }
            });
        }
    }
}
