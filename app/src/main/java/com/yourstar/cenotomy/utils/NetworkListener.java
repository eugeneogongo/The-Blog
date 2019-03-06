package com.yourstar.cenotomy.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yourstar.cenotomy.Constants;

import org.greenrobot.eventbus.EventBus;

public class NetworkListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       if(Netinfo.isOnline(context)){
           Log.i(NetworkListener.class.getCanonicalName(),Constants.NetworkOn);
           EventBus.getDefault().post(Constants.NetworkOn);
       }
    }
}
