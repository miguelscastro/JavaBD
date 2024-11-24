package com.msilva.cicciBolos.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.msilva.cicciBolos.model.produto.Produto;
import com.msilva.cicciBolos.model.produto.ProdutoService;
import com.msilva.cicciBolos.model.tipo.TipoService;

@Controller
public class ProdutoController {
    @Autowired
    private ApplicationContext context;

    // carrega o model da pagina inicial
    @GetMapping("/")
    public String principal(Model model) {
        ProdutoService ps = context.getBean(ProdutoService.class);

        // carrega a lista dos produtos no modelo
        List<Map<String, Object>> listaProdutos = ps.obterTodosProdutos();
        model.addAttribute("listaProdutos", ps.obterTodosProdutos());
        return "index";
    }

    // carrega o model das paginas de produtos
    @GetMapping("/{produto}")
    public String carregarPaginaProduto(Model model, @PathVariable String produto) {
        ProdutoService ps = context.getBean(ProdutoService.class);
        // passa o caminho de armazenamento das imgs
        model.addAttribute("caminhoDir", "/app/src/main/resources/static/images/");
        List<Map<String, Object>> listaProdutos = ps.obterTodosProdutos();
        model.addAttribute("listaProdutos", ps.obterTodosProdutos());
        switch (produto) {
            case "bolos":
                return "bolos";
            case "bolosDePote":
                return "bolosDePote";
            case "cupcakes":
                return "cupcakes";
            case "doces":
                return "doces";
            default:
                return "index";
        }
    }

    // Adição de novo produto na tabela Produto
    @GetMapping("/gerirProdutos")
    public String produto(Model model) {
        // passa o caminho de armazenamento das imgs
        model.addAttribute("caminhoDir", "/app/src/main/resources/static/images/");
        model.addAttribute("produto", new Produto());

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
        return "gerirProdutos";
    }

    @PostMapping("/gerirProdutos")
    public String adicionarProduto(Model model, @ModelAttribute Produto prod,
            @RequestParam("imagemProduto") MultipartFile imagemProduto) {
        ProdutoService ps = context.getBean(ProdutoService.class);
        // verifica se foi inserida uma imagem
        if (!imagemProduto.isEmpty()) {
            // Verifica se a imagem é maior que o limite configurado
            if (imagemProduto.getSize() > 10 * 1024 * 1024) { // 10MB
                model.addAttribute("error", "O tamanho da imagem excede o limite permitido (10MB).");
            }
            // define o caminho onde a imagem será aramzenada
            String caminhoDiretorio = "/app/src/main/resources/static/images/";

            // verifica se o caminho existe e o cria caso não exista
            File diretorio = new File(caminhoDiretorio);
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }

            String nomeArquivo = imagemProduto.getOriginalFilename();
            String nomeArquivoSemExtensao = nomeArquivo.substring(0, nomeArquivo.trim().lastIndexOf("."));
            String hashNomeArquivo = ps.md5hash(nomeArquivoSemExtensao) + ".png";
            File destino = new File(caminhoDiretorio + hashNomeArquivo);
            // tenta inserir a imagem na posição passada no caminho
            try {
                imagemProduto.transferTo(destino);
                prod.setCaminhoImagem(hashNomeArquivo);
                System.out.println("Imagem salva com sucesso");

            } catch (IOException e) {
                model.addAttribute("error", "Erro ao salvar a imagem.");
                return "/gerirProdutos";
            }
        }
        // tenta adicionar ao banco o novo Produto inserido
        try {
            ps.inserir(prod);
        } catch (IllegalArgumentException e) {

            // captura e exibe a mensagem de erro caso a tentativa de inserção não passe na
            // validação do verificarProduto() no ProdutoService
            model.addAttribute("error", e.getMessage());

            // recarrega a lista dos produtos no modelo
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

        // passa o caminho de armazenamento das imgs
        model.addAttribute("caminhoDir", "/app/src/main/resources/static/images/");

        // recarrega a lista dos produtos no modelo
        model.addAttribute("listaProdutos", ps.obterTodosProdutos());

        // recarrega o dropdown no modelo
        TipoService ts = context.getBean(TipoService.class);
        List<Map<String, Object>> listaTipos = ts.obterTodosTipos();
        model.addAttribute("listaTipos", listaTipos);

        // ajuda a validar qual tipo de value deve ser passado ao formulario já que
        // CREATE e UPDATE compartilham mesmo formuiario
        model.addAttribute("edicao", true);
        return "gerirProdutos";
    }

    @PostMapping("/gerirProdutos/{idProduto}")
    public String editarProduto(@PathVariable int idProduto, @ModelAttribute Produto prod, MultipartFile imagemProduto,
            Model model) {
        ProdutoService ps = context.getBean(ProdutoService.class);

        if (!imagemProduto.isEmpty()) {
            // Verifica se a imagem é maior que o limite configurado
            if (imagemProduto.getSize() > 10 * 1024 * 1024) { // 10MB
                model.addAttribute("error", "O tamanho da imagem excede o limite permitido (10MB).");
            }
            // define o caminho onde a imagem será salva
            String caminhoDiretorio = System.getProperty("user.dir") + "/src/main/resources/static/images/";
            String nomeArquivo = imagemProduto.getOriginalFilename();
            String nomeArquivoSemExtensao = nomeArquivo.substring(0, nomeArquivo.trim().lastIndexOf("."));
            String hashNomeArquivo = ps.md5hash(nomeArquivoSemExtensao) + ".png";
            File destino = new File(caminhoDiretorio + hashNomeArquivo);
            // tenta inserir a imagem na posição passada no caminho
            try {
                imagemProduto.transferTo(destino);
                prod.setCaminhoImagem(hashNomeArquivo);

            } catch (IOException e) {
                model.addAttribute("error", "Erro ao salvar a imagem.");
                return "/gerirProdutos";
            }
        }

        ps.atualizarProduto(idProduto, prod);

        return "redirect:/gerirProdutos";
    }

    // Deletar produto
    @PostMapping("/deletarProduto/{idProduto}")
    public String deletarProduto(@PathVariable int idProduto) {
        ProdutoService ps = context.getBean(ProdutoService.class);

        // Pega o produto e verifica se ele o a imagem estão vazios ou nulos
        Produto prod = ps.obterProduto(idProduto);
        if (prod != null && prod.getCaminhoImagem() != null) {
            String caminhoDiretorio = System.getProperty("user.dir") + "/src/main/resources/static/images/";
            File arquivoImagem = new File(caminhoDiretorio + prod.getCaminhoImagem());

            arquivoImagem.delete();
        }

        // Excluir o produto do banco
        ps.deletarProduto(idProduto);

        return "redirect:/gerirProdutos";
    }

}
