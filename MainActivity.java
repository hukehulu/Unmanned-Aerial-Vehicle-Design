package com.example.dronegcs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.amap.api.maps.MapView;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.CameraUpdateFactory;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private AMap aMap;
    private JoystickView joystick;
    private RTSPVideoPlayer videoPlayer;
    private FlightControl flightControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        joystick = findViewById(R.id.joystickView);

        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();

        // 初始化无人机起始位置
        LatLng initPos = new LatLng(39.90960, 116.397228);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initPos, 15));

        // 初始化视频播放（示例 RTSP URL）
        videoPlayer = new RTSPVideoPlayer(this);
        // videoPlayer.play("rtsp://无人机地址/stream", findViewById(R.id.playerView));

        try {
            flightControl = new FlightControl("/dev/ttyUSB0", 115200);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 遥杆监听
        joystick.setOnTouchListener((v, event) -> {
            float x = joystick.getNormalizedX();
            float y = joystick.getNormalizedY();
            // 将 x,y 转为飞控指令发送
            byte[] command = MapModule.generateCommand(x, y);
            try {
                flightControl.sendCommand(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (videoPlayer != null) videoPlayer.stop();
        if (flightControl != null) try { flightControl.close(); } catch (Exception e) {}
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}