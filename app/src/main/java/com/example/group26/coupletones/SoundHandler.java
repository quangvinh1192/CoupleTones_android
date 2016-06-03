package com.example.group26.coupletones;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by kssarabi on 6/3/2016.
 */
public class SoundHandler {

    private Context thisContext;
    private MediaPlayer mp;
    private Initialize initialize;

    private String arrival = "arrival sound";
    private String departure = "departure sound";
    private String classic = "classic";
    private String electribe = "electribe";
    private String music_box = "music_box";

    public SoundHandler(Initialize initialize) {
        this.mp = new MediaPlayer();
        this.initialize = initialize;
    }


    public void playSound( String sound_selected ){

        if( sound_selected.equals(arrival)){
            mp = MediaPlayer.create( initialize, R.raw.arrival);
            mp.start();
        }else if( sound_selected.equals(departure)){
            mp = MediaPlayer.create( initialize, R.raw.departure);
            mp.start();
        }else if( sound_selected.equals( electribe ) ){
            mp = MediaPlayer.create( initialize, R.raw.electribe);
            mp.start();
        }else if( sound_selected.equals(music_box)){
            mp = MediaPlayer.create( initialize, R.raw.musicbox);
            mp.start();
        }else if( sound_selected.equals( classic ) ){
            mp = MediaPlayer.create( initialize, R.raw.classic );
            mp.start();
        }


    }
}
