package com.utility.dup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class BatteryActivity extends AppCompatActivity {

    TextView tlevel, thealth, ttemp_c, tpower_src, tstatus, ttechnology, tvoltage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getApplicationContext().registerReceiver(mBroadCastReceiver, intentFilter);

        giveIdInit();
    }

    private void giveIdInit() {
        tlevel = findViewById(R.id.b_level);
        thealth = findViewById(R.id.b_health);
        ttemp_c = findViewById(R.id.b_temp_c);
        tpower_src = findViewById(R.id.b_power_src);
        tstatus = findViewById(R.id.b_status);
        ttechnology = findViewById(R.id.b_technology);
        tvoltage = findViewById(R.id.b_voltage);
    }

    private BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String charging_status = "", battery_condition = "", power_source = "";
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);

            if (health == BatteryManager.BATTERY_HEALTH_COLD) {
                battery_condition = "cold";
            } else if (health == BatteryManager.BATTERY_HEALTH_DEAD) {
                battery_condition = "Dead";
            } else if (health == BatteryManager.BATTERY_HEALTH_GOOD) {
                battery_condition = "Good";
            } else if (health == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                battery_condition = "Unknown";
            } else if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                battery_condition = "Overheat";
            } else if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                battery_condition = "Over Voltage";
            } else if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                battery_condition = "Unspecified Failure";
            }


            int temp_C = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
            int power_src = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

            if (power_src == BatteryManager.BATTERY_PLUGGED_USB) {
                power_source = "USB";
            } else if (power_src == BatteryManager.BATTERY_PLUGGED_AC) {
                power_source = "AC Adapter";
            } else if (power_src == BatteryManager.BATTERY_PLUGGED_WIRELESS) {
                power_source = "Wireless";
            } else {
                power_source = "Not Connected";
            }

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                charging_status = "Charging";
            } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                charging_status = "Not Charging";
            } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
                charging_status = "Battery Full";
            } else if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                charging_status = "Unknown";
            } else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                charging_status = "Not Charging";
            }
            String technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            tlevel.setText(String.valueOf(level) + "%");
            thealth.setText(battery_condition);
            ttemp_c.setText(String.valueOf(temp_C));
            tpower_src.setText(power_source);
            tstatus.setText(charging_status);
            ttechnology.setText(technology);
            tvoltage.setText(String.valueOf(voltage));

        }
    };
}