package com.app.gandalf.piquatro.models;

public class ClienteModel {
    private int idCliente;
    private String nomeCompletoCliente;
    private String emailCliente;
    private String senhaCliente;
    private String CPFCliente;
    private String celularCliente;
    private String telComercialCliente;
    private String telResidencialCliente;
    private String dtNascCliente;
    private int recebeNewsLetter;

    public ClienteModel(int idCliente, String nomeCompletoCliente, String emailCliente, String senhaCliente, String CPFCliente, String celularCliente, String telComercialCliente, String telResidencialCliente, String dtNascCliente, int recebeNewsLetter) {
        this.idCliente = idCliente;
        this.nomeCompletoCliente = nomeCompletoCliente;
        this.emailCliente = emailCliente;
        this.senhaCliente = senhaCliente;
        this.CPFCliente = CPFCliente;
        this.celularCliente = celularCliente;
        this.telComercialCliente = telComercialCliente;
        this.telResidencialCliente = telResidencialCliente;
        this.dtNascCliente = dtNascCliente;
        this.recebeNewsLetter = recebeNewsLetter;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCompletoCliente() {
        return nomeCompletoCliente;
    }

    public void setNomeCompletoCliente(String nomeCompletoCliente) {
        this.nomeCompletoCliente = nomeCompletoCliente;
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

    public String getCPFCliente() {
        return CPFCliente;
    }

    public void setCPFCliente(String CPFCliente) {
        this.CPFCliente = CPFCliente;
    }

    public String getCelularCliente() {
        return celularCliente;
    }

    public void setCelularCliente(String celularCliente) {
        this.celularCliente = celularCliente;
    }

    public String getTelComercialCliente() {
        return telComercialCliente;
    }

    public void setTelComercialCliente(String telComercialCliente) {
        this.telComercialCliente = telComercialCliente;
    }

    public String getTelResidencialCliente() {
        return telResidencialCliente;
    }

    public void setTelResidencialCliente(String telResidencialCliente) {
        this.telResidencialCliente = telResidencialCliente;
    }

    public String getDtNascCliente() {
        return dtNascCliente;
    }

    public void setDtNascCliente(String dtNascCliente) {
        this.dtNascCliente = dtNascCliente;
    }

    public int getRecebeNewsLetter() {
        return recebeNewsLetter;
    }

    public void setRecebeNewsLetter(int recebeNewsLetter) {
        this.recebeNewsLetter = recebeNewsLetter;
    }
}
