package com.msilva.ciccibolos.model.produto;

public class Produto {
    private int idProduto;
    private String nomeProduto;
    private String descricaoProduto;
    private int tipoProduto;

    public Produto() {

    }

    // Manipulação de já cadastrado
    public Produto(int idProduto, String nomeProduto, String descricaoProduto, int tipoProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.tipoProduto = tipoProduto;
    }

    // Inserção de novo produto
    public Produto(String nomeProduto, String descricaoProduto, int tipoProduto) {
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.tipoProduto = tipoProduto;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(int tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

}
