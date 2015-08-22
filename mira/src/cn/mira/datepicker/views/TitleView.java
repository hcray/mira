package cn.mira.datepicker.views;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.mira.datepicker.entities.Language;
import cn.mira.datepicker.interfaces.OnDateSelected;
import cn.mira.datepicker.interfaces.OnMonthChange;
import cn.mira.datepicker.interfaces.OnYearChange;

/**
 * 日期选择器的标题视图
 *
 * @author AigeStudio 2015-05-21
 */
public class TitleView extends LinearLayout implements MonthView.OnPageChangeListener, MonthView.OnSizeChangedListener {
    private String[] monthTitles;
    private TextView tvYear, tvMonth, tvConfirm;
    private OnDateSelected mOnDateSelected;
    private MonthView monthView;
    
    private OnMonthChange mOnMonthChange;

    private OnYearChange mOnYearChange;
    

    public TitleView(Context context) {
        super(context);
        setColor(0xFF84dfd8);
        setOrientation(HORIZONTAL);

        monthTitles = Language.getLanguage(context).monthTitles();

        LayoutParams llParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);

        tvYear = new TextView(context);
        tvYear.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        tvYear.setTextColor(Color.WHITE);

        tvMonth = new TextView(context);
        tvMonth.setGravity(Gravity.CENTER);
        tvMonth.setTextColor(Color.WHITE);

        tvConfirm = new TextView(context);
        tvConfirm.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        tvConfirm.setText(Language.getLanguage(context).ensureTitle());
        tvConfirm.setTextColor(Color.WHITE);
        tvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnDateSelected && null != monthView)
                    mOnDateSelected.selected(monthView.getDateSelected());
            }
        });

        addView(tvYear, llParams);
        addView(tvMonth, llParams);
        addView(tvConfirm, llParams);
    }

    public void setOnDateSelected(OnDateSelected onDateSelected, MonthView monthView) {
        mOnDateSelected = onDateSelected;
        this.monthView = monthView;
    }

    public void setColor(int color) {
        setBackgroundColor(color);
    }
    
    public void setOnMonthChange(OnMonthChange onMonthChange){
    	mOnMonthChange = onMonthChange;
    }
    
    public void setOnYearChange(OnYearChange onYearChange){
    	mOnYearChange = onYearChange;
    }

    @Override
    public void onMonthChange(int month) {
        tvMonth.setText(monthTitles[month - 1]);
        if(mOnMonthChange != null){
        	mOnMonthChange.change(month);
        }
    }

    @Override
    public void onYearChange(int year) {
        tvYear.setText(String.valueOf(year));
        if(mOnYearChange != null){
        	mOnYearChange.change(year);
        }
    }

    @Override
    public void onSizeChanged(int size) {
        int padding = (int) (size * 1F / 50F);
        int textSizeSmall = (int) (size * 1F / 25F);
        int textSizeLarge = (int) (size * 1F / 18F);

        tvYear.setPadding(padding, padding, 0, padding);
        tvYear.getPaint().setTextSize(textSizeSmall);

        tvMonth.setPadding(0, padding, 0, padding);
        tvMonth.getPaint().setTextSize(textSizeLarge);

        tvConfirm.setPadding(0, padding, padding, padding);
        tvConfirm.getPaint().setTextSize(textSizeSmall);
    }
}
