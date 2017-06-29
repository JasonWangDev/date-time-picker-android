package com.github.jasonwangdev.datetimepicker;

import java.util.Calendar;

/**
 * Created by jason on 2017/6/29.
 */

public interface OnDateTimePickerDialogFragmentClickListener {

    void onDateTimeClear();
    void onDateTimeSet(Calendar calendar);

}
