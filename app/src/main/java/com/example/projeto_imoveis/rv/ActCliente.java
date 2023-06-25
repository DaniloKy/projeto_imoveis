package com.example.projeto_imoveis.rv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projeto_imoveis.R;

public class ActCliente extends Activity {

    TextView textNome, textIdade;
    ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cliente);

        textNome = (TextView) findViewById(R.id.txtNome);
        textIdade = (TextView) findViewById(R.id.txtIdade);

        Intent intent = getIntent();

        String nome = intent.getStringExtra("nome");
        String idade = intent.getStringExtra("idade");
        //String imagem = intent.getStringExtra("imagem");
        boolean bool = intent.getBooleanExtra("bool", false);

        textNome.setText(nome);
        textIdade.setText(idade);
        //imagem.setImageUrl();
    }

}