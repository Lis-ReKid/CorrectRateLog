package com.re_kid.lis.correctratelog;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.re_kid.lis.correctratelog.obj.History;

public class UpdateHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 履歴を取得
        var intent = getIntent();
        History history = intent.getParcelableExtra("history");

        // 初期値入力
        EditText etHistoryId = findViewById(R.id.et_history_id);
        etHistoryId.setText(String.valueOf(history.getId()));
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        tvLearnedDate.setText(history.getLearnedDate().toString());
        TextView tvLearnedTime = findViewById(R.id.tv_learned_time);
        tvLearnedTime.setText(history.getLearnedTime().toString());
        EditText etCorrectNumber = findViewById(R.id.et_correct_number);
        etCorrectNumber.setText(String.valueOf(history.getCorrectNum()));
        EditText etEntireNumber = findViewById(R.id.et_entire_number);
        etEntireNumber.setText(String.valueOf(history.getEntireNum()));
        System.out.println(history.getId());
    }
}