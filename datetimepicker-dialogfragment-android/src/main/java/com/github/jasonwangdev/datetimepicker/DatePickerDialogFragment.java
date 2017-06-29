package com.github.jasonwangdev.datetimepicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by jason on 2017/6/29.
 */

public class DatePickerDialogFragment extends SuperDialogFragment implements DatePicker.OnDateChangedListener,
                                                                             View.OnClickListener {

    private DatePicker datePicker;


    public static DatePickerDialogFragment getInstance() {
        return getInstance(null);
    }

    public static DatePickerDialogFragment getInstance(Calendar calendar) {
        Bundle bundle = new Bundle();
        if (null != calendar)
        {
            bundle.putInt(KEY_YEAR, CalendarUtils.getYear(calendar));
            bundle.putInt(KEY_MONTH, CalendarUtils.getMonth(calendar));
            bundle.putInt(KEY_DAY, CalendarUtils.getDay(calendar));
        }

        DatePickerDialogFragment dialog = new DatePickerDialogFragment();
        dialog.setArguments(bundle);

        return dialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initCalendar();

        super.onCreate(savedInstanceState);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_datepicker, container, false);

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);

        setPickerDividerColor(view.findViewById(R.id.datePicker), R.color.DatePickerDialogFragment_DatePicker_DividerColor);
        initDatePicker();
        initButton(view);

        return view;
    }


    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        updateCalendar(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_today)
        {
            updateCalendar(Calendar.getInstance());
            updateDatePicker();
        }
        else if (viewId == R.id.btn_clear)
        {
            if (null != listener)
                listener.onDateTimeClear();

            dismiss();
        }
        else if (viewId == R.id.btn_ok)
        {
            if (null != listener)
                listener.onDateTimeSet(calendar);

            dismiss();
        }
    }


    private void initDatePicker() {
        datePicker.init(CalendarUtils.getYear(calendar),
                        CalendarUtils.getMonth(calendar),
                        CalendarUtils.getDay(calendar),
                        this);
    }

    private void updateDatePicker() {
        datePicker.updateDate(CalendarUtils.getYear(calendar),
                              CalendarUtils.getMonth(calendar),
                              CalendarUtils.getDay(calendar));
    }

    private void initButton(View view) {
        view.findViewById(R.id.btn_today).setOnClickListener(this);
        view.findViewById(R.id.btn_clear).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);
    }


    private void initCalendar() {
        calendar = Calendar.getInstance();
        if (null != getArguments())
        {
            CalendarUtils.setYear(calendar, getArguments().getInt(KEY_YEAR));
            CalendarUtils.setMonth(calendar, getArguments().getInt(KEY_MONTH));
            CalendarUtils.setDay(calendar, getArguments().getInt(KEY_DAY));
        }
    }

    private void updateCalendar(Calendar calendar) {
        updateCalendar(CalendarUtils.getYear(calendar),
                       CalendarUtils.getMonth(calendar),
                       CalendarUtils.getDay(calendar));
    }

    private void updateCalendar(int year, int monthOfYear, int dayOfMonth) {
        CalendarUtils.setYear(calendar, year);
        CalendarUtils.setMonth(calendar, monthOfYear);
        CalendarUtils.setDay(calendar, dayOfMonth);
    }

}
