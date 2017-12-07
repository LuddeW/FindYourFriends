package com.example.ludwig.findyourfriends;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Ludwig on 2017-12-07.
 */

public class MessageSender extends AsyncTask<String, Void, Void> {
    Socket socket;
    PrintWriter printWriter;
    @Override
    protected Void doInBackground(String... voids) {
        try {
            String message = voids[0];
            socket = new Socket("195.178.227.53", 7117);
            printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.write(message);

            printWriter.flush();
            printWriter.close();
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            message = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
