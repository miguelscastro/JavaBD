package com.msilva.ciccibolos.model;

public class Login {
    private int idUsuario;
    private String nomeUsuario;
    private String hash_senhaUsuario;

    public Login() {

    }

    public Login(int idUsuario, String nomeUsuario, String hash_senhaUsuario) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.hash_senhaUsuario = hash_senhaUsuario;
    }

    public Login(String nomeUsuario, String hash_senhaUsuario) {
        this.nomeUsuario = nomeUsuario;
        this.hash_senhaUsuario = hash_senhaUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getHash_senhaUsuario() {
        return hash_senhaUsuario;
    }

    public void setHash_senhaUsuario(String hash_senhaUsuario) {
        this.hash_senhaUsuario = hash_senhaUsuario;
    }

}
