package fi.aalto.calendar.app;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;

public class JacksonObservable<T> implements Observable.OnSubscribe<T> {
    private final ObjectMapper objectMapper;
    private final InputStream is;
    private final Class<T> clazz;

    private JacksonObservable(ObjectMapper objectMapper, InputStream is, Class<T> clazz) {
        this.objectMapper = objectMapper;
        this.is = is;
        this.clazz = clazz;
    }

    public static <T> Observable<T> createObservable(ObjectMapper objectMapper, InputStream is, Class<T> clazz) {
        return Observable.create(new JacksonObservable(objectMapper, is, clazz));
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        try {
            subscriber.onNext(objectMapper.readValue(is, clazz));
            subscriber.onCompleted();
        } catch (IOException e) {
            subscriber.onError(e);
        }
    }
}