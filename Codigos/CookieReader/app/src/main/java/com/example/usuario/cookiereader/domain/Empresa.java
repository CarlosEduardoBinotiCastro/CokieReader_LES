
package com.example.usuario.cookiereader.domain;

import java.util.ArrayList;

public class Empresa {

	private int cdEmpresa;

	private String nome;

	private int cdCidade;

    private int scans = 0;

    public int getCdEmpresa() {
        return cdEmpresa;
    }

    public void setCdEmpresa(int cdEmpresa) {
        this.cdEmpresa = cdEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCdCidade() {
        return cdCidade;
    }

    public void setCdCidade(int cdCidade) {
        this.cdCidade = cdCidade;
    }

    public void setScans(int quant){
        scans = quant;
    }

    @Override
    public String toString(){
        if(scans > 0){
            return this.getNome() + " / " + " Total: "+ String.valueOf(scans);
        }else{
            return this.getNome();
        }

    } 
    
}
