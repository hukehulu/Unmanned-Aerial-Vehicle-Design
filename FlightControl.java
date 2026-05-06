package com.example.dronegcs;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import android_serialport_api.SerialPort;

public class FlightControl {
    private SerialPort serialPort;
    private OutputStream outputStream;

    public FlightControl(String port, int baudRate) throws IOException {
        serialPort = new SerialPort(new File(port), baudRate, 0);
        outputStream = serialPort.getOutputStream();
    }

    public void sendCommand(byte[] command) throws IOException {
        outputStream.write(command);
        outputStream.flush();
    }

    public void close() throws IOException {
        outputStream.close();
        serialPort.close();
    }
}