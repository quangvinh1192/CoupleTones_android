package com.example.group26.coupletones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingsPage extends AppCompatActivity {

    NotificationControl notificationControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationControl = ((Initialize) getApplication() ).getNotificationControl();

        setContentView(R.layout.activity_settings_page);
    }

    public void onClickVibrateOff( View view ){
        notificationControl.shouldNotVibrate();
    }

    public void onClickVibrateOn( View view ){
        notificationControl.shouldVibrate();
    }

    public void onClickSoundOff( View view ){
        notificationControl.shouldNotSound();
    }

    public void onClickSoundOn( View view ){
        notificationControl.shouldSound();
    }
}
