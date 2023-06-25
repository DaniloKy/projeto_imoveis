package com.example.projeto_imoveis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto_imoveis.rv.MainClientes;
import com.example.projeto_imoveis.rv.MainImoveis;


public class Main extends AppCompatActivity implements View.OnClickListener {

    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);

        button = (Button) findViewById(R.id.btnImoveis);
        button2 = (Button) findViewById(R.id.btnClientes);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == this.button){
            Toast.makeText(this, "imoveis click", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainImoveis.class);
            startActivity(intent);
        }else if(view == this.button2){
            Toast.makeText(this, "clientes", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainClientes.class);
            startActivity(intent);
        }
    }
}