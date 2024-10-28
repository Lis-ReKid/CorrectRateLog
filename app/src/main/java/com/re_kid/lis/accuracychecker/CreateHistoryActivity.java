package com.re_kid.lis.accuracychecker;

import static java.lang.Long.parseLong;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateHistoryActivity extends AppCompatActivity {
    private DatabaseHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _helper = new DatabaseHelper(CreateHistoryActivity.this);

        CreateHistoryListener listener = new CreateHistoryListener();
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(listener);
    }

    public void onCreateBtnClick(View view) {
        // 入力内容を取得
        TextView tvLearnedDate = findViewById(R.id.tv_learned_date);
        String strLearnedDate = tvLearnedDate.getText().toString();
        EditText etAccurateNumber = findViewById(R.id.et_accurate_number);
        String strAccurateNumber = etAccurateNumber.getText().toString();
        EditText etEntireNumber = findViewById(R.id.et_entire_number);
        String strEntireNumber = etEntireNumber.getText().toString();

        // 正答率の産出
        double accuracyRate = AccuracyRate.calcRate(strAccurateNumber, strEntireNumber);

        SQLiteDatabase db = _helper.getWritableDatabase();

        // SQLを作成
        String sqlInsert = "INSERT INTO Histories " +
                "(history_datetime, accurate_number, entire_number, accuracy_rate)" +
                "VALUES(?, ?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sqlInsert);
        stmt.bindString(1, strLearnedDate);
        stmt.bindLong(2, parseLong(strAccurateNumber));
        stmt.bindLong(3, parseLong(strEntireNumber));
        stmt.bindDouble(4, accuracyRate);

        // SQLを実行
        stmt.executeInsert();
    }

    private class CreateHistoryListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (vId == R.id.btn_back) {
                _helper.close();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        _helper.close();
        super.onDestroy();
    }
}