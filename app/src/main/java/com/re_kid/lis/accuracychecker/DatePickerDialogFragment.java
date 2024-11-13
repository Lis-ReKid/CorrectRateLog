package com.re_kid.lis.accuracychecker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePickerDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String datePrm = requireArguments().getString("Date");
        Context context = requireContext();
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener)getActivity();
        int year = Integer.parseInt(datePrm != null ? datePrm.substring(0, 4) : "2000");
        int month = Integer.parseInt(datePrm != null ? datePrm.substring(5, 7) : "01");
        int day = Integer.parseInt(datePrm != null ? datePrm.substring(8, 10) : "01");
        DatePickerDialog datePicker = new DatePickerDialog(context, listener, year, month -1, day);
        return datePicker;
    }
}
