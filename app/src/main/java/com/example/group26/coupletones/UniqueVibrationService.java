package com.example.group26.coupletones;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class UniqueVibrationService extends Service {

    Vibrator vibrator2; // Vibrator for the unique vibration

    private final long[] two_short_vibrations = {0, 300, 200, 300};
    private final long[] one_short_vibration = {0, 1000};
    private final long[] one_long_vibration = {0, 2000};

    private String vibration_type;

    public UniqueVibrationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    final class VibrationThread implements Runnable{

        int startId;
        private String vibration_type;

        public VibrationThread( int startId ){
            this.startId = startId;
        }

        @Override
        public void run(){
            synchronized (this){

            try {
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            vibrateUnique();//plays the unique sound

            try{
                wait( 5000 );
            }catch ( InterruptedException e){
                e.printStackTrace();
            }

            stopSelf();
            }
        }
    }

    public void vibrateUnique(){

        if( vibrator2.hasVibrator() ) {

            Log.v("Can Vibrate", "YES");

            if (vibration_type.equals("2 short vibrations")) {

                vibrator2.vibrate(two_short_vibrations, -1);
            } else if (vibration_type.equals("1 short vibration")) {

                vibrator2.vibrate(one_short_vibration, -1);
            } else if (vibration_type.equals("1 long vibration")) {

                vibrator2.vibrate(one_long_vibration, -1);
            }

        }else{
            Log.v("Can Vibrate", "NO");
        }

    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId ){

        vibrator2 =  (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        vibration_type = (String)intent.getExtras().get( "vibration_type");

        Thread vibrationThread = new Thread( new VibrationThread( startId ) );
        vibrationThread.start();

        return super.onStartCommand( intent, flags, startId );

    }


}
