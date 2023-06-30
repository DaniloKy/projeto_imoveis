package com.example.projeto_imoveis.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projeto_imoveis.Main;

public class BroadcastReceiverData extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            int resultCode = bundle.getInt(ServiceVolley.RESULT);
            if(resultCode == Activity.RESULT_OK){
                Intent intentLogin = new Intent(context, Main.class);
                context.startActivity(intentLogin);
                Toast.makeText(context, "Download complete.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
