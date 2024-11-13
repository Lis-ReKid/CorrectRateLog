package com.re_kid.lis.accuracychecker.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String timePrm = requireArguments().getString("Time");
        Context context = requireContext();
        TimePickerDialog.OnTimeSetListener listener = (TimePickerDialog.OnTimeSetListener)getActivity();
        int hourOfDay = Integer.parseInt(timePrm != null ? timePrm.substring(0, 2) : "00");
        int minute = Integer.parseInt(timePrm != null ? timePrm.substring(3, 5) : "00");
        return new TimePickerDialog(context, listener, hourOfDay, minute, true);
    }
}
