package com.codve.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import java.io.File;

public class PreviewFragment extends DialogFragment {

    private ImageView mImageView;
    private static final String ARG_FILE = "file";


    // 实例化方法
    public static PreviewFragment newInstance(File file) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILE, file);
        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // 创建对话框实例
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        File file = (File) getArguments().getSerializable(ARG_FILE);
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_picture, null);
        mImageView = view.findViewById(R.id.crime_photo_zoom);
        Bitmap bitmap = PictureUtils.getScaledBitmap(
                file.getPath(), getActivity());
        mImageView.setImageBitmap(bitmap);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }
}
