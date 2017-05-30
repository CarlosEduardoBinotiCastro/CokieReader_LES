package com.example.usuario.cookiereader.domain;

import java.sql.Time;
import java.util.Date;

public class scanBiscoito {

	private int cdScan;

	private Date data;

	private Time hora;

	private Usuario usuario;

	private Biscoito biscoito;

	private com.example.usuario.cookiereader.domain.dcnt dcnt;

    public int getCdScan() {
        return cdScan;
    }

    public void setCdScan(int cdScan) {
        this.cdScan = cdScan;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Biscoito getBiscoito() {
        return biscoito;
    }

    public void setBiscoito(Biscoito biscoito) {
        this.biscoito = biscoito;
    }

    public com.example.usuario.cookiereader.domain.dcnt getDcnt() {
        return dcnt;
    }

    public void setDcnt(com.example.usuario.cookiereader.domain.dcnt dcnt) {
        this.dcnt = dcnt;
    }

        
          @Override
    public String toString(){
            return this.biscoito.getNome();
    } 
}
