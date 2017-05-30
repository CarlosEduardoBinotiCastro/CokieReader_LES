package com.example.usuario.cookiereader.domain;

import java.util.ArrayList;

public class Nutrientes {

	private int cdNutriente;

	private String nome;

    public int getCdNutriente() {
        return cdNutriente;
    }

    public void setCdNutriente(int cdNutriente) {
        this.cdNutriente = cdNutriente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

        
          @Override
    public String toString(){
            return this.getNome();
    } 

}
