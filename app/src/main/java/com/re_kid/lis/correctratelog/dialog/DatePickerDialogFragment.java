package com.re_kid.lis.correctratelog.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePickerDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var datePrm = requireArguments().getString("Date");
        var context = requireContext();
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener)getActivity();
        var year = Integer.parseInt(datePrm != null ? datePrm.substring(0, 4) : "2000");
        var month = Integer.parseInt(datePrm != null ? datePrm.substring(5, 7) : "01");
        var day = Integer.parseInt(datePrm != null ? datePrm.substring(8, 10) : "01");
        return new DatePickerDialog(context, listener, year, month -1, day);
    }
}
