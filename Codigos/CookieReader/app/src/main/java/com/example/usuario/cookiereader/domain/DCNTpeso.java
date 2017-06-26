package com.example.usuario.cookiereader.domain;

public class DCNTpeso {

	private int peso;

	private int cdNutriente;

	private int cdDcnt;

    private  String nomeNutriente;


    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getCdNutriente() {
        return cdNutriente;
    }

    public void setCdNutriente(int cdNutriente) {
        this.cdNutriente = cdNutriente;
    }

    public int getCdDcnt() {
        return cdDcnt;
    }

    public void setCdDcnt(int cdDcnt) {
        this.cdDcnt = cdDcnt;
    }

    public String getNomeNutriente() {
        return nomeNutriente;
    }

    public void setNomeNutriente(String nomeNutriente) {
        this.nomeNutriente = nomeNutriente;
    }

    @Override
    public String toString(){
            return  this.nomeNutriente +" / Peso: "+ Integer.toString(this.getPeso());
    } 

}
