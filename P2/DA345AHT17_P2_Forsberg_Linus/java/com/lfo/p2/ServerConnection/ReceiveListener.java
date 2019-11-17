package com.lfo.p2.ServerConnection;

import org.json.JSONObject;

/**
 * Created by LFO on 2017-10-05.
 */

public interface ReceiveListener {
        void newMessage(String answer);
        void newJsonMessage(JSONObject answer);
}
