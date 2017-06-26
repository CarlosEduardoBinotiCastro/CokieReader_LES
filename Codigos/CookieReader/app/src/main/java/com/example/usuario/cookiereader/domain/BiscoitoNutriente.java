package com.example.usuario.cookiereader.domain;

public class BiscoitoNutriente {

	private float quant;

	private int cdBiscoito;

	private int cdNutrientes;

    private String nomeNutriente;

        
    public float getQuant() {
        return quant;
    }

    public void setQuant(float quant) {
        this.quant = quant;
    }

    public void setCdBiscoito(int cdBiscoito) {
        this.cdBiscoito = cdBiscoito;
    }

    public int getCdBiscoito() {
        return cdBiscoito;
    }

    public int getCdNutrientes() {
        return cdNutrientes;
    }

    public void setCdNutrientes(int cdNutrientes) {
        this.cdNutrientes = cdNutrientes;
    }

    public String getNomeNutriente() {
        return nomeNutriente;
    }

    public void setNomeNutriente(String nomeNutriente) {
        this.nomeNutriente = nomeNutriente;
    }

    @Override
    public String toString(){
            return nomeNutriente +" / "+ String.valueOf(quant) + " g";
    }  
        
}
