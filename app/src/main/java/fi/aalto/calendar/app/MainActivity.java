package fi.aalto.calendar.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_pager);

        ViewPager pager = (ViewPager)findViewById(R.id.month_pager);
        MonthPageAdapter adapter = new MonthPageAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getInitialPosition());
    }
}
