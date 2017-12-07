package com.example.ludwig.findyourfriends;

import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Ludwig on 2017-12-07.
 */

public class ServerThread implements Runnable{
    Socket socket;
    ServerSocket serverSocket;
    InputStreamReader inputStreamReader;
    String message;
    Handler handler = new Handler();

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(7117);
            BufferedReader bufferedReader;
            while (true){
                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                message = bufferedReader.readLine();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
