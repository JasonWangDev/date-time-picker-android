package com.github.jasonwangdev.datetimepicker;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by jason on 2017/6/29.
 */

public abstract class SuperDialogFragment extends DialogFragment {

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    private static final String TAG = SuperDialogFragment.class.getName();

    protected OnDateTimePickerDialogFragmentClickListener listener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setDialogNoTitle();

        return createView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        setDialogSize();

        super.onResume();
    }


    public void setOnDateTimePickerDialogFragmentClickListener(OnDateTimePickerDialogFragmentClickListener listener) {
        this.listener = listener;
    }


    protected Calendar getCalendar(DatePicker datePicker, TimePicker timePicker)
    {
        Calendar calendar = Calendar.getInstance();

        if (null != datePicker && null != timePicker)
        {
            Calendar date = getCalendar(datePicker);
            Calendar time = getCalendar(timePicker);

            calendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        }

        return calendar;
    }

    protected Calendar getCalendar(DatePicker datePicker)
    {
        Calendar calendar = Calendar.getInstance();

        if (null != datePicker)
        {
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
        }

        return calendar;
    }

    protected Calendar getCalendar(TimePicker timePicker)
    {
        Calendar calendar = Calendar.getInstance();

        if (null != timePicker)
        {
            int hour;
            int minute;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                hour = timePicker.getHour();
                minute = timePicker.getMinute();
            }
            else
            {
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();
            }

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
        }

        return calendar;
    }


    /**
     * 設定日期 & 時間 Spinner Mode 選取器的分割線
     *
     * 利用系統資源 Id 抓取對應 DatePicker 或 TimePicker 中所有的 NumberPicker 元件，並進行 NumberPicker 分割線顏
     * 色的設定
     *
     * @param picker 傳入日期或時間選取器元件
     * @param colorRes 傳入顏色資源 Id
     */
    protected void setPickerDividerColor(View picker, int colorRes) {
        if (null == picker)
            return;

        Resources system = Resources.getSystem();
        int[] ids;
        if (picker instanceof DatePicker)
        {
            ids = new int[]{system.getIdentifier("day", "id", "android"),
                            system.getIdentifier("month", "id", "android"),
                            system.getIdentifier("year", "id", "android")};
        }
        else if (picker instanceof TimePicker)
        {
            ids = new int[]{system.getIdentifier("hour", "id", "android"),
                            system.getIdentifier("minute", "id", "android"),
                            system.getIdentifier("amPm", "id", "android")};
        }
        else
            return;

        for (int id : ids)
        {
            NumberPicker dayPicker = (NumberPicker) picker.findViewById(id);
            setPickerDividerColor(dayPicker, colorRes);
        }
    }


    /**
     * 設定 DialogFragment 不顯示標題列
     */
    private void setDialogNoTitle() {
        try
        {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 設定 DialogFragment 長寬
     *
     * 根據不同尺寸的螢幕大小以及螢幕的方向動態調整 DialogFragment 需要的大小
     */
    private void setDialogSize() {
        try
        {
            ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
            Configuration configuration = getResources().getConfiguration();
            if ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) != Configuration.SCREENLAYOUT_SIZE_LARGE &&
                (configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) != Configuration.SCREENLAYOUT_SIZE_XLARGE &&
                configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            else
            {
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 設定 NumberPicker 元件的分割線
     *
     * 利用反射機制抓取對應 NumberPicker 元件中的分割線物件進行顏色的改變設定
     *
     * @param picker 傳入 NumberPicker 元件
     * @param colorRes 傳入要設定的顏色資源 Id
     */
    private void setPickerDividerColor(NumberPicker picker, int colorRes) {
        if (picker == null)
            return;

        int count = picker.getChildCount();
        for (int i = 0; i < count; i++)
        {
            try
            {
                Field dividerField = picker.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(getActivity(), colorRes));
                dividerField.set(picker, colorDrawable);
                picker.invalidate();
            }
            catch (Exception e) { }
        }
    }

}
