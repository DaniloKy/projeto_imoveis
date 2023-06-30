package com.example.projeto_imoveis.classes;

public class Caracteristica {
    private int id;
    public String sauna;
    public String areacomum;

    public Caracteristica(){}

    public Caracteristica(String sauna, String areacomum){
        this.sauna = sauna;
        this.areacomum = areacomum;
    }

    public boolean hasSauna(){ return (this.sauna == "sim")?true:false; }
    public boolean hasAreacomum(){ return (this.areacomum == "sim")?true:false; }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Caracteristica{" +
                "id=" + id +
                ", sauna=" + sauna +
                ", areacomum=" + areacomum +
                '}';
    }
}
