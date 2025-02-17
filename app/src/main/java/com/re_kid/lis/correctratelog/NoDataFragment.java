package com.re_kid.lis.correctratelog;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoDataFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 履歴新規登録画面遷移ボタンリスナ登録
        view.findViewById(R.id.btn_show_create_history_view).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateHistoryActivity.class);
            startActivity(intent);
        });
        // カテゴリ一覧画面遷移ボタンリスナ登録
        view.findViewById(R.id.btMoveToCategoryListNoData).setOnClickListener(v -> {
            var intent = new Intent(getActivity(), CategoryListActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_data, container, false);
    }
}