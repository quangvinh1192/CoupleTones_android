package com.example.group26.coupletones;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class UniqueSoundService extends Service {

    MediaPlayer mp;

    private String sound_type;

    public UniqueSoundService() {
    }

    final class SoundThread implements Runnable{

        int startId;

        public SoundThread( int startId ){
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

                playSound();//plays the unique sound

                try{
                    wait( 5000 );
                }catch ( InterruptedException e){
                    e.printStackTrace();
                }

                stopSelf();
            }
        }
    }

    public void playSound(){

        switch ( sound_type ){
            case "classic":
                mp = MediaPlayer.create( this, R.raw.classic );
                mp.start();
                break;
            case "alarm clock":
                mp = MediaPlayer.create( this, R.raw.alarmclock );
                mp.start();
                break;
            case "doorchime":
                mp = MediaPlayer.create( this, R.raw.doorchime );
                mp.start();
                break;
            case "electribe":
                mp = MediaPlayer.create( this, R.raw.electribe );
                mp.start();
                break;
            case "electron beat":
                mp = MediaPlayer.create( this, R.raw.electronicbeat );
                mp.start();
                break;
            case "horn":
                mp = MediaPlayer.create( this, R.raw.horn );
                mp.start();
                break;
            case "invasion":
                mp = MediaPlayer.create( this, R.raw.invasion );
                mp.start();
                break;
            case "military march":
                mp = MediaPlayer.create( this, R.raw.militarymarch );
                mp.start();
                break;
            case "music box":
                mp = MediaPlayer.create( this, R.raw.musicbox );
                mp.start();
                break;
            default:
                mp = MediaPlayer.create( this, R.raw.stationnotification );
                mp.start();
                break;


        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId ){

        sound_type = (String)intent.getExtras().get( "sound_type");

        Thread soundThread = new Thread( new SoundThread( startId ) );
        soundThread.start();

        return super.onStartCommand( intent, flags, startId );
    }
}
