package com.example.projeto_imoveis.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.projeto_imoveis.R;

public class MainDataService extends Activity {

    public static final String NOTIFICATION = "com.example.projeto_imoveis.dataService";

    private BroadcastReceiverData broadService = new BroadcastReceiverData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, ServiceVolley.class);
        intent.putExtra("logging", false);
        intent.putExtra("NOTIFICATION", NOTIFICATION);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.loading_page);
        IntentFilter it = new IntentFilter();
        it.addAction(NOTIFICATION);
        it.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadService, it);

    }
}
