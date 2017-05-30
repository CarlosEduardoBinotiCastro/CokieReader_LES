
package com.example.usuario.cookiereader.domain;

import java.util.ArrayList;

public class dcnt {

	private int cdDcnt;

	private String nome;


    public int getCdDcnt() {
        return cdDcnt;
    }

    public void setCdDcnt(int cdDcnt) {
        this.cdDcnt = cdDcnt;
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
