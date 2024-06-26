package com.example.projeto_imoveis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto_imoveis.bd.DatabaseHelper;
import com.example.projeto_imoveis.classes.Caracteristica;
import com.example.projeto_imoveis.classes.Imovel;

import java.util.HashMap;


public class ManageImoveis extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper db;
    int id, idCaracteristica;
    String descricao;
    boolean isUpdate = false;
    Button button;
    EditText edit_descricao, edit_tipologia, edit_localizacao, edit_url;
    CheckBox checkSauna, checkAreacomum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_imoveis);

        edit_descricao = (EditText) findViewById(R.id.descricao);
        edit_tipologia = (EditText) findViewById(R.id.tipologia);
        edit_localizacao = (EditText) findViewById(R.id.localizacao);
        edit_url = (EditText) findViewById(R.id.url);
        checkSauna = (CheckBox) findViewById(R.id.checkSauna);
        checkAreacomum = (CheckBox) findViewById(R.id.checkAreacomum);
        button = (Button) findViewById(R.id.createBtn);
        button.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent.hasExtra("id") && intent.hasExtra("idCaracteristica")) {
            isUpdate = true;
            id = intent.getIntExtra("id", -1);
            descricao = intent.getStringExtra("descricao");
            edit_descricao.setText(intent.getStringExtra("descricao"));
            edit_tipologia.setText(intent.getStringExtra("tipologia"));
            edit_localizacao.setText(intent.getStringExtra("localizacao"));
            idCaracteristica =  intent.getIntExtra("idCaracteristica", -1);
            checkSauna.setChecked(intent.getBooleanExtra("hasSauna", false));
            checkAreacomum.setChecked(intent.getBooleanExtra("hasAreacomum", false));
            button.setText("Save");
        }

    }

    @Override
    public void onClick(View view) {
        if(view == this.button){
            if(!isUpdate) {
                Imovel imovel = getInputValues();
                Log.e("IMOVE A CRIRAR", imovel.toString());
                db = new DatabaseHelper(getApplicationContext());
                if(!db.checkImovel(imovel.descricao))
                    db.criarImovel(imovel);
                db.fecharDB();
                finish();
            }else{
                int status = -1;
                Imovel imovel = getInputValues();
                Log.i("IMOVEL:", imovel.toString());
                db = new DatabaseHelper(getApplicationContext());
                //if(descricao == imovel.descricao)
                    //status = db.atualizarImovel(imovel);
                //else if (!db.checkImovel(imovel.descricao))
                    status = db.atualizarImovel(imovel);
                db.fecharDB();
                Log.i("ATUALIZACAO STATUS", "Status: "+String.valueOf(status));
                finish();
            }
        }
    }

    private Imovel getInputValues(){
        String descricao = edit_descricao.getText().toString();
        String tipologia = edit_tipologia.getText().toString();
        String localizacao = edit_localizacao.getText().toString();
        String url = edit_url.getText().toString();
        String sauna = checkSauna.isChecked() ? "sim" : "nao";
        String areacomum = checkAreacomum.isChecked() ? "sim" : "nao";
        Caracteristica caracteristica = new Caracteristica(sauna, areacomum);
        caracteristica.setId(this.idCaracteristica);
        Imovel imovel = new Imovel(descricao, tipologia, localizacao, url);
        imovel.setId(this.id);
        imovel.caracteristicas = caracteristica;
        return imovel;
    }
}