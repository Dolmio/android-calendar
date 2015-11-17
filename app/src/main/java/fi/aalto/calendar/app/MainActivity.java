package fi.aalto.calendar.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


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


    @Override
    public boolean onOptionsItemSelected(MenuItem add_menu) {
        switch (add_menu.getItemId()) {
            case R.id.add_menu:
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(add_menu);

        }
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
