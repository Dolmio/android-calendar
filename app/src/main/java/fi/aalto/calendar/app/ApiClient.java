package fi.aalto.calendar.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import rx.Observable;
import rx.exceptions.OnErrorThrowable;

import java.io.IOException;

public class ApiClient {

    private static final String host = "http://10.0.2.2:8080/";
    private static final String eventsUrl = host + "api/event";

    public static Observable<CalendarEvent[]> getEvents(OkHttpClient httpClient, ObjectMapper objectMapper) {
        return OkHttpObservable.createObservable(httpClient, new Request.Builder().url(eventsUrl).get().build())
                .map(response -> {
                    try {
                        return response.body().byteStream();
                    } catch (IOException e) {
                        throw OnErrorThrowable.from(e);
                    }
                }) .flatMap(is -> JacksonObservable.createObservable(objectMapper, is, CalendarEvent[].class));
    }
}
