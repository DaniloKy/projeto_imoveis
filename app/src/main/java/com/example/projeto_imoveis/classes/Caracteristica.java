package com.example.projeto_imoveis.classes;

public class Caracteristica {
    private int id;
    public int sauna;
    public int areacomum;

    public Caracteristica(){}

    public Caracteristica(int sauna, int areacomum){
        this.sauna = sauna;
        this.areacomum = areacomum;
    }

    public boolean hasSauna(){ return (this.sauna == 1)?true:false; }
    public boolean hasAreacomum(){ return (this.areacomum == 1)?true:false; }

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
