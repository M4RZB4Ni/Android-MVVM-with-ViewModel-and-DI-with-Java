package com.marzbani.adjoetask.infrastructure.services;

// UserPresentReceiver.java

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.marzbani.adjoetask.infrastructure.Constants;

public class UserPresentReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("UserPresentReceiver", "Received USER_PRESENT broadcast");
        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {


            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
            sharedPreferences.edit().putLong(Constants.PHONE_UNLOCK_TIME, System.currentTimeMillis()).apply();

        }
    }


}





