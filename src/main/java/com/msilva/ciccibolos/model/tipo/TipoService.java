package com.msilva.cicciBolos.model.tipo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoService {
    @Autowired
    TipoDAO tdao;

    public void inserir(Tipo ti) {
        // verifica se o campo nome passado para o objeto Tipo não está vazio
        if (ti.getNomeTipoProduto() == null || ti.getNomeTipoProduto().trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo não pode ser vazio.");
        }
        // verifica se o nome passado para o objeto Tipo já não está registrado
        if (tdao.verificarTipo(ti.getNomeTipoProduto())) {
            throw new IllegalArgumentException("Já existe um tipo de produto com esse nome.");
        }
        tdao.inserir(ti);
    }

    public List<Map<String, Object>> obterTodosTipos() {
        return tdao.obterTodosTipos();
    }

    public Tipo obterTipo(int idTipoProduto) {
        return tdao.obterTipo(idTipoProduto);
    }

    public void atualizarTipo(int idTipoProduto, Tipo ti) {
        tdao.atualizarTipo(idTipoProduto, ti);
    }

    public void deletarTipo(int idTipoProduto) {

        if (tdao.verificarUso(idTipoProduto)) {
            throw new IllegalArgumentException(
                    "Este tipo está em uso! Para excluí-lo, altere o tipo dos produtos cadastrados nele ou exclua-os");
        }
        tdao.deletarTipo(idTipoProduto);
    }

    public boolean verificarUso(int idTipoProduto) {
        return tdao.verificarUso(idTipoProduto);
    }
}
