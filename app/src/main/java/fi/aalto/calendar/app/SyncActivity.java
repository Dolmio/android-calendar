package fi.aalto.calendar.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SyncActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        CalendarApplication app = (CalendarApplication) getApplication();

        setupSyncListener(findViewById(R.id.buttonSyncTo),  ApiClient.syncToGoogle(app.httpClient, app.objectMapper), "Synced Events to Google succesfully");
        setupSyncListener(findViewById(R.id.buttonSyncFrom),  ApiClient.syncFromGoogle(app.httpClient, app.objectMapper), "Synced Events from Google succesfully");
    }

    private void setupSyncListener(View view, Observable<String> syncObservable, String successMessage) {
        view.setOnClickListener(v -> {
            syncObservable.
                    subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            result -> {
                                Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_LONG).show();
                                finish();
                            },
                            throwable -> Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show());
        });

    }


}
