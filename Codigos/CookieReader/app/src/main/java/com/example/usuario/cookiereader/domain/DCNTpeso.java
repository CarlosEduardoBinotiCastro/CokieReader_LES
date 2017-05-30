package com.example.usuario.cookiereader.domain;

public class DCNTpeso {

	private int peso;

	private Nutrientes nutrientes;

	private com.example.usuario.cookiereader.domain.dcnt dcnt;


    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Nutrientes getNutrientes() {
        return nutrientes;
    }

    public void setNutrientes(Nutrientes nutrientes) {
        this.nutrientes = nutrientes;
    }

    public com.example.usuario.cookiereader.domain.dcnt getDcnt() {
        return dcnt;
    }

    public void setDcnt(com.example.usuario.cookiereader.domain.dcnt dcnt) {
        this.dcnt = dcnt;
    }
        
    
      @Override
    public String toString(){
            return Integer.toString(this.getPeso());
    } 

}
