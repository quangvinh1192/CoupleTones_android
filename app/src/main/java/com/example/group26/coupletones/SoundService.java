package com.example.group26.coupletones;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
//TODO Please Write Comments :)

//To use:
// getExtras( "aOrD", "" );       ---> gets whether it is arrival or departure
// getExtras( "sound_type", "" ); ---> gets the sound type
public class SoundService extends Service {

    private MediaPlayer mp;
    String aOrD;
    String sound_type;

    private String arrival = "arrival";
    private String departure = "departure";
    private String classic = "classic";
    private String electribe = "electribe";
    private String music_box = "music box";

    public SoundService() {
    }

    final class SoundThread implements Runnable{

        int startId;

        public SoundThread( int startId ){
            this.startId = startId;
        }

        @Override
        public void run(){
            synchronized (this){

                if( !aOrD.equals("None") ) {
                    playSound(aOrD);//plays arrival or departure

                    try {
                        wait(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                 stopSelf();
            }
        }
    }

    public void playSound( String sound_type ){

        if( sound_type.equals(arrival)){
            mp = MediaPlayer.create( this, R.raw.arrival);
            mp.start();
        }else if( sound_type.equals(departure)){
            mp = MediaPlayer.create( this, R.raw.departure);
            mp.start();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId ){

        aOrD = (String)intent.getExtras().get("aOrD");
        sound_type = (String)intent.getExtras().get( "sound_type");

        Thread soundThread = new Thread( new SoundThread( startId ) );
        soundThread.start();

        return super.onStartCommand( intent, flags, startId );
    }

    @Override
    public void onDestroy(){

        super.onDestroy();
    }

}
