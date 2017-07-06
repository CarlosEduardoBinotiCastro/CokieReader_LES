package com.example.usuario.cookiereader.domain;

import java.util.Date;

public class Usuario {

	private int cdUsuario;

	private String login;

	private String senha;

	private String nome;

	private String cpf;

	private int  cdCidade;

    private String dataSessao;

    private int  quantEscaneamento;

    public int getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(int cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getCdCidade() {
        return cdCidade;
    }

    public void setCdCidade(int cdCidade) {
        this.cdCidade = cdCidade;
    }

    public String getDataSessao() {
        return dataSessao;
    }

    public void setDataSessao(String dataSessao) {
        this.dataSessao = dataSessao;
    }

    public int getQuantEscaneamento() {
        return quantEscaneamento;
    }

    public void setQuantEscaneamento(int quantEscaneamento) {
        this.quantEscaneamento = quantEscaneamento;
    }

    @Override
    public String toString(){
            return this.getNome();
    }     

}
