package fi.aalto.calendar.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

public class CalendarDayHourArrayAdapter extends ArrayAdapter<List<CalendarEvent>> {
    private final Context context;
    private final List<List<CalendarEvent>> values;

    public CalendarDayHourArrayAdapter (Context context, List<List<CalendarEvent>> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int hourNumber = position;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.month_day_row, parent, false);
        TextView dayNumberView = (TextView) rowView.findViewById(R.id.dayNumber);
        List<CalendarEvent> eventsForDay = values.get(position);
        ListView eventListView = (ListView) rowView.findViewById(R.id.dayEventsList);
        eventListView.setAdapter(new CalendarEventArrayAdapter(context, eventsForDay));
        dayNumberView.setText(String.valueOf(hourNumber) + ":00");
        return rowView;
    }
}
