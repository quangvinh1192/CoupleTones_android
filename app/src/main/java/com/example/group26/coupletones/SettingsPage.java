package com.example.group26.coupletones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class  SettingsPage extends AppCompatActivity {

    NotificationControl notificationControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationControl = ((Initialize) getApplication() ).getNotificationControl();

        setContentView(R.layout.activity_settings_page);
    }


    /** sends a notification when vibration is turned off */
    public void onClickVibrateOff( View view ){
        notificationControl.shouldNotVibrate();
        Toast toast = Toast.makeText( getApplicationContext(), "Vibrate turned off" , Toast.LENGTH_SHORT);
        toast.show();
    }

    /** sends a notification when vibration is turned on */
    public void onClickVibrateOn( View view ){
        notificationControl.shouldVibrate();
        Toast toast = Toast.makeText( getApplicationContext(), "Vibrate turned on" , Toast.LENGTH_SHORT);
        toast.show();
    }

    /** sends a notification when sound is turned off */
    public void onClickSoundOff( View view ){
        notificationControl.shouldNotSound();
        Toast toast = Toast.makeText( getApplicationContext(), "Sound turned off" , Toast.LENGTH_SHORT);
        toast.show();
    }

    /** sends a notification when sound is turned on */
    public void onClickSoundOn( View view ){
        notificationControl.shouldSound();
        Toast toast = Toast.makeText( getApplicationContext(), "Sound turned on" , Toast.LENGTH_SHORT);
        toast.show();
    }
}
