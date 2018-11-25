package trikita.talalarmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Window;
import android.view.WindowManager;

import trikita.anvil.Anvil;
import trikita.anvil.RenderableView;
import trikita.promote.Promote;
import trikita.talalarmo.ui.AlarmLayout;
import trikita.talalarmo.ui.Theme;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends Activity {
    Boolean pluggedIn = false;
    private BatteryReceiver mBatteryReiceiver = new BatteryReceiver();
    private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    @Override
    public void onCreate(Bundle b) {

        super.onCreate(b);

        updateTheme();

        setContentView(new RenderableView(this) {
            public void view() {
                AlarmLayout.view();
            }
        });

        if(mBatteryReiceiver.isPluggedIn())

        {
            BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
            int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            boolean stopcharge = false;
            while (batLevel <= 50) {
                stopcharge = false;
                SystemClock.sleep(60000);
            }
            stopcharge = true;
        }

    }



    @Override
    public void onResume() {
        super.onResume();
        updateTheme();
        Anvil.render();
        registerReceiver(mBatteryReiceiver, mIntentFilter);
        if(mBatteryReiceiver.isPluggedIn())

        {
            BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
            int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            boolean stopcharge = false;
            while (batLevel <= 50) {
                stopcharge = false;
            }
            stopcharge = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBatteryReiceiver);
        if(mBatteryReiceiver.isPluggedIn())

        {
            BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
            int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            boolean stopcharge = false;
            while (batLevel <= 50) {
                stopcharge = false;
            }
            stopcharge = true;
        }
    }


    public void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void updateTheme() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (Theme.get(App.getState().settings().theme()).light) {
                setTheme(android.R.style.Theme_Holo_Light_NoActionBar);
            } else {
                setTheme(android.R.style.Theme_Holo_NoActionBar);
            }
        } else {
            if (Theme.get(App.getState().settings().theme()).light) {
                setTheme(android.R.style.Theme_Material_Light_NoActionBar);
            } else {
                setTheme(android.R.style.Theme_Material_NoActionBar);
            }
        }

        // fill status bar with a theme dark color on post-Lollipop devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Theme.get(App.getState().settings().theme()).primaryDarkColor);
        }
    }

}
