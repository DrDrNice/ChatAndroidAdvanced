package com.example.chatandroidadvanced.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.chatandroidadvanced.BuildConfig;
import com.example.chatandroidadvanced.viewmodel.MessageViewModel;

public class CustomReceiver extends BroadcastReceiver {

    private static final String ACTION_CUSTOM_BROADCAST = "ACTION_CUSTOM_BROADCAST";
MessageViewModel mMessageViewModel;

public CustomReceiver(MessageViewModel messageViewModel){
    this.mMessageViewModel = messageViewModel;
}

    @Override
    public void onReceive(Context context, Intent intent) {
mMessageViewModel.insert(new Message("3","198", "196","337"));
        Toast.makeText(context,"BroadCast", Toast.LENGTH_SHORT);
    }
}
