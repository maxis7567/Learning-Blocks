package com.nova.maxis7567.learningblocks1.tools.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nova.maxis7567.learningblocks1.R;

public class LoadingDialog {
    private final ViewGroup viewGroup;
    private final View view;

    public LoadingDialog(Context context, ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dialog_loading, viewGroup, false);
        view.findViewById(R.id.MessageDialogView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //required Empty body
            }
        });
    }
    public void show(){
        try {
            viewGroup.removeView(view);
        }catch (Exception ignore){}
        viewGroup.addView(view);
    }
    public void dismiss(){
        try {
            viewGroup.removeView(view);
        }catch (Exception ignore){}
    }


}
