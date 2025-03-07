package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.re_kid.lis.correctratelog.MainActivity;
import com.re_kid.lis.correctratelog.R;

public class MigrationCanIssueDialogFragment extends MigrationDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
        // カスタムビューをインフレート
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.dialog_data_migration, null);
        // データ移行ID確定ボタンリスナ登録
        view.findViewById(R.id.btnConfirmMigrationId).setOnClickListener(v -> {
            var result = false;
            // 何らかの処理
            EditText etMigrationId = view.findViewById(R.id.etMigrationId);
            if (etMigrationId.getText().toString().isEmpty())return;
            // データ移行に成功したらメイン画面をリロード
            if (etMigrationId.getText().toString().equals("testpass")) result = true;
            if (result) {
                var intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return;
            }
            var dialog = new MigrationFailedDialogFragment();
            dialog.show(getActivity().getSupportFragmentManager(), "MigrationDialogFragment");
        });
        // ダイアログをビルド
        builder.setTitle(R.string.dialog_title_migration)
                .setView(view);
        return builder.create();
    }
}
