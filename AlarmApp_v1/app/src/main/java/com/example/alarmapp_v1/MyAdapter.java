package com.example.alarmapp_v1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<Alarm> {
    static ArrayList<Alarm> alarms;
    public MyAdapter(@NonNull Context context, ArrayList<Alarm> alarmas) {
        super(context, R.layout.row, alarmas);
        alarms = alarmas;
    }
    Alarm actualItem;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        {
            Alarm item = (Alarm) getItem(position);
            actualItem = item;
            LayoutInflater layoutInflater = (LayoutInflater)LayoutInflater.from(getContext());
            View row = layoutInflater.inflate(R.layout.row, parent, false);

            TextView titleText = row.findViewById(R.id.alarmText);
            TextView timeText = row.findViewById(R.id.alarmTime);
            TextView daysText = row.findViewById(R.id.alarmDays);
            Switch switchButton = row.findViewById(R.id.alarmActive);
            // Set resources
            titleText.setText(item.title);
            timeText.setText(item.getFullTime());
            daysText.setText(getShortDaysOfWeek(item));
            switchButton.setChecked(item.isActive());
            //
            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
            return row;

        }
    }

    public String getShortDaysOfWeek(Alarm alarm){
        String daysShortString = "";
        daysShortString += alarm.daysOfWeek.isDay1() ? getContext().getString(R.string.day_1_short) : "";
        daysShortString += alarm.daysOfWeek.isDay2() ? getContext().getString(R.string.day_2_short) : "";
        daysShortString += alarm.daysOfWeek.isDay3() ? getContext().getString(R.string.day_3_short) : "";
        daysShortString += alarm.daysOfWeek.isDay4() ? getContext().getString(R.string.day_4_short) : "";
        daysShortString += alarm.daysOfWeek.isDay5() ? getContext().getString(R.string.day_5_short) : "";
        daysShortString += alarm.daysOfWeek.isDay6() ? getContext().getString(R.string.day_6_short) : "";
        daysShortString += alarm.daysOfWeek.isDay7() ? getContext().getString(R.string.day_7_short): "";
        return daysShortString;
    }

}
