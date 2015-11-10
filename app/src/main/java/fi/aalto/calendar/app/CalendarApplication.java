package fi.aalto.calendar.app;

import android.app.Application;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;

public class CalendarApplication extends Application {


public OkHttpClient httpClient;
public ObjectMapper objectMapper;
    @Override
    public void onCreate() {
        super.onCreate();
        httpClient =  new OkHttpClient();
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


    }
}
