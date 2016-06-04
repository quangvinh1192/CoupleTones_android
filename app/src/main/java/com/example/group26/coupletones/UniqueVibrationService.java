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
    private final long[] three_vibrations = {0, 200, 100, 200, 100, 200};
    private final long[] short_long_short = {0, 100, 100, 500, 100, 100};
    private final long[] quickening = {0, 500, 50, 250, 50, 100, 50, 50};
    private final long[] slowing_down = {0, 50, 50, 100, 50, 250, 50, 500};
    private final long[] rapid_fire = {0, 50, 10, 50, 10, 50, 10, 50, 10, 50};
    private final long[] final_fantasy = {0, 50, 100, 50, 100, 50, 100, 400, 100, 300, 100, 350, 200, 100, 100, 50, 600};
    private final long[] special = {0, 150, 50, 75, 50, 75, 50, 150, 50, 75, 50, 75, 50, 300};

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



            switch (vibration_type) {

                case "2 short vibrations":
                    vibrator2.vibrate(two_short_vibrations, -1);
                    break;

                case "1 short vibration":
                    vibrator2.vibrate(one_short_vibration, -1);
                    break;

                case "1 long vibration":
                    vibrator2.vibrate(one_long_vibration, -1);
                    break;

                case "3 vibrations":
                    vibrator2.vibrate(three_vibrations, -1);
                    break;

                case "short long short":
                    vibrator2.vibrate(short_long_short, -1);
                    break;

                case "quickening":
                    vibrator2.vibrate(quickening, -1);
                    break;

                case "slowing down":
                    vibrator2.vibrate(slowing_down, -1);
                    break;

                case "rapid fire":
                    vibrator2.vibrate(rapid_fire, -1);
                    break;

                case "final fantasy":
                    vibrator2.vibrate(final_fantasy, -1);
                    break;

                case "none":
                    break;

                default:
                    vibrator2.vibrate(special, -1);
                    break;
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
