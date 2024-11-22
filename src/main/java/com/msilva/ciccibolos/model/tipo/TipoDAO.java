package com.msilva.ciccibolos.model.tipo;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class TipoDAO {
    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbc;

    @PostConstruct
    private void initialize() {
        jdbc = new JdbcTemplate(dataSource);
    }

    // Verifica se o tipo já existe no BD, impedindo inserção de um mesmo tipo sob
    // ID diferente
    public boolean verificarTipo(String nomeTipoProduto) {
        String sql = "SELECT COUNT(*) FROM Tipo WHERE nm_TipoProduto = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, nomeTipoProduto);
        return count != null && count > 0;
    }

    public void inserir(Tipo ti) {
        String sql = "INSERT INTO Tipo(nm_TipoProduto) VALUES (?);";
        Object[] parametros = new Object[1];
        parametros[0] = ti.getNomeTipoProduto();
        jdbc.update(sql, parametros);
    }

    // {cd_Produto: 1, nm_Produto: Bolo de Chocolate, cd_TipoProduto: Bolo,
    // ds_Produto: Bolo de chocolate com morango}
    public List<Map<String, Object>> obterTodosTipos() {
        String sql = "SELECT * FROM Tipo;";
        return jdbc.queryForList(sql);
    }

    public void atualizarTipo(int idTipoProduto, Tipo ti) {
        String sql = "UPDATE Tipo SET nm_TipoProduto = ? WHERE cd_TipoProduto = ?;";
        jdbc.update(sql, ti.getNomeTipoProduto(), idTipoProduto);
    }

    public Tipo obterTipo(int idTipoProduto) {
        String sql = "SELECT * FROM Tipo WHERE cd_TipoProduto = ?;";
        return JsonObject.converterTipo(jdbc.queryForMap(sql, idTipoProduto));
    }

    // verifica se o tipo está em uso na tabela produto por ser chave estrangeira
    public boolean verificarUso(int idTipoProduto) {
        String sql = "SELECT COUNT(*) FROM Produto Where cd_tipoproduto = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, idTipoProduto);
        return count != null && count > 0;
    }

    public void deletarTipo(int idTipoProduto) {
        String sql = "DELETE FROM Tipo WHERE cd_TipoProduto = ?;";
        jdbc.update(sql, idTipoProduto);
    }
}
