package com.app.gandalf.piquatro.models;

public class Cart_List {
    private int id;
    private String nome;
    private String desc;
    private String image;
    private double preco;
    private double promocao;
    private int qtd;

    public Cart_List(int id, String nome, String desc, String image, double preco, double promocao, int qtd) {
        this.id = id;
        this.nome = nome;
        this.desc = desc;
        this.image = image;
        this.preco = preco;
        this.promocao = promocao;
        this.qtd = qtd;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getPromocao() {
        return promocao;
    }

    public void setPromocao(double promocao) {
        this.promocao = promocao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cart_List other = (Cart_List) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + nome + ", price=" + promocao + "]";
    }
}
