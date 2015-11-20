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
    private static final String syncToUrl = host + "api/sync-to";
    private static final String syncFromUrl = host + "api/sync-from";

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

    public static Observable<CalendarEvent> editEvent(CalendarEvent event, OkHttpClient httpClient, ObjectMapper objectMapper) {
        RequestBody body = requestBodyForCreateAndEdit(event, objectMapper);
        String url = eventsUrl + "/" + event.getId();
        return OkHttpObservable.createObservable(httpClient, new Request.Builder().url(url).put(body).build())
                .map(response -> {
                            try {
                                return response.body().byteStream();
                            } catch (IOException e) {
                                throw OnErrorThrowable.from(e);
                            }
                        }
                ).flatMap(is -> JacksonObservable.createObservable(objectMapper, is, CalendarEvent.class));
    }

    public static Observable<String> deleteEvent(CalendarEvent event, OkHttpClient httpClient, ObjectMapper objectMapper) {
        String url = eventsUrl + "/" + event.getId();
        return OkHttpObservable.createObservable(httpClient, new Request.Builder().url(url).delete().build())
                .map(response -> {
                            try {
                                return response.body().string();
                            } catch (IOException e) {
                                throw OnErrorThrowable.from(e);
                            }
                        }
                );

    }

    public static Observable<String> syncToGoogle( OkHttpClient httpClient, ObjectMapper objectMapper) {
        return sync(syncToUrl, httpClient, objectMapper);
    }

    public static Observable<String> syncFromGoogle( OkHttpClient httpClient, ObjectMapper objectMapper) {
        return sync(syncFromUrl, httpClient, objectMapper);
    }



    private static Observable<String> sync(String url, OkHttpClient httpClient, ObjectMapper objectMapper) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body  = RequestBody.create(JSON, "{}");

        return OkHttpObservable.createObservable(httpClient, new Request.Builder().url(url).post(body).build())
                .map(response -> {
                            try {
                                return response.body().string();
                            } catch (IOException e) {
                                throw OnErrorThrowable.from(e);
                            }
                        }
                );
    }

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
