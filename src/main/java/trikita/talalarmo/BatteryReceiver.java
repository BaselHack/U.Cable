package trikita.talalarmo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {
    private boolean pluggedIn = false;

    public boolean isPluggedIn() {
        return pluggedIn;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)){
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String message="";
            switch(status){
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    pluggedIn = true;
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    pluggedIn = false;
                    break;
            }

        }
    }
}
