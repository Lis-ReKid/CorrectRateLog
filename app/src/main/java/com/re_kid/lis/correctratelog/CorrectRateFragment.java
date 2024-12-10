package com.re_kid.lis.correctratelog;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CorrectRateFragment extends Fragment {
    private Cursor histories;
    public CorrectRateFragment() {
        super(R.layout.fragment_corrrect_rate);
    }

    public static CorrectRateFragment newInstance(Cursor cursor) {
        CorrectRateFragment fragment = new CorrectRateFragment();
        fragment.setCursor(cursor);
        return fragment;
    }

    private void setCursor(Cursor cursor) {
        this.histories = cursor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvAccuracyRate = view.findViewById(R.id.tv_correct_rate);
        tvAccuracyRate.setText("0");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_corrrect_rate, container, false);
    }
}