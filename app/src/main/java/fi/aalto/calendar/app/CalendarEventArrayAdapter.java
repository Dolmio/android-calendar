package fi.aalto.calendar.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CalendarEventArrayAdapter extends ArrayAdapter<CalendarEvent> {
    private final Context context;
    private final List<CalendarEvent> values;

    public CalendarEventArrayAdapter(Context context, List<CalendarEvent> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.calendar_event_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.descriptionLabel);
        CalendarEvent event = values.get(position);
        textView.setText(event.getDescription());
        return rowView;
    }

}
