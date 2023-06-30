package com.example.projeto_imoveis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto_imoveis.bd.DatabaseHelper;
import com.example.projeto_imoveis.classes.Client;


public class ManageClientes extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper db;
    int id;
    boolean isUpdate = false;
    Button button;
    EditText edit_nome, edit_idade, edit_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_clientes);

        edit_nome = (EditText) findViewById(R.id.nome);
        edit_idade = (EditText) findViewById(R.id.idade);
        edit_url = (EditText) findViewById(R.id.url);
        button = (Button) findViewById(R.id.createBtn);
        button.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.hasExtra("id")) {
            isUpdate = true;
            id = intent.getIntExtra("id", -1);
            edit_nome.setText(intent.getStringExtra("nome"));
            edit_idade.setText(intent.getStringExtra("idade"));
            button.setText("Save");
        }

    }

    @Override
    public void onClick(View view) {
        if(view == this.button){
            if(!isUpdate) {
                Client client = getInputValues();
                db = new DatabaseHelper(getApplicationContext());
                if(!db.checkClient(client.nome))
                    db.criarClient(client);
                db.fecharDB();
                finish();
            }else{
                Client client = getInputValues();
                Log.i("ATUALIZACAO Cliente", client.toString());
                db = new DatabaseHelper(getApplicationContext());
                int status = db.atualizarClient(client);
                db.fecharDB();
                Log.i(" STATUS", String.valueOf(status));
                finish();
            }
        }
    }

    private Client getInputValues(){
        String nome = edit_nome.getText().toString();
        Integer idade = Integer.valueOf(edit_idade.getText().toString());
        String url = edit_url.getText().toString();
        Client client = new Client(nome, idade, url);
        client.setId(this.id);
        return client;
    }
}