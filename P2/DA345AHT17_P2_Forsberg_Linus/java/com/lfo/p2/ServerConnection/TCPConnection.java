package com.lfo.p2.ServerConnection;

import android.util.Log;
import org.json.JSONObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by LFO on 2017-10-05.
 */

public class TCPConnection {

    private RunOnThread thread;
    private ReceiveListener rListener;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private InetAddress address;
    private int port;
    private String ip;
    private Exception exception;
    private ReceiveJson receiveJson;


    public TCPConnection(String ip, int port, ReceiveListener rListener) {
        this.ip = ip;
        this.port = port;
        this.rListener = rListener;
        thread = new RunOnThread();
    }

    public void connect() {
        thread.start();
        thread.execute(new Connect());
    }

    public void disconnect() {
        thread.execute(new Disconnect());
    }

    public void sendJson(JSONObject json) {
        thread.execute(new SendJson(json));
    }

//    public void receiveJson() {
//        thread.execute(receiveJson);
//    }

    public Exception getException() {
        Exception result = exception;
        exception = null;
        return result;
    }

    private class ReceiveJson extends Thread {
        public void run() {
            while (receiveJson != null)
            try {
                String message = input.readUTF();
                Log.d("ReceiveJson", message);
                JSONObject json = new JSONObject(message);
                rListener.newJsonMessage(json);

            } catch(Exception e) {
                Log.d("ReceiveJson", e.toString());
            }
        }
    }

    private class SendJson implements Runnable {
        private JSONObject json;

        public SendJson(JSONObject json) {
            this.json = json;
        }

        public void run() {
                try {
                    output.writeUTF(json.toString());
                    output.flush();
                    Log.d("SendJson", json.toString());
                } catch (IOException e) {
                    exception = e;
                    rListener.newMessage("EXCEPTION");
                }
        }
    }

    private class Connect implements Runnable {
        public void run() {
            try {
                Log.d("TCPConnection", "Connect-run");
                address = InetAddress.getByName(ip);
                Log.d("TCPConnection-Connect", "Skapar socket");
                socket = new Socket(address, port);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                output.flush();
                receiveJson = new ReceiveJson();
                receiveJson.start();
                Log.d("TCPConnection-Connect", "Strömmar klara");
                rListener.newMessage("connected");
            } catch (Exception e) {
                Log.d("TCPConnection-Connect", e.toString());
                exception = e;
                rListener.newMessage("exception");
            }
        }
    }

    public class Disconnect implements Runnable {
        public void run() {
            try {
                if (socket != null)
                    socket.close();
                if (input != null)
                    input.close();
                if (output != null)
                    output.close();
                thread.stop();
                rListener.newMessage("CLOSED");
            } catch (IOException e) {
                exception = e;
                rListener.newMessage("EXCEPTION");
            }
        }
    }
}

