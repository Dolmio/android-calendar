package fi.aalto.calendar.app;

import android.app.Application;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.squareup.okhttp.OkHttpClient;


public class CalendarApplication extends Application {


public OkHttpClient httpClient;
public ObjectMapper objectMapper;
    @Override
    public void onCreate() {
        super.onCreate();
        httpClient =  new OkHttpClient();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


    }
}
