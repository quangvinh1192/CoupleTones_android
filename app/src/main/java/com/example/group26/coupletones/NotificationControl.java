package com.example.group26.coupletones;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by gagan on 5/22/16.
 */
public class NotificationControl {

    private Initialize initialize;
    NotificationCompat.Builder mBuilder;

    public NotificationControl(Initialize initialize){
        this.initialize = initialize;
        mBuilder = new NotificationCompat.Builder(initialize);

    }


    /**
     * @param arriveOrDepart true if arriving at location, false if departing from location
     * @param place place you are leaving or entering
     * @return  returns true if the notification was successfully sent to the user
     */
    public boolean notify(boolean arriveOrDepart, aFavoritePlace place){
        mBuilder.setSmallIcon(R.drawable.notifications_icon);
        //if the spouse is arriving at a location then set the tittle to "Arriving"
        String message;
        if(arriveOrDepart){
            mBuilder.setContentTitle("Arriving");
            message = "Spouse is arriving at " + place.getName();
        }

        else{
            mBuilder.setContentTitle("Departing");
            message = "Spouse is departing from " + place.getName();
        }

        mBuilder.setContentText(message);
        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) initialize.getSystemService(Context.NOTIFICATION_SERVICE);

//      notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
        return true;
    }

    //3 types of vibrations
    // 3 unique vibrates: "2 short vibrations", "1 short vibration", "1 long vibration"
    // To choose the vibration, pass in any of the above vibration Strings
    public boolean vibrate(  String vibration_type ){
        Intent intent = new Intent(initialize, VibrationService.class);
        intent.putExtra("vibration selected", vibration_type );
        initialize.startActivity(intent);
        //if the phone vibrated return true
        return true;
    }

    //3 types of sounds
    // "classic", "electribe", "music box"
    // To choose the sound, just pass in any of the above Strings
    public boolean playSound( String sound_type ){
        Intent intent = new Intent(initialize, SoundService.class);
        intent.putExtra("sound selected", sound_type);
        initialize.startActivity(intent);
        //if the sound was played pressed return true
        return true;
    }
}
