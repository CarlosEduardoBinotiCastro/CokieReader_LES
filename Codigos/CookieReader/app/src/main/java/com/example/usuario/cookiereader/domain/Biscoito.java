package com.example.usuario.cookiereader.domain;

import java.util.ArrayList;

public class Biscoito {

	private int cdBiscoito;

	private String nome;

	private int cdEmpresa;

	private ArrayList<BiscoitoNutriente> biscoitoNutriente = new ArrayList();

    public int getCdBiscoito() {
        return cdBiscoito;
    }

    public void setCdBiscoito(int cdBiscoito) {
        this.cdBiscoito = cdBiscoito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCdEmpresa() {
        return cdEmpresa;
    }

    public void setCdEmpresa(int empresa) {
        this.cdEmpresa = empresa;
    }

    public ArrayList<BiscoitoNutriente> getBiscoitoNutriente() {
        return biscoitoNutriente;
    }

    public void setBiscoitoNutriente(ArrayList<BiscoitoNutriente> biscoitoNutriente) {
        this.biscoitoNutriente = biscoitoNutriente;
    }

        

    @Override
    public String toString(){
        return this.getNome();
    } 
    
}
