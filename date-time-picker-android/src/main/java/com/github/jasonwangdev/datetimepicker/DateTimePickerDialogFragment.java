package com.github.jasonwangdev.datetimepicker;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
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

/**
 * Created by jason on 2017/6/28.
 */

public class DateTimePickerDialogFragment extends DialogFragment {

    private static final int MODE_DATE_TIME = 0x01;
    private static final int MODE_DATE = 0x02;
    private static final int MODE_TIME = 0x03;

    private static final String KEY_MODE = "Mode";

    private Calendar calendarDate;

    private DatePicker datePicker;
    private TimePicker timePicker;


    public static DateTimePickerDialogFragment getDateTimeInstance() {
        return getInstance(MODE_DATE_TIME);
    }

    public static DateTimePickerDialogFragment getDateInstance() {
        return getInstance(MODE_DATE);
    }

    public static DateTimePickerDialogFragment getTimeInstance() {
        return getInstance(MODE_TIME);
    }


    private static DateTimePickerDialogFragment getInstance(int mode) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_MODE, mode);

        DateTimePickerDialogFragment dialog = new DateTimePickerDialogFragment();
        dialog.setArguments(bundle);

        return dialog;
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
                ((TextView) view.findViewById(R.id.tv_title)).setText(getResources().getString(R.string.DateTimeDialogFragment_Title_SelectDateTime));
                break;

            case MODE_DATE:
                ((TextView) view.findViewById(R.id.tv_title)).setText(getResources().getString(R.string.DateTimeDialogFragment_Title_SelectDate));
                break;

            case MODE_TIME:
                ((TextView) view.findViewById(R.id.tv_title)).setText(getResources().getString(R.string.DateTimeDialogFragment_Title_SelectTime));
                break;
        }

        ((Button) view.findViewById(R.id.btn_today)).setText(getResources().getString(R.string.DateTimeDialogFragment_Button_Today));
        ((Button) view.findViewById(R.id.btn_cancel)).setText(getResources().getString(R.string.DateTimeDialogFragment_Button_Cancel));
        ((Button) view.findViewById(R.id.btn_ok)).setText(getResources().getString(R.string.DateTimeDialogFragment_Button_Ok));

        return view;
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

    @Override
    public void onResume() {
        setDialogSize();

        super.onResume();
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

}
