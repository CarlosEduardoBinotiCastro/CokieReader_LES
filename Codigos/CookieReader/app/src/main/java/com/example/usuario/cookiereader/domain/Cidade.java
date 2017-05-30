package com.example.usuario.cookiereader.domain;

public class Cidade {

	private int cdCidade;

	private String nome;

	private String siglaUf;

    public int getCdCidade() {
        return cdCidade;
    }

    public void setCdCidade(int cdCidade) {
        this.cdCidade = cdCidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSiglaUf() {
        return siglaUf;
    }

    public void setSiglaUf(String siglaUf) {
        this.siglaUf = siglaUf;
    }

    @Override
    public String toString(){
            return this.getNome();
    } 

}
