package fi.aalto.calendar.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import org.joda.time.YearMonth;

public class MonthPageAdapter extends FragmentStatePagerAdapter {

    private int initialPosition = getCount() / 2;
    private YearMonth initalYearMonth = YearMonth.now();
    public MonthPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 500;
    }

    @Override
    public Fragment getItem(int position) {
        YearMonth newYearMonth = position <= initialPosition ?
                initalYearMonth.minusMonths(initialPosition - position):
                initalYearMonth.plusMonths(position - initialPosition);
        return MonthListFragment.newInstance(newYearMonth);
    }

    public int getInitialPosition() {
        return initialPosition;
    }
}