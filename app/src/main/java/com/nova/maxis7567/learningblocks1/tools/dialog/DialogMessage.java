package com.nova.maxis7567.learningblocks1.tools.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mmin18.widget.RealtimeBlurView;
import com.nova.maxis7567.learningblocks1.R;


public class DialogMessage {
    private Context context;
    public ViewGroup parents;
    public View view;
    public RealtimeBlurView blurView;
    public TextView success;
    public TextView cancel;
    public TextView title;
    public TextView description;
    public ImageView image;
    private boolean cancelable = false;

    public DialogMessage setSuccessTitle(String title) {
        this.success.setText(title);
        return this;
    }

    public DialogMessage setTitle(String title) {
        this.title.setText(title);
        return this;
    }

    public DialogMessage setDescription(String title) {
        this.description.setText(title);
        return this;
    }

    public DialogMessage setCancelTitle(String title) {
        this.cancel.setText(title);
        return this;
    }


    public DialogMessage setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }


    public View getView() {
        return view;
    }

    public RealtimeBlurView getBlurView() {
        return blurView;
    }

    public TextView getSuccess() {
        return success;
    }

    public TextView getCancel() {
        return cancel;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getDescription() {
        return description;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void show(){
        try {
            parents.removeView(view);
        } catch (Exception ignore) {
        }
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parents.addView(view);
            }
        });
    }

    public DialogMessage(Context context, final ViewGroup parent, final boolean singleButton, final DialogMessageInterface interfaceConfirm) {
        parents = parent;
        this.context=context;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dialog_message, parent, false);
        blurView = view.findViewById(R.id.MessageDialogView);
        success = view.findViewById(R.id.MessageDialogSuccess);
        title = view.findViewById(R.id.MessageDialogTitle);
        description = view.findViewById(R.id.MessageDialogDesc);
        cancel = view.findViewById(R.id.MessageDialogCancel);
        image = view.findViewById(R.id.MessageDialogImage);
        if (singleButton){
            cancel.setVisibility(View.GONE);
        }
        blurView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!singleButton || cancelable) {
                    try {
                        parents.removeView(view);
                    } catch (Exception ignore) {
                    }
                    interfaceConfirm.OnCancel();
                }
            }
        });
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    parents.removeView(view);
                } catch (Exception ignore) {

                }
                interfaceConfirm.OnConfirmed();
            }
        });
    }


    public interface DialogMessageInterface {
        void OnConfirmed();

        void OnCancel();
    }
}
