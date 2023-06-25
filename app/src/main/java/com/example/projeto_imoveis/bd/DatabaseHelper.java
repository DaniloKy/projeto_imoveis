package com.example.projeto_imoveis.bd;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.projeto_imoveis.classes.Caracteristica;
import com.example.projeto_imoveis.classes.Client;
import com.example.projeto_imoveis.classes.Imovel;
import com.example.projeto_imoveis.classes.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bdImoveis";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_CLIENTES = "clientes";
    private static final String TABLE_IMOVEIS = "imoveis";
    private static final String TABLE_CARACTERISTICAS = "caracteristicas";

    //GLOBAL
    private static final String KEY_ID = "id";
    private static final String KEY_URL = "url_foto";

    //USERS
    private static final String KEY_USER = "user";
    private static final String KEY_PASSWORD = "password";

    //CLIENTES
    private static final String KEY_NOME = "nome";
    private static final String KEY_IDADE = "idade";

    //IMOVEIS
    private static final String KEY_DESCRICAO = "descricao";
    private static final String KEY_TIPOLOGIA = "tipologia";
    private static final String KEY_LOCALIZACAO = "localizacao";
    private static final String KEY_CARACT_ID = "caract_id";

    //CARACTERISTICAS
    private static final String KEY_SAUNA = "sauna";
    private static final String KEY_AREACOMUM = "areacomum";


    //Tag para logcat
    private static final String LOG = "DatabaseHelper";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE "+TABLE_USERS+"("+
                    KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    KEY_USER+" TEXT UNIQUE,"+
                    KEY_PASSWORD+" TEXT NOT NULL"+
            " )";

    private static final String CREATE_TABLE_CLIENTES =
            "CREATE TABLE "+TABLE_CLIENTES+"("+
                    KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    KEY_NOME+" TEXT NOT NULL,"+
                    KEY_IDADE+" INTEGER NOT NULL,"+
                    KEY_URL+" TEXT"+
            " )";

    private static final String CREATE_TABLE_IMOVEIS =
            "CREATE TABLE "+TABLE_IMOVEIS+"("+
                    KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    KEY_DESCRICAO+" TEXT NOT NULL,"+
                    KEY_TIPOLOGIA+" TEXT NOT NULL,"+
                    KEY_LOCALIZACAO+" TEXT NOT NULL,"+
                    KEY_URL+" TEXT,"+
                    KEY_CARACT_ID+" INTEGER"+

            " )";
    private static final String CREATE_TABLE_CARACTERISTICAS =
            "CREATE TABLE "+TABLE_CARACTERISTICAS+"("+
                    KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    KEY_SAUNA+" INTEGER,"+
                    KEY_AREACOMUM+" INTEGER"+
            " )";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //criação das tabelas
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CLIENTES);
        db.execSQL(CREATE_TABLE_IMOVEIS);
        db.execSQL(CREATE_TABLE_CARACTERISTICAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_IMOVEIS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CARACTERISTICAS);

        this.onCreate(db);
    }
    /*
    * Adicionar as caracteristicas de um imovel
    * */
    public Caracteristica criarCaracteristica(Caracteristica caracteristica){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SAUNA, caracteristica.sauna);
        values.put(KEY_AREACOMUM, caracteristica.areacomum);
        int caract_id = (int) db.insert(TABLE_CARACTERISTICAS, null, values);
        caracteristica.setId(caract_id);
        return caracteristica;
    }

    public Imovel criarImovel(Imovel imovel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRICAO, imovel.descricao);
        values.put(KEY_TIPOLOGIA, imovel.tipologia);
        values.put(KEY_LOCALIZACAO, imovel.localizacao);
        values.put(KEY_URL, imovel.url);
        Caracteristica caractristica = this.criarCaracteristica(imovel.caracteristicas);
        values.put(KEY_CARACT_ID, caractristica.getId());
        imovel.caracteristicas = caractristica;

        int imovel_id = (int) db.insert(TABLE_IMOVEIS, null, values);
        imovel.setId(imovel_id);

        return imovel;
    }

    public Client criarClient(Client client){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, client.nome);
        values.put(KEY_IDADE, client.idade);
        values.put(KEY_URL, client.url);
        int cliente_id = (int) db.insert(TABLE_CLIENTES, null, values);
        client.setId(cliente_id);
        return client;
    }

    public User criarUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER, user.user);
        values.put(KEY_PASSWORD, user.getPassword());
        int user_id = (int) db.insert(TABLE_USERS, null, values);
        user.setId(user_id);
        return user;
    }

    public Imovel obterImovel(long imovel_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_IMOVEIS+" WHERE "+KEY_ID+" = "+imovel_id;
        Cursor c = db.rawQuery(query, null);
        Imovel imovel = new Imovel();
        if(c != null && c.moveToFirst()) {
            imovel.setId(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
            imovel.descricao = c.getString(c.getColumnIndexOrThrow(KEY_DESCRICAO));
            imovel.tipologia = c.getString(c.getColumnIndexOrThrow(KEY_TIPOLOGIA));
            imovel.localizacao = c.getString(c.getColumnIndexOrThrow(KEY_LOCALIZACAO));
            imovel.url = c.getString(c.getColumnIndexOrThrow(KEY_URL));
            int caract_id = c.getInt(c.getColumnIndexOrThrow(KEY_CARACT_ID));
            Caracteristica caracteristica = this.obterImovelCaracteristicas(caract_id);
            imovel.caracteristicas = caracteristica;
        }
        return imovel;
    }

    public Boolean attemptLogIn(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_USERS+" WHERE "+KEY_USER+" = '"+username+"' AND "+KEY_PASSWORD+" = '"+password+"'";
        Cursor c = db.rawQuery(query, null);
        boolean login = false;
        if(c != null && c.moveToFirst()) {
            login = true;
        }
        return login;
    }

    public Caracteristica obterImovelCaracteristicas(int caract_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_CARACTERISTICAS+" WHERE "+KEY_ID+" = "+caract_id;
        Cursor c = db.rawQuery(query, null);
        Caracteristica caracteristica = new Caracteristica();
        if(c != null && c.moveToFirst()) {
            caracteristica.setId(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
            caracteristica.sauna = c.getInt(c.getColumnIndexOrThrow(KEY_SAUNA));
            caracteristica.areacomum = c.getInt(c.getColumnIndexOrThrow(KEY_AREACOMUM));
        }
        return caracteristica;
    }

    public List<Imovel> obterImoveis(){
        List<Imovel> imoveis = new ArrayList<Imovel>();
        String query = "SELECT * FROM "+TABLE_IMOVEIS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()){
            do {
                Imovel imovel = new Imovel();
                imovel.setId(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
                imovel.descricao = c.getString(c.getColumnIndexOrThrow(KEY_DESCRICAO));
                imovel.tipologia = c.getString(c.getColumnIndexOrThrow(KEY_TIPOLOGIA));
                imovel.localizacao = c.getString(c.getColumnIndexOrThrow(KEY_LOCALIZACAO));
                imovel.url = c.getString(c.getColumnIndexOrThrow(KEY_URL));
                int caract_id = c.getInt(c.getColumnIndexOrThrow(KEY_CARACT_ID));
                Log.i("CARACT_ID", String.valueOf(caract_id));
                Caracteristica caracteristica = this.obterImovelCaracteristicas(caract_id);
                imovel.caracteristicas = caracteristica;
                imoveis.add(imovel);
            }while(c.moveToNext());
        }
        return imoveis;
    }

    public List<Client> obterClientes(){
        List<Client> clientes = new ArrayList<Client>();
        String query = "SELECT * FROM "+TABLE_CLIENTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()){
            do {
                Client client = new Client();
                client.setId(c.getInt(c.getColumnIndexOrThrow(KEY_ID)));
                client.nome = c.getString(c.getColumnIndexOrThrow(KEY_NOME));
                client.idade = c.getInt(c.getColumnIndexOrThrow(KEY_IDADE));
                client.url = c.getString(c.getColumnIndexOrThrow(KEY_URL));
                clientes.add(client);
            }while(c.moveToNext());
        }
        return clientes;
    }

    public int atualizarImovel(Imovel imovel){
        Log.i("ATUALIZAR", imovel.descricao);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRICAO, imovel.descricao);
        values.put(KEY_TIPOLOGIA, imovel.tipologia);
        values.put(KEY_LOCALIZACAO, imovel.localizacao);
        atualizarCaracteristica(imovel.caracteristicas);
        Log.i("VALUIES", String.valueOf(imovel.getId()));
        return db.update(TABLE_IMOVEIS, values, KEY_ID+ " = ? ",new String[]{String.valueOf(imovel.getId())});
    }

    public int atualizarCaracteristica(Caracteristica caracteristica){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SAUNA, caracteristica.sauna);
        values.put(KEY_AREACOMUM, caracteristica.areacomum);
        return db.update(TABLE_CARACTERISTICAS, values, KEY_ID+ " = ? ",new String[]{String.valueOf(caracteristica.getId())});
    }

    public int removeImovel(int imovel_id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_IMOVEIS, KEY_ID+" = ? ", new String[]{String.valueOf(imovel_id)});
    }

    public int atualizarClient(Client client){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, client.nome);
        values.put(KEY_IDADE, client.idade);
        values.put(KEY_URL, client.url);
        return db.update(TABLE_CLIENTES, values, KEY_ID+ " = ? ",new String[]{String.valueOf(client.getId())});
    }

    public int removeClient(int client_id){
        Log.i("ONREMOVE_CLIENTID", String.valueOf(client_id));
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CLIENTES, KEY_ID+" = ? ", new String[]{String.valueOf(client_id)});
    }

    public void fecharDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen())
            db.close();
    }

}
