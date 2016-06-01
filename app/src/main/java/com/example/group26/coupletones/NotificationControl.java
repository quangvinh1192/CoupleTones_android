package com.example.group26.coupletones;

import android.content.Intent;

/**
 * Created by gagan on 5/22/16.
 */
public class NotificationControl {

    private Initialize initialize;

    public void NotificationControl(Initialize initialize){
        this.initialize = initialize;
    }

    //3 types of vibrations
    // 3 unique vibrates: "2 short vibrations", "1 short vibration", "1 long vibration"
    // To choose the vibration, pass in any of the above vibration Strings
    public void vibrate(  String vibration_type ){
        Intent intent = new Intent(initialize, VibrationService.class);
        intent.putExtra("vibration selected", vibration_type );
        initialize.startActivity(intent);
    }

    //3 types of sounds
    // "classic", "electribe", "music box"
    // To choose the sound, just pass in any of the above Strings
    public void playSound( String sound_type ){
        Intent intent = new Intent(initialize, SoundService.class);
        intent.putExtra("sound selected", sound_type);
        initialize.startActivity(intent);
    }
}
