package fi.aalto.calendar.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;

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

    public static Observable<CalendarEvent> addEvent(CalendarEvent event, OkHttpClient httpClient, ObjectMapper objectMapper) {
        RequestBody body = requestBodyForCreateAndEdit(event, objectMapper);
        return OkHttpObservable.createObservable(httpClient, new Request.Builder().url(eventsUrl).post(body).build())
                .map(response -> {
                    try {
                        return response.body().byteStream();
                    } catch (IOException e) {
                        throw OnErrorThrowable.from(e);
                    }
                }
                ).flatMap(is -> JacksonObservable.createObservable(objectMapper, is, CalendarEvent.class));
    }

    //Sync TO
    //TODO

    //Sync FROM
    //TODO

    //EDIT
    //TODO


    private static RequestBody requestBodyForCreateAndEdit(CalendarEvent event, ObjectMapper mapper) {
        String editableEventInJSON = null;
        try {
            editableEventInJSON = mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //The request
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return RequestBody.create(JSON, editableEventInJSON);
    }





}
