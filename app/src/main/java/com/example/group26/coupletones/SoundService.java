package com.example.group26.coupletones;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class SoundService extends Service {
    Intent intent;
    MediaPlayer mp;

    public SoundService() {
    }

    final class SoundThread implements Runnable{
        int startId;

        public SoundThread(int startId){
            this.startId = startId;
        }

        @Override
        public void run(){
            synchronized (this){

                playSound();

                try{
                    wait(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                stopSelf(startId);
            }
        }
    }

    //TODO Remove the toast and actually play the sounds
    public void playSound(){
        String sound_selected = (String) intent.getExtras().get("sound selected");

        switch (sound_selected) {
            case "sound 1":
                /*TODO will uncomment once the sounds are downloaded
                mp = MediaPlayer.create(this, R.raw.sound1);
                mp.start();
                 */
                break;
            case "sound 2":
                /*TODO will uncomment once the sounds are downloaded
                mp = MediaPlayer.create(this, R.raw.sound1);
                mp.start();
                 */
                break;
            case "sound 3":
                /*TODO will uncomment once the sounds are downloaded
                mp = MediaPlayer.create(this, R.raw.sound1);
                mp.start();
                 */
                break;
        }


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        this.intent = intent;
        Thread thread = new Thread(new SoundThread(startId));
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
