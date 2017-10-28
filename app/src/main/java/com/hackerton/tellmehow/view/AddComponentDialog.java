package com.hackerton.tellmehow.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.view.Window;

import com.hackerton.tellmehow.R;
import com.hackerton.tellmehow.databinding.DialogAddComponentBinding;

public class AddComponentDialog extends Dialog {
    private DialogAddComponentBinding binding;
    private Runnable listener;

    public AddComponentDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_add_component, null, false);
        setContentView(binding.getRoot());

        binding.closeButton.setOnClickListener((v) -> {
            this.dismiss();
        });

        binding.saveButton.setOnClickListener((v) -> {
            listener.run();
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void setDoneListener(Runnable listener) {
        this.listener = listener;
    }

    @Override
    public void show() {
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        super.show();
    }
}