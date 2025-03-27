package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.MainActivity;
import com.re_kid.lis.correctratelog.R;

public class MigrationIdIssuedDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var migrationId = requireArguments().getString("migrationId");
        var builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.dialog_migration_id_issued) + "\n" +
                migrationId.toString() + "\n" +
                getString(R.string.dialog_migration_id_annotation));
        return builder.create();
    }

    @Override
    public void onDestroy() {
        var intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onDestroy();
    }
}
