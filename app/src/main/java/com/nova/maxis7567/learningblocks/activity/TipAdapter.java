package com.nova.maxis7567.learningblocks.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.models.Tip;
import com.nova.maxis7567.learningblocks.tools.Picasso;

import java.util.List;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {
    private Context context;
    private List<Tip> list;

    public TipAdapter(Context context, List<Tip> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.item_tip,parent,false);
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

    class ViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView image;
        private TextView title,desc;
        public ViewHolder(@NonNull View v) {
            super(v);
            image=v.findViewById(R.id.ItemTipImage);
            title=v.findViewById(R.id.ItemTipTitle);
            desc=v.findViewById(R.id.ItemTipDesc);
        }

        public void bindItem(Tip tip) {
            Picasso.load(tip.getIcon(),image,R.drawable.image_holder_spinner);
            title.setText(tip.getTitle());
            desc.setText(tip.getDesc());
        }
    }
}
