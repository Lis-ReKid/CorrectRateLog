package com.re_kid.lis.correctratelog.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.re_kid.lis.correctratelog.MainActivity;
import com.re_kid.lis.correctratelog.R;

public class DeleteHistoryCompleteDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getText(R.string.toast_delete_complete));
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getParentFragment().onDestroy();
        super.onDestroyView();
    }
}
