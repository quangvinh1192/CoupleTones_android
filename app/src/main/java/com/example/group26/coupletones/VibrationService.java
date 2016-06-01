package com.example.group26.coupletones;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class VibrationService extends Service {

    Intent intent;

    public VibrationService() {
    }

    final class VibrationThread implements Runnable{
        int startId;

        public VibrationThread(int startId){
            this.startId = startId;
        }

        @Override
        public void run(){
            synchronized (this){

                vibrate();

                try{
                    wait(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                stopSelf(startId);
            }
        }
    }


    public void vibrate(){
        String vibration_selected = (String) intent.getExtras().get("vibration selected");
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //TODO remove Toasts
        if(vibrator.hasVibrator()) {
            Log.v("Can Vibrate", "YES");

            switch (vibration_selected) {
                case "2 short vibrations":
                    long[] pattern = {0, 300, 200, 300};
                    vibrator.vibrate(pattern, -1);
                    break;
                case "1 short vibration":
                    long[] pattern2 = {0, 1000};
                    vibrator.vibrate(pattern2, -1);
                    break;
                case "1 long vibration":
                    long[] pattern3 = {0, 2000};
                    vibrator.vibrate(pattern3, -1);
                    break;
            }
        }else{
            Log.v("Can Vibrate", "NO");
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        this.intent = intent;
        Thread thread = new Thread(new VibrationThread(startId));
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
