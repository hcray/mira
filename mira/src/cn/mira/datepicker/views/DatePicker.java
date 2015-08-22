package cn.mira.datepicker.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import cn.mira.datepicker.interfaces.IPick;
import cn.mira.datepicker.interfaces.OnDateSelected;
import cn.mira.datepicker.interfaces.OnMonthChange;
import cn.mira.datepicker.interfaces.OnYearChange;

/**
 * 日期选择器
 *
 * @author AigeStudio 2015-05-21
 */
public class DatePicker extends LinearLayout implements IPick {
    private MonthView monthView;
    private TitleView titleView;

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.WHITE);
        setOrientation(VERTICAL);

        LayoutParams llParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        titleView = new TitleView(context);
        titleView.setVisibility(View.GONE);
        addView(titleView, llParams);

        monthView = new MonthView(context);
        monthView.setOnPageChangeListener(titleView);
        monthView.setOnSizeChangedListener(titleView);
        addView(monthView, llParams);
    }

    @Override
    public void setOnDateSelected(OnDateSelected onDateSelected) {
        titleView.setOnDateSelected(onDateSelected, monthView);
    }

    @Override
    public void setColor(int color) {
        titleView.setColor(color);
        monthView.setColorMain(color);
    }

    @Override
    public void isLunarDisplay(boolean display) {
        monthView.setLunarShow(display);
    }

	@Override
	public void isMultiSelect(boolean select) {
		monthView.setSelectType(select);
	}

	@Override
	public void setOnMonthChange(OnMonthChange onMonthChange) {
		titleView.setOnMonthChange(onMonthChange);
	}

	@Override
	public void setOnYearChange(OnYearChange onYearChange) {
		titleView.setOnYearChange(onYearChange);
	}

}
