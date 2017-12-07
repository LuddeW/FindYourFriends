package com.example.ludwig.findyourfriends;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);

        Thread listenThread = new Thread(new ServerThread1());
        listenThread.start();

    }

    public void sendData(View view) throws IOException, JSONException {
        JSONObject object = new JSONObject("{ ”type”:”register”,”group”:”NAME”, ”member”:”NAME”    }");
        MessageSender sender = new MessageSender();
        sender.execute(parseToJSON(object.toString()));
    }

    class ServerThread1 implements Runnable {
        Socket socket;
        ServerSocket serverSocket;
        InputStreamReader inputStreamReader;
        String message;
        Handler handler = new Handler();

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(0);
                //socket = serverSocket.accept()
                BufferedReader bufferedReader;
                while (true) {
                    socket = new Socket("195.178.227.53", 7117);
                    inputStreamReader = new InputStreamReader(socket.getInputStream());
                    bufferedReader = new BufferedReader(inputStreamReader);
                    message = bufferedReader.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String parseToJSON(String group) throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);
        writer.beginObject().name("type").value("register").name("group").value(group).endObject();
        return stringWriter.toString();
    }
}
