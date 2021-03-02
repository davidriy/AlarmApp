package com.example.alarmapp_v1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.TimeZone;

public class NewAlarm extends AppCompatActivity {
    // Data variables
    Alarm alarm;
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
    // Component variables
    AlertDialog.Builder dialogDays;
    Button repeatBtn;
    ImageButton saveBtn;
    TimePicker timePicker;
    EditText alarmName;
    // Controla
    boolean alarmSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alarm);
        // Action bar back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        manager();
    }

    // Override del método que se ejecuta cuando pulsamos el botón de back - devuelve a la MainActivity
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void manager(){
        alarm = new Alarm("", true, "10", "22", new DaysOfWeek(false, false, false, false, false, false, false));
        alarmSaved = false;
        instanciateDialogDays();
        manageInputName();
        manageTimePicker();
        manageRepeatBtn();
        manageSaveBtn();
    }
    public void manageInputName(){
        alarm.setTitle("Alarm");
        alarmName = findViewById(R.id.alarmName);
        alarmName.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    alarm.setTitle(s.toString());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    public void manageRepeatBtn(){
        repeatBtn = findViewById(R.id.repeatBtn);
        repeatBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        openDialog();
                    }
                }
        );
    }
    public int getActualDayOfWeek(){
        // Obtener dia de la semana - menos uno porque los ingleses cuentan a partir del domingo
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK ) - 1;
        return dayOfWeek;
    }
    // Manages save button
    public void manageSaveBtn(){
        // If the alarm has not been saved already
        if(!alarmSaved){
            alarmSaved = true;
            // Se resta uno para cumplir con el índice base 1
            final int dayOfWeek = getActualDayOfWeek() - 1;
            final int nextDay = dayOfWeek + 1;
            saveBtn = findViewById(R.id.saveBtn);
            saveBtn.setOnClickListener(
                    new View.OnClickListener(){
                        // Required API for LocalTime
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(View v) {
                            if(isAllDaysFalse()){
                                // Si no se selecciona un dia que ponga el dia actual si la hora no ha pasado y si ha pasado que sea la del dia siguiente
                                if(selectedTimeIsAfterActualTime()){
                                    setChecked(true, dayOfWeek);
                                }
                                else {
                                    setChecked(true, nextDay);
                                }
                            }
                            // Finaliza la actividad y envía la alarma a la main
                            finish();
                        }
                    }
            );
        }
    }
    public void manageTimePicker(){
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        // Inicializar timePicker con hora y min actuales
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        // Poner valor en alarma por si no se selecciona ninguna hora ni minuto
        alarm.setHour(convertIntToString(hour));
        alarm.setMinute(convertIntToString(minute));
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                alarm.setHour(convertIntToString(hourOfDay));
                alarm.setMinute(convertIntToString(minute));
                Log.d("TIEMPO ", alarm.getFullTime());
            }
        });
    }
    // Si el número es de solo un dígito pone un 0 a la izquierda
    public String convertIntToString(int number){
        int length = String.valueOf(number).length();
        if(length == 1){
            return "0" + String.valueOf(number);
        } else {
            return String.valueOf(number);
        }
    }
    public void openDialog(){
        dialogDays.show();
    }
    public void instanciateDialogDays(){
        final String[] multiChoiceItems = getResources().getStringArray(R.array.day_select_choice_array);
        final boolean[] checkedItems = {false, false, false, false, false, false, false};
        dialogDays = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.repeat_title))
                .setMultiChoiceItems(multiChoiceItems, checkedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked){
                                    setChecked(true, which);
                                } else {
                                    setChecked(false, which );
                                }
                            }
                        });
    }

    // Checkea los items del array
    public void setChecked(boolean status, int which){
        switch (which){
            case 0:
                alarm.daysOfWeek.setDay1(status);
                break;
            case 1:
                alarm.daysOfWeek.setDay2(status);
                break;
            case 2:
                alarm.daysOfWeek.setDay3(status);
                break;
            case 3:
                alarm.daysOfWeek.setDay4(status);
                break;
            case 4:
                alarm.daysOfWeek.setDay5(status);
                break;
            case 5:
                alarm.daysOfWeek.setDay6(status);
                break;
            case 6:
                alarm.daysOfWeek.setDay7(status);
                break;
        }
    }
    public boolean isAllDaysFalse(){
        boolean allFalse = true;
        if (alarm.daysOfWeek.isDay1()) allFalse = false;
        if (alarm.daysOfWeek.isDay2()) allFalse = false;
        if (alarm.daysOfWeek.isDay3()) allFalse = false;
        if (alarm.daysOfWeek.isDay4()) allFalse = false;
        if (alarm.daysOfWeek.isDay5()) allFalse = false;
        if (alarm.daysOfWeek.isDay6()) allFalse = false;
        if (alarm.daysOfWeek.isDay7()) allFalse = false;
        return allFalse;
    }
    // Required api for localtime
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean selectedTimeIsAfterActualTime() {
        LocalTime actualTime = LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        LocalTime alarmTime = LocalTime.of(Integer.valueOf(alarm.getHour()), Integer.valueOf(alarm.getMinute()));
        return alarmTime.isAfter(actualTime) ? true : false;
    }

    // Acaba y envia la nueva alarma a la main activity
    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("ALARM", alarm);
        setResult(RESULT_OK, data);
        super.finish();
    }

}