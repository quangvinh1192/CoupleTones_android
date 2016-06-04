package com.example.group26.coupletones;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

public class VibrationService extends Service {

    private String aOrD;
    private String vibration_type;

    Vibrator vibrator; // Vibrator object to do actual vibrating

    private final long[] arrivalPattern = {0, 100};
    private final long[] departurePattern = {0, 500, 100, 500};
    private final long[] two_short_vibrations = {0, 300, 200, 300};
    private final long[] one_short_vibration = {0, 1000};
    private final long[] one_long_vibration = {0, 2000};

    public VibrationService() {
    }

    final class VibrationThread implements Runnable{

        int startId;

        public VibrationThread( int startId ){
            this.startId = startId;
        }

        @Override
        public void run(){
            synchronized (this){

                if( !aOrD.equals("None") ) {
                    vibrate(aOrD);//plays arrival or departure

                    try {
                        wait(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                vibrate(vibration_type);//plays the unique sound

                try{
                    wait( 5000 );
                }catch ( InterruptedException e){
                    e.printStackTrace();
                }

                stopSelf();
            }
        }
    }

    //TODO switch to switch case
    public void vibrate( String vibration_type ){

        if(vibrator.hasVibrator()) {
            Log.v("Can Vibrate", "YES");
            if( vibration_type.equals( "2 short vibrations" ) ) {

                vibrator.vibrate( two_short_vibrations, -1);
            }else if(vibration_type.equals("1 short vibration")) {

                vibrator.vibrate( one_short_vibration, -1);
            }else if( vibration_type.equals("1 long vibration")) {

                vibrator.vibrate( one_long_vibration, -1);
            }else if( vibration_type.equals("arrival")){

                vibrator.vibrate( arrivalPattern, -1);
            }else if( vibration_type.equals("departure")){

                vibrator.vibrate( departurePattern, -1 );
            }else{
                Log.d( "vibration set to: ", vibration_type );
            }
        }else{
            Log.v("Can Vibrate", "NO");
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId ){

        vibrator =  (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        aOrD = (String)intent.getExtras().get( "aOrD" );
        vibration_type = (String)intent.getExtras().get( "vibration_type");

        Thread vibrationThread = new Thread( new VibrationThread( startId ) );
        vibrationThread.start();

        return super.onStartCommand( intent, flags, startId );
    }
}
