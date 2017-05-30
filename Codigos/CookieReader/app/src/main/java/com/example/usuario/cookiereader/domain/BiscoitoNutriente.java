package com.example.usuario.cookiereader.domain;

public class BiscoitoNutriente {

	private float quant;

	private Biscoito biscoito;

	private Nutrientes nutrientes;

        
    public float getQuant() {
        return quant;
    }

    public void setQuant(float quant) {
        this.quant = quant;
    }

    public Biscoito getBiscoito() {
        return biscoito;
    }

    public void setBiscoito(Biscoito biscoito) {
        this.biscoito = biscoito;
    }

    public Nutrientes getNutrientes() {
        return nutrientes;
    }

    public void setNutrientes(Nutrientes nutrientes) {
        this.nutrientes = nutrientes;
    }
        
        @Override
    public String toString(){
            return Float.toString(this.getQuant());
    }  
        
}
