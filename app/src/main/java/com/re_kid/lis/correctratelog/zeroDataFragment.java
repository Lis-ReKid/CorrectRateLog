package com.re_kid.lis.correctratelog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class zeroDataFragment extends Fragment {

    public zeroDataFragment() {
        super(R.layout.fragment_zero_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zero_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 各種ボタンリスナ登録
        View.OnClickListener listener = (View.OnClickListener) getActivity();
        if (listener != null) {
            // 登録画面遷移ボタンリスナ登録
            view.findViewById(R.id.btn_create).setOnClickListener(listener);
            // カテゴリ一覧画面遷移ボタンリスナ登録
            view.findViewById(R.id.btMoveToCategoryList).setOnClickListener(listener);
            // 絞り込みボタンリスナ登録
            view.findViewById(R.id.btnShowFilteringDialog).setOnClickListener(listener);
        } else {
            Log.e("HistoryListFragment", "OnClickListener is null");
        }
    }
}