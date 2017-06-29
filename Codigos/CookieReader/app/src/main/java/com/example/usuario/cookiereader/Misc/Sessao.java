package com.example.usuario.cookiereader.Misc;

import com.example.usuario.cookiereader.domain.Usuario;



/**
 * Created by Usuario on 24/05/2017.
 */

public class Sessao {
    private static Usuario usuario;
    private static Integer quantScan;

    public void logado(Usuario logado){
        this.usuario = logado;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public Integer getQuantScan() {
        return quantScan;
    }

    public void setQuantScan(Integer quantScan) {
        Sessao.quantScan = quantScan;
    }
}
