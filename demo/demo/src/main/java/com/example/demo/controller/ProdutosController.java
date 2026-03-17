
package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Produtos;
import com.example.demo.model.user.Usuario;
import com.example.demo.services.ProdutosService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutosController {

    private final ProdutosService produtosService;

    public ProdutosController(ProdutosService produtosService){
        this.produtosService = produtosService;
    }

    @GetMapping("/listar")
    public List<Produtos> listarProdutos(@AuthenticationPrincipal Usuario usuario){
        return produtosService.listarProdutos(usuario.getId());
    }

    @GetMapping("/listarPorId/{idProduto}")
    public Produtos listaProdutosPorId(
            @PathVariable Long idProduto,
            @AuthenticationPrincipal Usuario usuario){

        return produtosService.listarProdutosId(idProduto , usuario.getId());
    }

    @PostMapping("/adicionarProduto")
    public Produtos adicionarProduto(
            @RequestBody Produtos produtos,
            @AuthenticationPrincipal Usuario usuario) {

        return produtosService.adicionarProduto(produtos, usuario.getId());
    }

    @DeleteMapping("/removerProduto/{produtoId}")
    public void removerProduto(
            @PathVariable Long produtoId,
            @AuthenticationPrincipal Usuario usuario) {

        produtosService.removerProduto(produtoId, usuario.getId());
    }

    @GetMapping("/listarPorCodigo/{codigo}")
    public Produtos listarPorCodigo(
            @PathVariable String codigo,
            @AuthenticationPrincipal Usuario usuario){

        return produtosService.procurarCodigo(codigo, usuario.getId());
    }

    @PutMapping("/editarProduto/{idProduto}")
    public Produtos editarProduto(
            @PathVariable Long idProduto,
            @RequestBody Produtos produto,
            @AuthenticationPrincipal Usuario usuario){

        return produtosService.editarProduto(idProduto, produto, usuario.getId());
    }
}

