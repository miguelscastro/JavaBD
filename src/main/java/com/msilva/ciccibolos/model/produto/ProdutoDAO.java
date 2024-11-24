package com.msilva.cicciBolos.model.produto;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class ProdutoDAO {
    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbc;

    @PostConstruct
    private void initialize() {
        jdbc = new JdbcTemplate(dataSource);
    }

    // Verifica se o produto já existe no BD, impedindo inserção de um mesmo produto
    // sob ID diferente
    public boolean verificarProduto(String nomeProduto) {
        // verifica se existe outro produto com mesmo nome no BD
        String sql = "SELECT COUNT(*) FROM Produto WHERE nm_Produto = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, nomeProduto);
        return count != null && count > 0;
    }

    public void inserir(Produto prod) {

        String sql = "INSERT INTO produto(nm_Produto,ds_Produto,cd_TipoProduto,ds_caminhoImagem) VALUES (?,?,?,?)";
        Object[] parametros = new Object[4];
        parametros[0] = prod.getNomeProduto();
        parametros[1] = prod.getDescricaoProduto();
        parametros[2] = prod.getTipoProduto();
        parametros[3] = prod.getCaminhoImagem();
        jdbc.update(sql, parametros);
    }

    // {id: 1, nomeProduto: Bolo de Chocolate, tipoProduto: Bolo,
    // descriçãoProduto: Bolo de chocolate com morango}
    public List<Map<String, Object>> obterTodosProdutos() {
        String sql = "SELECT p.cd_produto, p.nm_produto, p.ds_produto, t.nm_tipoproduto, p.ds_caminhoimagem, p.cd_tipoproduto FROM Produto p JOIN Tipo t ON p.cd_tipoproduto = t.cd_tipoproduto;";
        return jdbc.queryForList(sql);
    }

    public void atualizarProduto(int idProduto, Produto prod) {
        String sql = "UPDATE Produto SET nm_Produto = ?, ds_Produto = ?, cd_TipoProduto = ?, ds_caminhoImagem = ? WHERE cd_Produto = ?;";
        jdbc.update(sql, prod.getNomeProduto(), prod.getDescricaoProduto(), prod.getTipoProduto(),
                prod.getCaminhoImagem(), idProduto);
    }

    public Produto obterProduto(int idProduto) {
        String sql = "SELECT * FROM Produto WHERE cd_Produto = ?;";
        return JsonObject.converterProduto(jdbc.queryForMap(sql, idProduto));
    }

    public void deletarProduto(int idProduto) {

        String sql = "DELETE FROM Produto WHERE cd_Produto = ?";
        jdbc.update(sql, idProduto);
    }
}
