package com.msilva.ciccibolos.model.produto;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    @Autowired
    ProdutoDAO pdao;

    public void inserir(Produto prod) {
        // verifica se o campo nome passado para o objeto Tipo não está vazio
        if (prod.getNomeProduto() == null || prod.getNomeProduto().trim().isEmpty()) {
            throw new IllegalArgumentException("O produto precisa de um nome.");
        }
        // verifica se o nome passado para o objeto Tipo já não está registrado
        if (pdao.verificarProduto(prod.getNomeProduto())) {
            throw new IllegalArgumentException("Já existe um produto com esse nome.");
        }
        // verifica se a descrição não está vazia
        if (prod.getDescricaoProduto() == null || prod.getDescricaoProduto().trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição não pode estar vazia.");
        }
        // verifica se o tipo do produto foi definido
        if (prod.getTipoProduto() == 0) {
            throw new IllegalArgumentException("O produto precisa ter um tipo definido.");
        }
        pdao.inserir(prod);
    }

    public List<Map<String, Object>> obterTodosProdutos() {
        return pdao.obterTodosProdutos();
    }

    public Produto obterProduto(int idProduto) {
        return pdao.obterProduto(idProduto);
    }

    public void atualizarProduto(int idProduto, Produto prod) {
        pdao.atualizarProduto(idProduto, prod);
    }

    public void deletarProduto(int idProduto) {
        pdao.deletarProduto(idProduto);
    }
}
