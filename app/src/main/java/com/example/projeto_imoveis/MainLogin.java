package com.example.projeto_imoveis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto_imoveis.bd.DatabaseHelper;
import com.example.projeto_imoveis.classes.User;


public class MainLogin extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper db;
    Button button, buttonCreateTest;
    EditText edit_user, edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        edit_user = (EditText) findViewById(R.id.username);
        edit_password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.loginBtn);
        buttonCreateTest = (Button) findViewById(R.id.createTest);
        button.setOnClickListener(this);
        buttonCreateTest.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == this.button){
            db = new DatabaseHelper(getApplicationContext());
            boolean attempt = db.attemptLogIn(String.valueOf(edit_user.getText()), String.valueOf(edit_password.getText()));
            db.fecharDB();
            if(attempt){
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Main.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Failed to login", Toast.LENGTH_SHORT).show();
            }
        }else if(view == buttonCreateTest){
            User user = new User("admin");
            user.setPassword("admin");
            db = new DatabaseHelper(getApplicationContext());
            User u = db.criarUser(user);
            db.fecharDB();
            Log.i("USER TESTE", u.toString());
        }
    }
}