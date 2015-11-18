package fi.aalto.calendar.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    public DatePickerDialog.OnDateSetListener callback;

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener callback) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setDateSetCallback(callback);
        return fragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), callback, year, month, dayOfMonth);
    }

    public void setDateSetCallback(DatePickerDialog.OnDateSetListener callback) {
        this.callback = callback;
    }
}