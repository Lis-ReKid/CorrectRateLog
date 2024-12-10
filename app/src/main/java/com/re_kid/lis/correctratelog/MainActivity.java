package com.re_kid.lis.correctratelog;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.re_kid.lis.correctratelog.model.HistoryModel;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // フラグメントの取得
        try(HistoryModel model = new HistoryModel(MainActivity.this)) {
            Cursor cursor = model.selectAll();
            int count = cursor.getCount();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setReorderingAllowed(true);
            if (count == 0) {
                transaction.replace(R.id.top_fragment_container, NoDataFragment.class, null);
            } else {
                transaction.replace(R.id.top_fragment_container, CorrectRateFragment.newInstance(cursor), null);
                transaction.replace(R.id.bottom_fragment_container, HistoryListFragment.class, null);
            }
            transaction.commit();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
