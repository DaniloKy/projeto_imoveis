package com.example.projeto_imoveis.volley;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.projeto_imoveis.bd.DatabaseHelper;
import com.example.projeto_imoveis.classes.Client;
import com.example.projeto_imoveis.classes.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GetUserDataVolley implements Response.ErrorListener, Response.Listener<JSONObject>{

    protected MainVolleyInterface mainVolleyInterface;
    protected int result = Activity.RESULT_OK;
    protected Context context;

    public GetUserDataVolley(Context context, MainVolleyInterface mainVolleyInterface) {
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
            JSONArray jsonUsers = response.getJSONArray("users");
            for (int i = 0; i < jsonUsers.length(); i++){
                User user = new User();
                JSONObject jsonPessoa = jsonUsers.getJSONObject(i);
                user.user = jsonPessoa.getString("user");
                user.setPassword(jsonPessoa.getString("password"));
                DatabaseHelper db = new DatabaseHelper(this.context);
                if(!db.checkUser(user.user))
                    db.criarUser(user);
                db.fecharDB();
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
