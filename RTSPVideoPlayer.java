package com.example.dronegcs;

import android.content.Context;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class RTSPVideoPlayer {
    private ExoPlayer player;
    private Context context;

    public RTSPVideoPlayer(Context context) {
        this.context = context;
        player = new ExoPlayer.Builder(context).build();
    }

    public void play(String rtspUrl, PlayerView playerView) {
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(rtspUrl);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    public void stop() {
        player.stop();
        player.release();
    }
}