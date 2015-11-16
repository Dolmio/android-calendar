package fi.aalto.calendar.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;


public class DayViewFragment extends Fragment {
    private static final String DATE_PARAM = "date_param";
    private static final String EVENTS_PARAM = "events_param";

    private LocalDate date;
    private List<CalendarEvent> events;

    public static DayViewFragment newInstance(LocalDate date, List<CalendarEvent> events) {
        DayViewFragment fragment = new DayViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(DATE_PARAM, date);
        args.putSerializable(EVENTS_PARAM, new ArrayList<>(events));
        fragment.setArguments(args);
        return fragment;
    }

    public DayViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = (LocalDate) getArguments().getSerializable(DATE_PARAM);
            events = (ArrayList<CalendarEvent>) getArguments().getSerializable(EVENTS_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view, container, false);

        TextView dateLabel = (TextView) view.findViewById(R.id.dateLabel);
        dateLabel.setText(date.toString());
        return view;
    }

}
