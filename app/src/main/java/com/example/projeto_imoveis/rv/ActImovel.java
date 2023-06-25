package com.example.projeto_imoveis.rv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projeto_imoveis.R;

public class ActImovel extends Activity{

    TextView textDescricao, textTipologia, textLocalizacao, hasSauna, hasAreacomum;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_imovel);

        textDescricao = (TextView) findViewById(R.id.descricaoImovel);
        textTipologia = (TextView) findViewById(R.id.tipologiaImovel);
        textLocalizacao = (TextView) findViewById(R.id.localizacaoImovel);
        textLocalizacao = (TextView) findViewById(R.id.localizacaoImovel);
        hasSauna = (TextView) findViewById(R.id.hasSauna);
        hasAreacomum = (TextView) findViewById(R.id.hasAreacomum);
        image = (ImageView) findViewById(R.id.imagemImovel);

        Intent intent = getIntent();
/*
        int id = intent.getIntExtra("id", -1);
        DatabaseHelper db = new DatabaseHelper();
        Imovel im = db.obterImovel(id);
        db.fecharDB();
 */
        String descricao = intent.getStringExtra("descricao");
        String tipologia = intent.getStringExtra("tipologia");
        String localizacao = intent.getStringExtra("localizacao");
        boolean sauna = intent.getBooleanExtra("hasSauna", false);
        boolean areacomum = intent.getBooleanExtra("hasAreacomum", false);
        // image = intent.getStringExtra("image");

        textDescricao.setText(descricao);
        textTipologia.setText(tipologia);
        textLocalizacao.setText(localizacao);

        String temSauna = sauna==true?"Incui":"Nao Inclui";
        String temAreacomum = areacomum==true?"Incui":"Nao Inclui";
        hasSauna.setText(temSauna+": sauna");
        hasAreacomum.setText(temAreacomum+": areacomum");
        //image.set
    }
}