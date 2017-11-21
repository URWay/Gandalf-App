package com.app.gandalf.piquatro.models;

public class CartItensPedido {
    private int idProduto;
    private int idPedido;
    private int qtdProduto;
    private double precoVendaItem;

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    public double getPrecoVendaItem() {
        return precoVendaItem;
    }

    public void setPrecoVendaItem(double precoVendaItem) {
        this.precoVendaItem = precoVendaItem;
    }
}
