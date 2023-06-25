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

import com.example.projeto_imoveis.ManageImoveis;
import com.example.projeto_imoveis.R;
import com.example.projeto_imoveis.bd.DatabaseHelper;
import com.example.projeto_imoveis.classes.Caracteristica;
import com.example.projeto_imoveis.classes.Imovel;

import java.util.List;

public class MainImoveis extends Activity implements View.OnClickListener{

    TextView txtNone;
    DatabaseHelper db;
    Button button;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rvimoveis);
        txtNone = (TextView) findViewById(R.id.noImoveis);
        button = (Button) findViewById(R.id.btnImovel);
        button.setOnClickListener(this);

        this.rv = (RecyclerView) findViewById(R.id.rvimoveis);
        this.rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabaseHelper(getApplicationContext());
        List<Imovel> imoveis = db.obterImoveis();
        db.fecharDB();
        Log.i("imoveis", imoveis.toString());
        if(imoveis.size() <= 0){
            txtNone.setText("Imoveis not found");
        }else {
            ImovelAdapter imAdapter = new ImovelAdapter(imoveis);
            this.rv.setAdapter(imAdapter);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == this.button){
            Toast.makeText(this, "create new imovel", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ManageImoveis.class);
            startActivity(intent);
        }
    }

}