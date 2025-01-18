package com.re_kid.lis.correctratelog;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.re_kid.lis.correctratelog.dialog.CreateCategoryDialog;
import com.re_kid.lis.correctratelog.model.CategoryModel;

public class CategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 登録ボタン押下時処理
        findViewById(R.id.btMoveToCategoryCreate).setOnClickListener(v -> {
            var dialog = new CreateCategoryDialog();
            dialog.show(getSupportFragmentManager(), "CreateCategoryDialog");
        });

        // カテゴリリストを作成
        ListView lvCategory = findViewById(R.id.lvCategoryList);
        String[] from = {"_id", "category_name"};
        int[] to = {R.id.tvCategoryIdRow, R.id.tvCategoryNameRow};
        Context context = CategoryListActivity.this;
        Cursor cursor;
        try (var model = new CategoryModel(context)) {
            cursor = model.selectAll();
            var adapter = new SimpleCursorAdapter(CategoryListActivity.this,
                    R.layout.category_row,
                    cursor,
                    from,
                    to,
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            lvCategory.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}