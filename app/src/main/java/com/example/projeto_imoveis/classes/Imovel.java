package com.example.projeto_imoveis.classes;

public class Imovel {
    private int id;
    public String descricao;
    public String tipologia;
    public String localizacao;
    public String url;
    public Caracteristica caracteristicas;

    public Imovel(){}

    public Imovel(String descricao, String tipologia, String localizacao, String url){
        this.descricao = descricao;
        this.tipologia = tipologia;
        this.localizacao = localizacao;
        this.url = url;
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Imovel{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", tipologia='" + tipologia + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", url='" + url + '\'' +
                ", caracteristicas=" + caracteristicas +
                '}';
    }
}