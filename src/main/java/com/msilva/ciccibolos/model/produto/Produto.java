package com.msilva.cicciBolos.model.produto;

public class Produto {
    private int idProduto;
    private String nomeProduto;
    private String descricaoProduto;
    private int tipoProduto;
    private String caminhoImagem;

    // Imagem
    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public Produto() {

    }

    // Manipulação de já cadastrado
    public Produto(int idProduto, String nomeProduto, String descricaoProduto, int tipoProduto, String caminhoImagem) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.tipoProduto = tipoProduto;
        this.caminhoImagem = caminhoImagem;
    }

    // Inserção de novo produto
    public Produto(String nomeProduto, String descricaoProduto, int tipoProduto, String caminhoImagem) {
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.tipoProduto = tipoProduto;
        this.caminhoImagem = caminhoImagem;

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
