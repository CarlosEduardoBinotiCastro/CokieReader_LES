package com.example.usuario.cookiereader.domain;

/**
 * Created by Usuario on 29/06/2017.
 */

public class Scan {
    private Integer cdBiscoito;
    private Integer cdUsuario;
    private String data;
    private Integer cdDcnt;
    private Integer favorito;

    public Integer getCdBiscoito() {
        return cdBiscoito;
    }

    public void setCdBiscoito(Integer cdBiscoito) {
        this.cdBiscoito = cdBiscoito;
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getCdDcnt() {
        return cdDcnt;
    }

    public void setCdDcnt(Integer cdDcnt) {
        this.cdDcnt = cdDcnt;
    }

    public Integer getFavorito() {
        return favorito;
    }

    public void setFavorito(Integer favorito) {
        this.favorito = favorito;
    }
}
