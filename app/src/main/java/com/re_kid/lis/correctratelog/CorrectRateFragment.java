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

import com.re_kid.lis.correctratelog.obj.CorrectRate;
import com.re_kid.lis.correctratelog.obj.History;

import java.util.List;

public class CorrectRateFragment extends Fragment {
    private Cursor historiesCursor;
    public CorrectRateFragment() {
        super(R.layout.fragment_corrrect_rate);
    }

    public static CorrectRateFragment newInstance(Cursor cursor) {
        var fragment = new CorrectRateFragment();
        fragment.setCursor(cursor);
        return fragment;
    }

    private void setCursor(Cursor cursor) {
        this.historiesCursor = cursor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 正答率を取得して表示
        TextView tvAccuracyRate = view.findViewById(R.id.tv_correct_rate);
        var count = historiesCursor.getCount();
        if (count == 0) {
            tvAccuracyRate.setText("-");
        } else {
            List<History> histories = History.getHistories(historiesCursor);
            var correctRate = CorrectRate.getTotalCorrectRate(histories);
            tvAccuracyRate.setText(correctRate.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_corrrect_rate, container, false);
    }
}