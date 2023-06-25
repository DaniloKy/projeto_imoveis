package com.example.projeto_imoveis.rv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeto_imoveis.ManageClientes;
import com.example.projeto_imoveis.R;
import com.example.projeto_imoveis.bd.DatabaseHelper;
import com.example.projeto_imoveis.classes.Client;
import com.example.projeto_imoveis.classes.Imovel;

import java.util.ArrayList;
import java.util.List;

public class MainClientes extends Activity implements View.OnClickListener{

    RecyclerView rv;
    TextView txtNone;
    DatabaseHelper db;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rvclientes);
        txtNone = (TextView) findViewById(R.id.noClientes);
        button = (Button) findViewById(R.id.btnClientes);
        button.setOnClickListener(this);

        this.rv = (RecyclerView) findViewById(R.id.rvclietes);
        this.rv.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(getApplicationContext());
        List<Client> clientes = db.obterClientes();
        db.fecharDB();
        if(clientes.size() <= 0) {
            txtNone.setText("Clientes not found");
        }else{
            ClienteAdapter clAdapter = new ClienteAdapter(clientes);
            this.rv.setAdapter(clAdapter);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == this.button){
            Intent intent = new Intent(this, ManageClientes.class);
            startActivity(intent);
        }
    }
}