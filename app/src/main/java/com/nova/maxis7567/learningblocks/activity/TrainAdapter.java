package com.nova.maxis7567.learningblocks.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.hinext.maxis7567.mstools.DisplayMetricsUtils;
import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.models.Step;
import com.nova.maxis7567.learningblocks.tools.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.List;



public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.ViewHolder> {
    private Context context;
    private List<Step> list;
    private DisplayMetricsUtils dis;

    public TrainAdapter(Context context, List<Step> list) {
        Fresco.initialize(context);
        this.context = context;
        this.list = list;
        dis=new DisplayMetricsUtils(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.item_step,parent,false);
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
        private TextView number,title,content,download;
        private View line;
        private ImageView image1,image2,image3;
        private LinearLayout imageView;
        private ConstraintLayout downloadView;
        public ViewHolder(@NonNull View v) {
            super(v);
            number=v.findViewById(R.id.ItemStepNumber);
            title=v.findViewById(R.id.ItemStepTitle);
            content=v.findViewById(R.id.ItemStepContent);
            line=v.findViewById(R.id.ItemStepLine);
            download=v.findViewById(R.id.ItemStepDownload);
            downloadView=v.findViewById(R.id.ItemStepDownloadView);
            imageView=v.findViewById(R.id.ItemStepImageView);
            image1=v.findViewById(R.id.ItemStepImage1);
            image3=v.findViewById(R.id.ItemStepImage3);
            image2=v.findViewById(R.id.ItemStepImage2);
//            if (getAdapterPosition()!=list.size()-1&&list.size()>1){
//                itemView.getLayoutParams().width=dis.getDeviceScreenSize().x*85/100;
//            }

        }

        public void bindItem(final Step step) {
            title.setText(step.getTitle());
            number.setText(String.valueOf(getAdapterPosition()+1));
            content.setText(step.getDesc());
            if (getAdapterPosition()==0){
                ConstraintLayout.LayoutParams newLayoutParams = (ConstraintLayout.LayoutParams) line.getLayoutParams();
                newLayoutParams.setMargins(0,dis.convertDIPToPixels(16),0,0);
                line.setLayoutParams(newLayoutParams);
            }else if (getAdapterPosition()==list.size()-1){
                ConstraintLayout.LayoutParams newLayoutParams = (ConstraintLayout.LayoutParams) line.getLayoutParams();
                newLayoutParams.setMargins(0,0,0,0);
                newLayoutParams.height=dis.convertDIPToPixels(16);
                line.setLayoutParams(newLayoutParams);
            }
            else {
                ConstraintLayout.LayoutParams newLayoutParams = (ConstraintLayout.LayoutParams) line.getLayoutParams();
                newLayoutParams.setMargins(0,0,0,0);
                line.setLayoutParams(newLayoutParams);
            }
            if (title.length()==0){
                title.setVisibility(View.GONE);
            }
            if (step.getImage1()!=null||step.getImage3()!=null||step.getImage2()!=null){
                final List<String> tmp=new ArrayList<>();
                if (step.getImage1()!=null){
                    tmp.add(step.getImage1());
                }
                if (step.getImage2()!=null){
                    tmp.add(step.getImage2());
                }
                if (step.getImage3()!=null){
                    tmp.add(step.getImage3());
                }

                imageView.setVisibility(View.VISIBLE);
                if (step.getImage1()!=null) {
                    Picasso.load(step.getImage1(), image1, R.drawable.image_holder_spinner);
                    image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageViewer.Builder  fresco=   new ImageViewer.Builder(context,tmp.toArray());
                            GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                                    .setProgressBarImage(R.drawable.fressco_progress);
                            fresco.setCustomDraweeHierarchyBuilder(hierarchyBuilder);
                            fresco.show();
                        }
                    });
                }
                if (step.getImage2()!=null) {
                    Picasso.load(step.getImage2(), image2, R.drawable.image_holder_spinner);
                    image2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageViewer.Builder  fresco=   new ImageViewer.Builder(context,tmp.toArray());
                            GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                                    .setProgressBarImage(R.drawable.fressco_progress);
                            fresco.setCustomDraweeHierarchyBuilder(hierarchyBuilder);
                            fresco.setStartPosition(1);
                            fresco.show();
                        }
                    });
                }
                if (step.getImage3()!=null) {
                    Picasso.load(step.getImage3(), image3, R.drawable.image_holder_spinner);
                    image3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageViewer.Builder  fresco=   new ImageViewer.Builder(context,tmp.toArray());
                            GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                                    .setProgressBarImage(R.drawable.fressco_progress);
                            fresco.setCustomDraweeHierarchyBuilder(hierarchyBuilder);
                            fresco.setStartPosition(2);
                            fresco.show();
                        }
                    });
                }
            }else {
                imageView.setVisibility(View.GONE);
            }
            if (step.getFile()!=null){
                downloadView.setVisibility(View.VISIBLE);
                download.setText(step.getFileSize());
                downloadView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(step.getFile()));
                        context.startActivity(intent);
                    }
                });
            }else {
                downloadView.setVisibility(View.GONE);
            }
        }
    }
}
