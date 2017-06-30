package com.github.jasonwangdev.datetimepicker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by jason on 2017/6/29.
 */

public class DatePickerDialogFragment extends SuperDialogFragment implements View.OnClickListener {

    private static final String KEY_YEAR = "Year";
    private static final String KEY_MONTH = "Month";
    private static final String KEY_DAY = "Day";

    private OnDateTimePickerDialogFragmentClickListener listener;

    private DatePicker datePicker;


    public static DatePickerDialogFragment getInstance() {
        return getInstance(null);
    }

    public static DatePickerDialogFragment getInstance(Calendar calendar) {
        Bundle bundle = new Bundle();
        if (null != calendar)
        {
            bundle.putInt(KEY_YEAR, calendar.get(Calendar.YEAR));
            bundle.putInt(KEY_MONTH, calendar.get(Calendar.MONTH));
            bundle.putInt(KEY_DAY, calendar.get(Calendar.DAY_OF_MONTH));
        }

        DatePickerDialogFragment dialog = new DatePickerDialogFragment();
        dialog.setArguments(bundle);

        return dialog;
    }


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_datepicker, container, false);

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);

        setPickerDividerColor(datePicker, R.color.PickerDialogFragment_Picker_DividerColor);
        initDatePicker();
        initButton(view);

        return view;
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_today)
        {
            Calendar currentCalendar = Calendar.getInstance();
            setDatePicker(currentCalendar);
        }
        else if (viewId == R.id.btn_clear)
        {
            if (null != listener)
                listener.onDateTimeClear();

            dismiss();
        }
        else if (viewId == R.id.btn_ok)
        {
            Calendar datePickerCalendar = getCalendar(datePicker);
            if (null != listener)
                listener.onDateTimeSet(datePickerCalendar);

            dismiss();
        }
    }


    public void setOnDateTimePickerDialogFragmentClickListener(OnDateTimePickerDialogFragmentClickListener listener) {
        this.listener = listener;
    }


    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (getArguments().containsKey(KEY_YEAR))
        {
            calendar.set(Calendar.YEAR, getArguments().getInt(KEY_YEAR));
            calendar.set(Calendar.MONTH, getArguments().getInt(KEY_MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, getArguments().getInt(KEY_DAY));
        }

        setDatePicker(calendar);
    }

    private void initButton(View view) {
        view.findViewById(R.id.btn_today).setOnClickListener(this);
        view.findViewById(R.id.btn_clear).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);
    }


    private void setDatePicker(Calendar calendar) {
        datePicker.updateDate(calendar.get(Calendar.YEAR),
                              calendar.get(Calendar.MONTH),
                              calendar.get(Calendar.DAY_OF_MONTH));
    }

}
