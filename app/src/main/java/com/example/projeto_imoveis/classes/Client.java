package com.example.projeto_imoveis.classes;

public class Client {
    private int id;
    public String nome;
    public Integer idade;
    public String url;

    public Client(){}

    public Client(String nome, Integer idade, String url){
        this.nome = nome;
        this.idade = idade;
        this.url = url;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", url='" + url + '\'' +
                '}';
    }
}
