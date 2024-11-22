package com.msilva.ciccibolos.model.tipo;

public class Tipo {
    private int idTipoProduto;
    private String nomeTipoProduto;

    public Tipo() {

    }

    public Tipo(int idTipoProduto, String nomeTipoProduto) {
        this.idTipoProduto = idTipoProduto;
        this.nomeTipoProduto = nomeTipoProduto;
    }

    public Tipo(String nomeTipoProduto) {
        this.nomeTipoProduto = nomeTipoProduto;
    }

    public int getIdTipoProduto() {
        return idTipoProduto;
    }

    public void setIdTipoProduto(int idTipoProduto) {
        this.idTipoProduto = idTipoProduto;
    }

    public String getNomeTipoProduto() {
        return nomeTipoProduto;
    }

    public void setNomeTipoProduto(String nomeTipoProduto) {
        this.nomeTipoProduto = nomeTipoProduto;
    }
}
