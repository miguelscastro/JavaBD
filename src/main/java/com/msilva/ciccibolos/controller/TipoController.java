package com.msilva.ciccibolos.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.msilva.ciccibolos.model.tipo.Tipo;
import com.msilva.ciccibolos.model.tipo.TipoService;

@Controller
public class TipoController {
    @Autowired
    private ApplicationContext context;

    // Adição de novo tipo na tabela Tipo
    @GetMapping("/gerirTipos")
    public String tipo(Model model) {
        model.addAttribute("tipo", new Tipo(""));
        TipoService ts = context.getBean(TipoService.class);
        List<Map<String, Object>> listaTipos = ts.obterTodosTipos();
        model.addAttribute("listaTipos", listaTipos);
        return "gerirTipos";
    }

    @PostMapping("/gerirTipos")
    public String addTipo(Model model, @ModelAttribute Tipo ti) {
        // tenta adicionar ao banco o novo Tipo inserido
        try {
            TipoService ts = context.getBean(TipoService.class);
            ts.inserir(ti);
        } catch (IllegalArgumentException e) {
            // captura e exibe a mensagem de erro caso a tentativa de inserção não passe na
            // validação do verificarTipo() no TipoService
            model.addAttribute("error", e.getMessage());
            // recarrega a lista dos tipos no modelo
            TipoService ts = context.getBean(TipoService.class);
            model.addAttribute("listaTipos", ts.obterTodosTipos());
            return "gerirTipos";
        }
        return "redirect:/gerirTipos";
    }

    // Edição de tipo de produto na tabela Tipo
    @GetMapping("/gerirTipos/{idTipoProduto}")
    public String editaTipo(Model model, @PathVariable int idTipoProduto) {
        // popula o formulário com os dados do tipo selecionado para edição
        TipoService ts = context.getBean(TipoService.class);
        Tipo ti = ts.obterTipo(idTipoProduto);
        model.addAttribute("idTipoProduto", idTipoProduto);
        model.addAttribute("tipo", ti);

        // recarrega a lista dos produtos no modelo
        model.addAttribute("listaTipos", ts.obterTodosTipos());

        // ajuda a validar qual tipo de value deve ser passado ao formulario já que
        // CREATE e UPDATE compartilham mesmo formuiario
        model.addAttribute("edicao", true);
        return "gerirTipos";
    }

    @PostMapping("/gerirTipos/{idTipoProduto}")
    public String editarTipo(@PathVariable int idTipoProduto, @ModelAttribute Tipo ti) {
        TipoService ts = context.getBean(TipoService.class);
        ts.atualizarTipo(idTipoProduto, ti);

        return "redirect:/gerirTipos";
    }

    // Deletar produto
    @PostMapping("/deletarTipo/{idTipoProduto}")
    public String deletarTipo(Model model, @PathVariable int idTipoProduto, RedirectAttributes redirectAttributes) {

        try {
            TipoService ts = context.getBean(TipoService.class);
            ts.deletarTipo(idTipoProduto);
        } catch (IllegalArgumentException e) {

            // Captura e exibe a mensagem de erro
            redirectAttributes.addFlashAttribute("error", e.getMessage());

            // Recarrega a lista dos tipos no modelo e faz o redirecionamento
            TipoService ts = context.getBean(TipoService.class);
            redirectAttributes.addFlashAttribute("listaTipos", ts.obterTodosTipos());
            return "redirect:/gerirTipos";
        }
        return "redirect:/gerirTipos";
    }
}
