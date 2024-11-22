package com.msilva.cicciBolos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.msilva.cicciBolos.model.produto.Produto;
import com.msilva.cicciBolos.model.produto.ProdutoService;
import com.msilva.cicciBolos.model.tipo.TipoService;

@Controller
public class ProdutoController {
    @Autowired
    private ApplicationContext context;

    // Verificação de identidade do gestor do sistema (TO-DO)
    @GetMapping("/")
    public String principal() {
        return "teste";
    }

    // Adição de novo produto na tabela Produto
    @GetMapping("/gerirProdutos")
    public String produto(Model model) {

        model.addAttribute("produto", new Produto("", "", 0));

        // carrega a lista de produtos no modulo
        ProdutoService ps = context.getBean(ProdutoService.class);
        List<Map<String, Object>> listaProdutos = ps.obterTodosProdutos();
        model.addAttribute("listaProdutos", listaProdutos);

        // carrega a lista de tipos de produto no modulo
        TipoService ts = context.getBean(TipoService.class);
        List<Map<String, Object>> listaTipos = ts.obterTodosTipos();
        model.addAttribute("listaTipos", listaTipos);

        // ajuda a validar qual tipo de action deve ser passado ao formulario já que
        // CREATE e UPDATE compartilham mesmo formuiario
        model.addAttribute("edicao", false);
        return "/gerirProdutos";
    }

    @PostMapping("/gerirProdutos")
    public String adicionarProduto(Model model, @ModelAttribute Produto prod) {
        // tenta adicionar ao banco o novo Produto inserido
        try {
            ProdutoService ps = context.getBean(ProdutoService.class);
            ps.inserir(prod);
        } catch (IllegalArgumentException e) {

            // captura e exibe a mensagem de erro caso a tentativa de inserção não passe na
            // validação do verificarProduto() no ProdutoService
            model.addAttribute("error", e.getMessage());

            // recarrega a lista dos produtos no modelo
            ProdutoService ps = context.getBean(ProdutoService.class);
            model.addAttribute("listaProdutos", ps.obterTodosProdutos());

            // recarrega o dropdown no modelo
            TipoService ts = context.getBean(TipoService.class);
            List<Map<String, Object>> listaTipos = ts.obterTodosTipos();
            model.addAttribute("listaTipos", listaTipos);
            return "/gerirProdutos";
        }
        return "redirect:/gerirProdutos";
    }

    // Edição de produto da tabela Produto
    @GetMapping("/gerirProdutos/{idProduto}")
    public String editaProduto(Model model, @PathVariable int idProduto) {
        // popula o formulário com os dados do produto selecionado para edição
        ProdutoService ps = context.getBean(ProdutoService.class);
        Produto prod = ps.obterProduto(idProduto);
        model.addAttribute("idProduto", idProduto);
        model.addAttribute("produto", prod);

        // recarrega a lista dos produtos no modelo
        model.addAttribute("listaProdutos", ps.obterTodosProdutos());

        // recarrega o dropdown no modelo
        TipoService ts = context.getBean(TipoService.class);
        List<Map<String, Object>> listaTipos = ts.obterTodosTipos();
        model.addAttribute("listaTipos", listaTipos);

        // ajuda a validar qual tipo de value deve ser passado ao formulario já que
        // CREATE e UPDATE compartilham mesmo formuiario
        model.addAttribute("edicao", true);
        return "/gerirProdutos";
    }

    @PostMapping("/gerirProdutos/{idProduto}")
    public String editarProduto(@PathVariable int idProduto, @ModelAttribute Produto prod) {
        ProdutoService ps = context.getBean(ProdutoService.class);
        ps.atualizarProduto(idProduto, prod);

        return "redirect:/gerirProdutos";
    }

    // Deletar produto
    @PostMapping("/deletarProduto/{idProduto}")
    public String deletarProduto(@PathVariable int idProduto) {
        ProdutoService ps = context.getBean(ProdutoService.class);
        ps.deletarProduto(idProduto);
        return "redirect:/gerirProdutos";
    }

}
