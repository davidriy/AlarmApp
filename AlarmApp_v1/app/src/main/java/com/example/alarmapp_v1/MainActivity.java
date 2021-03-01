package com.example.alarmapp_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Component variables
    private MyAdapter adapter;
    private ListView listView;
    private AlertDialog.Builder dialogDays;
    private Intent newAlarmIntent;
    private ArrayList<Alarm> alarms;
    private ImageButton addAlarmBtn;
    private Alarm selectedAlarm;
    private XmlManager xmlManager;
    // Request code
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manage();

    }

    public void manage() {
        xmlManager = new XmlManager(getApplicationContext());
        // Instantiate alarm object
        selectedAlarm = new Alarm("", true, "10", "22", new DaysOfWeek(false, false, false, false, false, false, false));
        // Instantiate new alarm intent
        newAlarmIntent = new Intent(this, NewAlarm.class);
        // Instanciate alarms list
        alarms = xmlManager.obtenerListaAlarma();
        // Manage listView
        listView = findViewById(R.id.alarmList);
        // Instantiate adapter and assign adapter to listview
        adapter = new MyAdapter(this, alarms);
        listView.setAdapter(adapter);
        listView.setLongClickable(true);
        // Manage listView long click
        manageItemLongClick();
        // Instantiate alarm button and set listener
        addAlarmBtn = findViewById(R.id.addAlarmBtn);
        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent for result
                startActivityForResult(newAlarmIntent, REQUEST_CODE);
            }
        });
        // Instantiate delete dialog
        instanciateDeleteDialog();
    }

    public void manageItemLongClick() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAlarm = alarms.get(position);
                openDeleteDialog();
                Toast.makeText(getApplicationContext(), "longClicked", Toast.LENGTH_SHORT);
                return true;
            }
        });
        listView.setClickable(true);
    }

    public void createMaxAlarmsToast() {
        Toast maxAlarmsToast = Toast.makeText(getApplicationContext(), getString(R.string.alert_max_alarm), Toast.LENGTH_SHORT);
        maxAlarmsToast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        maxAlarmsToast.show();
    }
    public void saveAlarm(Alarm alarm) {
        if (adapter.alarms.size() >= 5) {
            createMaxAlarmsToast();
        } else {
            adapter.add(alarm);
            xmlManager.newAlarm(alarm);

        }
    }
    public void openDeleteDialog() {
        dialogDays.show();
    }
    public void deleteAlarm(Alarm alarm){
        adapter.remove(alarm);
        xmlManager.deleteAlarm(alarm);
    }
    public void instanciateDeleteDialog() {
        dialogDays = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_title))
                .setPositiveButton(getString(R.string.OK_btn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAlarm(selectedAlarm);
                    }
                })
                .setNegativeButton(getString(R.string.CANCEL_btn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing happens
                    }
                })
        ;
    }


    // When NewAlarm intent finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("ALARM")) {
                saveAlarm((Alarm) data.getSerializableExtra("ALARM"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}