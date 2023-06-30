package com.example.projeto_imoveis.volley;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projeto_imoveis.bd.DatabaseHelper;
import com.example.projeto_imoveis.classes.Caracteristica;
import com.example.projeto_imoveis.classes.Client;
import com.example.projeto_imoveis.classes.Imovel;
import com.example.projeto_imoveis.classes.User;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetMainDataVolley implements Response.ErrorListener, Response.Listener<JSONObject>{

    protected MainVolleyInterface mainVolleyInterface;
    protected int result = Activity.RESULT_OK;
    protected Context context;

    public GetMainDataVolley(Context context, MainVolleyInterface mainVolleyInterface) {
        this.mainVolleyInterface = mainVolleyInterface;
        this.context = context;
        String url = "https://www.dropbox.com/s/brj3s1aenj07icm/desafio.json?dl=1";
        VolleySingleton mySingleton = VolleySingleton.getInstance(context);
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        mySingleton.addToRequestQueue(jsonObjRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        try{
            JSONArray jsonClientes = response.getJSONObject("clientes").getJSONArray("cliente");
            for (int i = 0; i < jsonClientes.length(); i++){
                Client cliente = new Client();
                JSONObject jsonCliente = jsonClientes.getJSONObject(i);
                cliente.nome = jsonCliente.getString("nome");
                cliente.idade = jsonCliente.getInt("idade");
                cliente.url = jsonCliente.getString("url_foto");
                DatabaseHelper db = new DatabaseHelper(this.context);
                if(!db.checkClient(cliente.nome))
                    db.criarClient(cliente);
                db.fecharDB();
                Log.e("CLient", cliente.toString());
            }
            JSONArray jsonImoveis = response.getJSONObject("imoveis").getJSONArray("imovel");
            for (int i = 0; i < jsonImoveis.length(); i++){
                Imovel imovel = new Imovel();
                JSONObject jsonImovel = jsonImoveis.getJSONObject(i);
                imovel.descricao = jsonImovel.getString("descricao");
                imovel.tipologia = jsonImovel.getString("tipologia");
                imovel.localizacao = jsonImovel.getString("localizacao");
                imovel.url = jsonImovel.getString("url_foto");
                Caracteristica caracteristica = new Caracteristica();
                if(jsonImovel.has("lista_caracteristicas")){
                    JSONArray jsonCaracteristicas = jsonImovel.getJSONArray("lista_caracteristicas");
                    caracteristica.sauna = jsonCaracteristicas.getJSONObject(0).getString("sauna");
                    caracteristica.areacomum = jsonCaracteristicas.getJSONObject(1).getString("areacomum");
                    imovel.caracteristicas = caracteristica;
                }
                DatabaseHelper db = new DatabaseHelper(this.context);
                if(!db.checkImovel(imovel.descricao))
                    db.criarImovel(imovel);
                db.fecharDB();
                Log.e("IMOVEL", imovel.toString());
            }
            result = Activity.RESULT_OK;
            this.mainVolleyInterface.onResponse(result);
        }catch (Exception e){
            e.printStackTrace();
            result = Activity.RESULT_CANCELED;
            this.mainVolleyInterface.onResponse(result);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("ErrorOnVolleyResponse", String.valueOf(error));
        result = Activity.RESULT_CANCELED;
        this.mainVolleyInterface.onResponse(result);
    }
}
