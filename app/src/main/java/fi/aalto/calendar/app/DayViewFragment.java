package fi.aalto.calendar.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
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

        final ListView listview = (ListView) view.findViewById(R.id.hourList);
        List<List<CalendarEvent>> eventsByHour = eventsByHourInDay(events);
        listview.setAdapter(new CalendarDayHourArrayAdapter(getContext(), eventsByHour));

        listview.setSelection(getFirstPositionWhereNotEmpty(eventsByHour));
        return view;
    }

    public static int getFirstPositionWhereNotEmpty(List<List<CalendarEvent>> lists) {
        for (int i = 0; i<lists.size(); i++){
            if(!lists.get(i).isEmpty()){
                return i;
            }
        }
        return 0;
    }
    public static List<List<CalendarEvent>> eventsByHourInDay(List<CalendarEvent> events) {
        List<List<CalendarEvent>> result = new ArrayList<>();
        for(int i = 0; i < 24; i++) {
            final int hour = i;
            List<CalendarEvent> eventsInDay = Stream.of(events).filter((e) ->
                    e.getStartTime().getHourOfDay() <= hour && e.getEndTime().getHourOfDay() >= hour
            ).collect(Collectors.toList());
            result.add(i, eventsInDay);
        }
        return result;

    }

}
