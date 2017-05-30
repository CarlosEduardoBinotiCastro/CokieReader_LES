package com.example.usuario.cookiereader.Misc;

import com.example.usuario.cookiereader.domain.Usuario;

/**
 * Created by Usuario on 24/05/2017.
 */

public class Sessao {
    private static Usuario usuario;

    public void logado(Usuario logado){
        this.usuario = logado;
    }

    public Usuario getUsuario(){
        return usuario;
    }
}
