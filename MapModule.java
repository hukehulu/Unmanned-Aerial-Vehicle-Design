package com.example.dronegcs;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;

public class MapModule {

    private AMap aMap;

    public MapModule(AMap map) {
        this.aMap = map;
    }

    public void updateDroneLocation(double lat, double lon) {
        LatLng pos = new LatLng(lat, lon);
        aMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
    }

    public static byte[] generateCommand(float x, float y) {
        // 简单示例，将 -1~1 的 x,y 转为字节指令
        byte cmdX = (byte)((x + 1) * 127);
        byte cmdY = (byte)((y + 1) * 127);
        return new byte[]{cmdX, cmdY};
    }
}