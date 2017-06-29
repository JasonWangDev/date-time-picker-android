package com.github.jasonwangdev.datetimepicker;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by jason on 2017/6/28.
 */

public class DateTimePickerDialogFragment extends DialogFragment implements View.OnClickListener, DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {

    private static final int MODE_DATE_TIME = 0x01;
    private static final int MODE_DATE = 0x02;
    private static final int MODE_TIME = 0x03;

    private static final String KEY_MODE = "Mode";

    private static final String KEY_YEAR = "Year";
    private static final String KEY_MONTH = "Month";
    private static final String KEY_DAY = "Day";

    private static final String KEY_HOUR = "Hour";
    private static final String KEY_MINUTE = "minute";

    private OnDateTimePickerDialogFragmentClickListener listener;

    private Calendar calendar;

    private DatePicker datePicker;
    private TimePicker timePicker;


    public static DateTimePickerDialogFragment getDateTimeInstance() {
        return getInstance(MODE_DATE_TIME, null);
    }

    public static DateTimePickerDialogFragment getDateInstance() {
        return getInstance(MODE_DATE, null);
    }

    public static DateTimePickerDialogFragment getTimeInstance() {
        return getInstance(MODE_TIME, null);
    }


    private static DateTimePickerDialogFragment getInstance(int mode, Calendar calendar) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_MODE, mode);

        if (null != calendar)
        {
            bundle.putInt(KEY_YEAR, calendar.get(Calendar.YEAR));
            bundle.putInt(KEY_MONTH, calendar.get(Calendar.MONTH));
            bundle.putInt(KEY_DAY, calendar.get(Calendar.DAY_OF_MONTH));

            bundle.putInt(KEY_HOUR, calendar.get(Calendar.HOUR_OF_DAY));
            bundle.putInt(KEY_MINUTE, calendar.get(Calendar.MINUTE));
        }

        DateTimePickerDialogFragment dialog = new DateTimePickerDialogFragment();
        dialog.setArguments(bundle);

        return dialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParams();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setDialogNoTitle();

        View view = loadLayout(inflater, container);

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);

        setDatePickerDividerColor(datePicker);
        setTimePickerDividerColor(timePicker);

        // 文字設定
        switch (getArguments().getInt(KEY_MODE))
        {
            case MODE_DATE_TIME:
                ((TextView) view.findViewById(R.id.tv_title)).setText(getResources().getString(R.string.PickerDialogFragment_Title_SelectDateTime));
                break;

            case MODE_DATE:
                ((TextView) view.findViewById(R.id.tv_title)).setText(getResources().getString(R.string.PickerDialogFragment_Title_SelectDate));
                break;

            case MODE_TIME:
                ((TextView) view.findViewById(R.id.tv_title)).setText(getResources().getString(R.string.PickerDialogFragment_Title_SelectTime));
                break;
        }

        ((Button) view.findViewById(R.id.btn_today)).setText(getResources().getString(R.string.PickerDialogFragment_Button_Today));
        ((Button) view.findViewById(R.id.btn_clear)).setText(getResources().getString(R.string.PickerDialogFragment_Button_Clear));
        ((Button) view.findViewById(R.id.btn_ok)).setText(getResources().getString(R.string.PickerDialogFragment_Button_Ok));

        // 設置按鈕點擊事件
        view.findViewById(R.id.btn_today).setOnClickListener(this);
        view.findViewById(R.id.btn_clear).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);

        // 設置日期 & 時間選擇器要顯示的內容
        initDateTimePicker();

        return view;
    }

    @Override
    public void onResume() {
        setDialogSize();

        super.onResume();
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_today)
        {
            calendar = Calendar.getInstance();
            updateDateTimePicker();
        }
        else if (viewId == R.id.btn_clear)
        {
            if (null != listener)
                listener.onDateTimeClear();
        }
        else if (viewId == R.id.btn_ok)
        {
            if (null != listener)
                listener.onDateTimeSet(calendar);
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        setYear(year);
        setMonth(monthOfYear);
        setDay(dayOfMonth);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        setHour(hourOfDay);
        setMinute(minute);
    }


    public void setOnDateTimePickerDialogFragmentClickListener(OnDateTimePickerDialogFragmentClickListener listener) {
        this.listener = listener;
    }


    private void updateDateTimePicker() {
        updateDatePicker();
        updateTimePicker();
    }

    private void updateDatePicker() {
        if (null != datePicker)
            datePicker.updateDate(getYear(), getMonth(), getDay());
    }

    private void updateTimePicker() {
        if (null != timePicker)
        {
            if (Build.VERSION.SDK_INT >= 23 )
            {
                timePicker.setHour(getHour());
                timePicker.setMinute(getMinute());
            }
            else
            {
                timePicker.setCurrentHour(getHour());
                timePicker.setCurrentMinute(getMinute());
            }
        }
    }

    private void initParams() {
        // 儲存日期時間選擇器要顯示的內容
        calendar = Calendar.getInstance();
        if (getArguments().containsKey(KEY_YEAR))
        {
            calendar.set(Calendar.YEAR, getArguments().getInt(KEY_YEAR));
            calendar.set(Calendar.MONTH, getArguments().getInt(KEY_MODE));
            calendar.set(Calendar.DAY_OF_MONTH, getArguments().getInt(KEY_DAY));

            calendar.set(Calendar.HOUR_OF_DAY, getArguments().getInt(KEY_HOUR));
            calendar.set(Calendar.MINUTE, getArguments().getInt(KEY_MINUTE));
        }
    }

    private View loadLayout(LayoutInflater inflater, ViewGroup container) {
        switch (getArguments().getInt(KEY_MODE))
        {
            case MODE_DATE_TIME:
                return inflater.inflate(R.layout.dialogfragment_datetimepicker, container, false);
            case MODE_DATE:
                return inflater.inflate(R.layout.dialogfragment_datepicker, container, false);
            case MODE_TIME:
                return inflater.inflate(R.layout.dialogfragment_timepicker, container, false);
            default:
                return null;
        }
    }

    private void setDialogNoTitle() {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    private void setDialogSize() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        Configuration configuration = getResources().getConfiguration();
        switch ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK))
        {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                break;

            default:
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                {
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                }
                else
                {
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                break;
        }
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    private void setTimePickerDividerColor(TimePicker picker) {
        if (null == picker)
            return;

        Resources system = Resources.getSystem();
        int dayId = system.getIdentifier("hour", "id", "android");
        int monthId = system.getIdentifier("minute", "id", "android");
        int yearId = system.getIdentifier("amPm", "id", "android");

        NumberPicker dayPicker = (NumberPicker) picker.findViewById(dayId);
        NumberPicker monthPicker = (NumberPicker) picker.findViewById(monthId);
        NumberPicker yearPicker = (NumberPicker) picker.findViewById(yearId);

        setDividerColor(dayPicker);
        setDividerColor(monthPicker);
        setDividerColor(yearPicker);
    }

    private void setDatePickerDividerColor(DatePicker picker) {
        if (null == picker)
            return;

        Resources system = Resources.getSystem();
        int dayId = system.getIdentifier("day", "id", "android");
        int monthId = system.getIdentifier("month", "id", "android");
        int yearId = system.getIdentifier("year", "id", "android");

        NumberPicker dayPicker = (NumberPicker) picker.findViewById(dayId);
        NumberPicker monthPicker = (NumberPicker) picker.findViewById(monthId);
        NumberPicker yearPicker = (NumberPicker) picker.findViewById(yearId);

        setDividerColor(dayPicker);
        setDividerColor(monthPicker);
        setDividerColor(yearPicker);
    }

    private void setDividerColor(NumberPicker picker) {
        if (picker == null)
            return;

        int count = picker.getChildCount();
        for (int i = 0; i < count; i++)
        {
            try
            {
                Field dividerField = picker.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.DateTimePickerDialogFragment_Picker_DividerColor));
                dividerField.set(picker, colorDrawable);
                picker.invalidate();
            }
            catch (Exception e) { }
        }
    }

    private void initDateTimePicker() {
        initDatePicker();
        initTimePicker();
    }

    private void initDatePicker() {
        if (null != datePicker)
            datePicker.init(getYear(), getMonth(), getDay(), this);
    }

    private void initTimePicker() {
        if (null != timePicker)
        {
            if (Build.VERSION.SDK_INT >= 23 )
            {
                timePicker.setHour(getHour());
                timePicker.setMinute(getMinute());
            }
            else
            {
                timePicker.setCurrentHour(getHour());
                timePicker.setCurrentMinute(getMinute());
            }
            timePicker.setOnTimeChangedListener(this);
        }
    }


    private int getYear() {
        return calendarToInt(calendar, Calendar.YEAR);
    }

    private void setYear(int year) {
        setCalendar(calendar, Calendar.YEAR, year);
    }

    private int getMonth() {
        return calendarToInt(calendar, Calendar.MONTH);
    }

    private void setMonth(int month) {
        setCalendar(calendar, Calendar.MONTH, month);
    }

    private int getDay() {
        return calendarToInt(calendar, Calendar.DAY_OF_MONTH);
    }

    private void setDay(int day) {
        setCalendar(calendar, Calendar.DAY_OF_MONTH, day);
    }

    private int getHour() {
        return calendarToInt(calendar, Calendar.HOUR_OF_DAY);
    }

    private void setHour(int hour) {
        setCalendar(calendar, Calendar.HOUR_OF_DAY, hour);
    }

    private int getMinute() {
        return calendarToInt(calendar, Calendar.MINUTE);
    }

    private void setMinute(int minute) {
        setCalendar(calendar, Calendar.MINUTE, minute);
    }

    private int calendarToInt(Calendar calendar, int field) {
        return calendar.get(field);
    }

    private void setCalendar(Calendar calendar, int field, int value) {
        calendar.set(field, value);
    }

}
