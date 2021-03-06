package fi.aalto.calendar.app;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/*
This custom listview is needed to get wrap content for height to work
 */
public class DayListView  extends ListView {

    public DayListView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DayListView  (Context context) {
        super(context);
    }

    public DayListView  (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}