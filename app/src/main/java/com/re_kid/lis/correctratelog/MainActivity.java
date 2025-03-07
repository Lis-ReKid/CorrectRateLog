package com.re_kid.lis.correctratelog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.re_kid.lis.correctratelog.dialog.MigrationCanIssueDialogFragment;
import com.re_kid.lis.correctratelog.dialog.FilterHistoryByCategoryDialogFragment;
import com.re_kid.lis.correctratelog.dialog.FirstCreateCategoryDialogFragment;
import com.re_kid.lis.correctratelog.model.CategoryModel;
import com.re_kid.lis.correctratelog.model.HistoryModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_NOTIFICATION_REQUESTED = "notification_requested";
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

        // カテゴリ件数チェック
        try (var model = new CategoryModel(MainActivity.this)) {
            Cursor cursor = model.selectAll();
            // カテゴリ未登録の時、登録ダイアログを表示
            if(cursor.getCount() == 0) {
                var dialog = new FirstCreateCategoryDialogFragment();
                dialog.show(MainActivity.this.getSupportFragmentManager(), "FirstCreateCategoryDialog");
            }
        } catch (Exception e) {
            finish();
        }

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
                transaction.replace(R.id.bottom_fragment_container, HistoryListFragment.newInstance(cursor), null);
            }
            transaction.commit();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // データ移行ボタンリスナ登録
        findViewById(R.id.btnMoveToMigrationDialog).setOnClickListener(view -> {
            MigrationCanIssueDialogFragment migrationDialog = new MigrationCanIssueDialogFragment();
            migrationDialog.show(getSupportFragmentManager(), "DataMigrationDialogFragment");
        });

        // 初回のみ通知権限をリクエスト
        requestNotificationPermissionIfNeeded();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {
        var btnId = v.getId();
        if (btnId == R.id.btn_create) {
            Intent intent = new Intent(MainActivity.this, CreateHistoryActivity.class);
            startActivity(intent);
        } else if (btnId == R.id.btMoveToCategoryList) {
            var intent = new Intent(MainActivity.this, CategoryListActivity.class);
            startActivity(intent);
        } else if (btnId == R.id.btnShowFilteringDialog) {
            new FilterHistoryByCategoryDialogFragment()
                    .show(getSupportFragmentManager(),
                            "FilterHistoryByCategoryFragment");
        }
        enableWaitHandler(100L, v);
    }

    private void requestNotificationPermissionIfNeeded() {
        // Android12以前は権限リクエスト不要
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return;
        // リクエストされたことがあるかどうかを取得
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean hasRequested = prefs.getBoolean(KEY_NOTIFICATION_REQUESTED, false);
        // リクエスト済みなら何もしない
        if (hasRequested) return;
        // 権限リクエストを発行
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                1);
        // リクエスト済みフラグを保存
        prefs.edit().putBoolean(KEY_NOTIFICATION_REQUESTED, true).apply();
    }
    public void enableWaitHandler(long stopTime, final View view) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, stopTime);
    }
}