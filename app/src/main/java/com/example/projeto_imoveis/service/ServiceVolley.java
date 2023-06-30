package com.example.projeto_imoveis.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.projeto_imoveis.volley.GetMainDataVolley;
import com.example.projeto_imoveis.volley.GetUserDataVolley;
import com.example.projeto_imoveis.volley.MainVolleyInterface;

public class ServiceVolley extends IntentService implements MainVolleyInterface {

    public static final String RESULT = "result";
    public String NOTIFICATION;


    public ServiceVolley() {
        super("ServiceVolley");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Boolean logging = intent.getBooleanExtra("logging", false);
            this.NOTIFICATION = intent.getStringExtra("NOTIFICATION");
            Log.e("NOTTIFICATION", NOTIFICATION);
            if(logging)
                new GetUserDataVolley(this, this);
            else
                new GetMainDataVolley(this, this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onResponse(int response) {
        Intent intent = new Intent(this.NOTIFICATION);
        intent.putExtra(RESULT, response);
        sendBroadcast(intent);
    }
}
