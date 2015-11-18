package fi.aalto.calendar.app;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    public TimePickerDialog.OnTimeSetListener callback;

    public static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener callback) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setTimeSetCallback(callback);
        return fragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        
        return new TimePickerDialog(getActivity(), callback, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void setTimeSetCallback(TimePickerDialog.OnTimeSetListener callback) {
        this.callback = callback;
    }
}