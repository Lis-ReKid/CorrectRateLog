package com.re_kid.lis.accuracychecker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HistoryListFragment extends Fragment {
    public HistoryListFragment() {
        super(R.layout.fragment_history_list);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView lvHistory = view.findViewById(R.id.lvHistory);
        //テスト用仮リスト
        List<Map<String, Integer>> tempList = new ArrayList<>();
        Map<String, Integer> tempMap = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            tempMap.put("temp", i);
            tempList.add(tempMap);
        }
        String[] from = {};
        int[] to = {};
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), tempList, R.layout.history_row, from, to);
        lvHistory.setAdapter(adapter);
    }
}
