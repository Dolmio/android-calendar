package fi.aalto.calendar.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import java.util.List;

public class CalendarMonthDayArrayAdapter extends ArrayAdapter<List<CalendarEvent>> {
    private final Context context;
    private final List<List<CalendarEvent>> values;
    private final YearMonth yearMonth;

    public CalendarMonthDayArrayAdapter(Context context, List<List<CalendarEvent>> values, YearMonth currentYearMonth) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.yearMonth = currentYearMonth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int dayNumber = position + 1;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.month_day_row, parent, false);
        TextView dayNumberView = (TextView) rowView.findViewById(R.id.dayNumber);
        List<CalendarEvent> eventsForDay = values.get(position);
        ListView eventListView = (ListView) rowView.findViewById(R.id.dayEventsList);
        eventListView.setAdapter(new CalendarEventArrayAdapter(context, eventsForDay));
        dayNumberView.setText(String.valueOf(dayNumber));
        dayNumberView.setOnClickListener(v -> {

            int day = Integer.parseInt(((TextView) v).getText().toString());

            Fragment fragment = DayViewFragment.newInstance(
                    new LocalDate(yearMonth.getYear(), yearMonth.getMonthOfYear(), day),
                    eventsForDay);

            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.mainContainer, fragment);
            fragmentTransaction.addToBackStack("Something");
            fragmentTransaction.commit();
        });
        return rowView;
    }
}
