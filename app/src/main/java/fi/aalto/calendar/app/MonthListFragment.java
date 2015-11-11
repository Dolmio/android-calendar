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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.YearMonth;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.util.List;

public class MonthListFragment extends Fragment {

    private static final String YEAR_MONTH_KEY = "currentYearMonth";

    public static MonthListFragment newInstance(YearMonth yearMonth) {
        MonthListFragment fragment = new MonthListFragment();
        Bundle args = new Bundle();
        args.putSerializable(YEAR_MONTH_KEY, yearMonth);
        fragment.setArguments(args);

        return fragment;
    }

    public MonthListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_month_list, container, false);
        final ListView listview = (ListView) view.findViewById(R.id.listView);
        final TextView monthLabel = (TextView) view.findViewById(R.id.month_label);

        final YearMonth currentMonthYear = (YearMonth) getArguments().getSerializable(YEAR_MONTH_KEY);
        monthLabel.setText(currentMonthYear.toString());

        ObjectMapper mapper = ((CalendarApplication) getActivity().getApplication()).objectMapper;
        Observable<CalendarEvent[]> eventsObservable = ApiClient.getEvents(((CalendarApplication) getActivity().getApplication()).httpClient, mapper);


        eventsObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultEvents -> {
                    System.out.println("Got " + resultEvents.length + " events");
                    final CalendarEventArrayAdapter adapter = new CalendarEventArrayAdapter(getContext(), eventsInMonth(currentMonthYear, resultEvents));
                    listview.setAdapter(adapter);

                }, throwable -> {
                    System.err.println("Something went wrong when fetching the events");
                    throwable.printStackTrace();
                    System.err.println(throwable.getMessage());
                });

        return view;
    }

    public static List<CalendarEvent> eventsInMonth(YearMonth yearMonth, CalendarEvent[] events) {
        return Stream.of(events).filter((e) ->
                (e.getStartTime().getMonthOfYear() == yearMonth.getMonthOfYear() && e.getStartTime().getYear() == yearMonth.getYear()) ||
                (e.getEndTime().getMonthOfYear() == yearMonth.getMonthOfYear() && e.getEndTime().getYear() == yearMonth.getYear()))
                .collect(Collectors.toList());
    }

}
