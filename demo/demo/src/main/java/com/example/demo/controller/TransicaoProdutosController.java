
package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.TransicaoProdutos;
import com.example.demo.model.user.Usuario;
import com.example.demo.services.TransicaoProdutosService;

@RestController
@RequestMapping("/api/transacoes")
public class TransicaoProdutosController {

    private final TransicaoProdutosService service;

    public TransicaoProdutosController(TransicaoProdutosService service){
        this.service = service;
    }

    @GetMapping
    public List<TransicaoProdutos> listar(@AuthenticationPrincipal Usuario usuario){
        return service.listar(usuario.getId());
    }

    @PostMapping("/{produtoId}")
    public TransicaoProdutos criar(
            @PathVariable Long produtoId,
            @RequestBody TransicaoProdutos transacao,
            @AuthenticationPrincipal Usuario usuario){

        return service.criar(produtoId, usuario.getId(), transacao);
    }

    @DeleteMapping("/{id}")
    public void deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario){

        service.deletar(id, usuario.getId());
    }
}

