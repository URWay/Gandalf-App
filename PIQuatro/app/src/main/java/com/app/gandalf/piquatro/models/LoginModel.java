package com.app.gandalf.piquatro.models;

public class LoginModel {
    private String emailCliente;
    private String senhaCliente;
    private int idCliente;

    public LoginModel(String emailCliente, String senhaCliente, int idCliente) {
        this.emailCliente = emailCliente;
        this.senhaCliente = senhaCliente;
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getSenhaCliente() {
        return senhaCliente;
    }

    public void setSenhaCliente(String senhaCliente) {
        this.senhaCliente = senhaCliente;
    }
}
