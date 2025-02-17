package com.re_kid.lis.correctratelog.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        var timePrm = requireArguments().getString("Time");
        var context = requireContext();
        var listener = (TimePickerDialog.OnTimeSetListener)getActivity();
        var hourOfDay = Integer.parseInt(timePrm != null ? timePrm.substring(0, 2) : "00");
        var minute = Integer.parseInt(timePrm != null ? timePrm.substring(3, 5) : "00");
        return new TimePickerDialog(context, listener, hourOfDay, minute, true);
    }
}
