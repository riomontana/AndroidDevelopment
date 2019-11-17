package com.lfo.p2.Controller;

import android.util.Log;
import com.lfo.p2.Activity.MainActivity;
import com.lfo.p2.Fragment.AddGroupDialogFragment;
import com.lfo.p2.Fragment.GroupListDialogFragment;
import com.lfo.p2.ServerConnection.ReceiveListener;
import com.lfo.p2.ServerConnection.TCPConnection;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by LFO on 2017-10-05.
 */

public class Controller {

    private MainActivity mainActivity;
    private GroupListDialogFragment groupListDialogFragment;
    private AddGroupDialogFragment addGroupDialogFragment;
    private TCPConnection connection;
    private boolean connected = false;
    private ReceiveListener listener;
    private String user;
    private String group;
    private double latitude;
    private double longitude;
    private boolean connectedToGroup = false;
    private String groupID;
    private LocationUpdater locUpdater = new LocationUpdater();
    private boolean isLocUpdaterRunning = false;

    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mainActivity.setController(this);
        listener = new RListener();
        connection = new TCPConnection("195.178.227.53", 7117, listener);
        groupListDialogFragment = new GroupListDialogFragment();
    }

    public void newConnection() {
        connection.connect();
    }

    public void setLoginFragment() {
        mainActivity.showLoginDialog();
    }

    public void setGroupListDialogFragment() {
        updateGroups();
        mainActivity.showGroupListDialog(groupListDialogFragment);
    }

    public void setAddGroupDialogFragment() {
        addGroupDialogFragment = new AddGroupDialogFragment();
        mainActivity.showAddGroupDialog(addGroupDialogFragment);
    }

    public void unregisterGroup(String groupID) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "unregister");
            json.put("id", groupID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        connection.sendJson(json);
    }

    public void registerGroup(String groupName) {
        if(connectedToGroup) {
            unregisterGroup(groupID);
        }
        setGroup(groupName);
        JSONObject json = new JSONObject();
        try {
            json.put("type", "register");
            json.put("group", group);
            json.put("member", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        connection.sendJson(json);
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void welcomeToast() {
        mainActivity.showToast(user);
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void updateGroups() {
        JSONObject json = new JSONObject();
        try {
            json.put("type", "groups");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        connection.sendJson(json);
    }

    public void setUserLocation(double latitude, double longitude) {
        this.latitude =latitude;
        this.longitude =longitude;
        if(connectedToGroup) {
            updateUserLocation();
        }
    }

    public void updateUserLocation() {
        JSONObject json = new JSONObject();
        try {
            json.put("type","location");
            json.put("id", groupID);
            json.put("longitude", String.valueOf(getLongitude()));
            json.put("latitude", String.valueOf(getLatitude()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        connection.sendJson(json);
        Log.d("updateUserLocation", json.toString());
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void logout() {
        connection.disconnect();
        unregisterGroup(groupID);
    }

    public double getLatitude() {
        Log.d("getLat", String.valueOf(latitude));
        return latitude;
    }

    public double getLongitude() {
        Log.d("getLong", String.valueOf(longitude));
        return longitude;
    }

    private class RListener extends Thread implements ReceiveListener {
        @Override
        public void newMessage(final String answer) {
            mainActivity.runOnUiThread(new Runnable() {
                public void run() {
                    String message = answer;
                    Exception e = connection.getException();
                    if ("CONNECTED".equals(answer)) {
                        connected = true;
                    } else if ("CLOSED".equals(answer)) {
                        connected = false;
                    } else if ("EXCEPTION".equals(answer) && e != null) {
                        message = e.toString();
                    }
                }
            });
        }

        @Override
        public void newJsonMessage(final JSONObject jsonAnswer) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonMessage = jsonAnswer;
                    try {
                        if ("groups".equals(jsonAnswer.get("type"))) {
                            Log.d("newJsonMessage", jsonAnswer.toString());
                            groupListDialogFragment.setGroupArray(jsonAnswer);
                        }
                        else if("register".equals(jsonAnswer.get("type"))) {
                            connectedToGroup = true;
                            Log.d("newJsonMessage", jsonAnswer.toString());
                            setGroupID(jsonAnswer.getString("id"));
                            updateGroups();
                            if(!isLocUpdaterRunning) {
                                locUpdater.start();
                            }
                        }
                        else if("unregister".equals(jsonAnswer.get("type"))) {
                            Log.d("newJsonMessage", jsonAnswer.toString());
                        }
                        else if("members".equals(jsonAnswer.get("type"))) {
                            Log.d("newJsonMessage", jsonAnswer.toString());
                        }
                        else if("location".equals(jsonAnswer.get("type"))) {
                            Log.d("newJsonMessage", jsonAnswer.toString());
                        }
                        else if("locations".equals(jsonAnswer.get("type"))) {
                            Log.d("newJsonMessage", jsonAnswer.toString());
                            mainActivity.updateMembersLocations(jsonAnswer);
                        }
                        else if("exception".equals(jsonAnswer.get("type"))) {
                            Log.d("newJsonMessage", jsonAnswer.toString());
                        }
                    } catch (JSONException e1) {
                        Log.d("newJsonMessage", e1.toString());
                    }
                }
            });
        }
    }

    private class LocationUpdater extends Thread {
        @Override
        public void run() {
            isLocUpdaterRunning = true;
            while (connectedToGroup)
                try {
                    updateUserLocation();
                    Log.d("LocationUpdater", "lat: " + getLatitude() + " long: " + getLongitude());
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}