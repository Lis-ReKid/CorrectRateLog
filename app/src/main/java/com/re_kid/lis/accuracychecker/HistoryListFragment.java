package com.re_kid.lis.accuracychecker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HistoryListFragment extends Fragment {
    private DatabaseHelper _helper;
    public HistoryListFragment() {
        super(R.layout.fragment_history_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _helper = new DatabaseHelper(getActivity());
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
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sql = "SELECT * FROM Histories ORDER BY _id DESC";
        Cursor cursor = db.rawQuery(sql, null);
        String[] from = {"history_datetime", "accuracy_rate", "accurate_number", "entire_number"};
        int[] to = {R.id.tvHistoryDateTimeRow, R.id.tv_accuracy_rate_row, R.id.tv_accuracy_number_row, R.id.tv_entire_number_row};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.history_row, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lvHistory.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        _helper.close();
        super.onDestroy();
    }
}
