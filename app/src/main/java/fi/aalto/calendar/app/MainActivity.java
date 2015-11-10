package fi.aalto.calendar.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.fasterxml.jackson.databind.ObjectMapper;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.util.Arrays;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listview = (ListView) findViewById(R.id.listView);
        ObjectMapper mapper =  ((CalendarApplication) this.getApplication()).objectMapper;
        Observable<CalendarEvent[]> eventsObservable = ApiClient.getEvents(((CalendarApplication) this.getApplication()).httpClient, mapper);

        eventsObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultEvents -> {
                    System.out.println("Got " + resultEvents.length + " events");
                    final CalendarEventArrayAdapter adapter = new CalendarEventArrayAdapter(this, Arrays.asList(resultEvents));
                    listview.setAdapter(adapter);

                }, throwable -> {
                    System.err.println("Something went wrong when fetching the events");
                    throwable.printStackTrace();
                    System.err.println(throwable.getMessage());
                });
    }

}
