package org.webappbooster;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

public abstract class Plugin {

    private Context context;
    private int     connectionId;

    abstract public void execute(int requestId, String action, JSONObject request);

    public void setContext(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    public void setConnectionId(int id) {
        this.connectionId = id;
    }

    public void onCreate(String origin) {
        // Do nothing
    }

    public void onDestroy() {
        // Do nothing
    }

    protected void callActivity(Intent intent) {
        PluginManager.callActivityViaProxy(this, intent);
    }

    public void resultFromActivity(int resultCode, Intent data) {
        // Do nothing
    }

    protected void sendResult(int requestId, JSONObject result) {
        try {
            result.put("id", requestId);
            BoosterService.getService().sendResult(connectionId, result.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (context instanceof ProxyActivity) {
            ((ProxyActivity) context).finish();
        }
    }

    protected void runInContextOfProxyActivity() {
        PluginManager.runViaProxy(this);
    }

    public void callbackFromProxy() {
        // Do nothing
    }
}
