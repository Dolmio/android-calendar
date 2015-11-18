package fi.aalto.calendar.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem add_menu) {
        switch (add_menu.getItemId()) {
            case R.id.add_menu:
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                return true;
            case R.id.sync_menu:
                Intent intent2 = new Intent(MainActivity.this, SyncActivity.class);
                startActivity(intent2);
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
