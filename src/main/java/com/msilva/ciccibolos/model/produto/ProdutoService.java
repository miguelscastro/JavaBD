package com.msilva.cicciBolos.model.produto;

import java.util.List;
import java.util.Map;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    // converte o nome da imagem para hash
    public String md5hash(String originalString) {
        try {
            // Cria uma instância de MessageDigest com o algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Calcula o hash da string original
            byte[] hashBytes = md.digest(originalString.getBytes());

            // Converte o array de bytes para uma representação hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Algoritmo de hash não encontrado: " + e.getMessage());
        }
        return "batata";
    }
}
