package fi.aalto.calendar.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import rx.Observable;
import rx.exceptions.OnErrorThrowable;

import java.io.IOException;

public class ApiClient {

    private static final String host = "http://10.5.1.77:8080/";
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

    //Add & Edit an event
    public static String addOrEditEvent(OkHttpClient httpClient, RequestBody formBody) throws Exception {
        Request request = new Request.Builder()
                .url(eventsUrl)
                .post(formBody)
                .build();
        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("ERROR : " + response);
       return response.body().string();
    }

    //Sync TO
        //TODO

    //Sync FROM
        //TODO

}
