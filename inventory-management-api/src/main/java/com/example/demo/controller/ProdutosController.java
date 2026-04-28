package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTOs.ProdutoResponseDTO;
import com.example.demo.DTOs.ProdutoStatusDTO;
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
    private ProdutoResponseDTO toDTO(Produtos p) {
        return new ProdutoResponseDTO(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getCodigo(),
                p.getQnt(),
                p.getStatus(),
                p.getUsuario().getNome()
        );
    }

    // ✅ LISTAR TODOS
    @GetMapping("/listar")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos(
            @AuthenticationPrincipal Usuario usuario) {

        List<ProdutoResponseDTO> response = produtosService
                .listarProdutos(usuario.getId())
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    // ✅ LISTAR POR ID
    @GetMapping("/listarPorId/{idProduto}")
    public ResponseEntity<ProdutoResponseDTO> listaProdutosPorId(
            @PathVariable Long idProduto,
            @AuthenticationPrincipal Usuario usuario){

        Produtos produto = produtosService.listarProdutosId(idProduto , usuario.getId());
        return ResponseEntity.ok(toDTO(produto));
    }

    // ✅ ADICIONAR
    @PostMapping("/adicionarProduto")
    public ResponseEntity<ProdutoResponseDTO> adicionarProduto(
            @RequestBody Produtos produtos,
            @AuthenticationPrincipal Usuario usuario) {

        Produtos produtoSalvo = produtosService.adicionarProduto(produtos, usuario.getId());
        return ResponseEntity.ok(toDTO(produtoSalvo));
    }

    // ✅ REMOVER
    @DeleteMapping("/removerProduto/{produtoId}")
    public ResponseEntity<Void> removerProduto(
            @PathVariable Long produtoId,
            @AuthenticationPrincipal Usuario usuario) {

        produtosService.removerProduto(produtoId, usuario.getId());
        return ResponseEntity.noContent().build(); // 👈 padrão REST
    }
    // BUSCAR PRODUTOS POR STATUS
    @GetMapping("/resumo")
    public ResponseEntity<ProdutoStatusDTO> resumo(
            @AuthenticationPrincipal Usuario usuario) {

        List<Produtos> produtos = produtosService.listarProdutos(usuario.getId());

        int total = produtos.size();
        int emEstoque = 0;
        int estoqueBaixo = 0;
        int esgotados = 0;

        for (Produtos p : produtos) {
            if (p.getQnt() == 0) {
                esgotados++;
            } else if (p.getQnt() < 5) {
                estoqueBaixo++;
            } else {
                emEstoque++;
            }
        }

        ProdutoStatusDTO resumo = new ProdutoStatusDTO(
            total,
            emEstoque,
            estoqueBaixo,
            esgotados
        );

        return ResponseEntity.ok(resumo);
    }
    // ✅ BUSCAR POR CÓDIGO
    @GetMapping("/listarPorCodigo/{codigo}")
    public ResponseEntity<ProdutoResponseDTO> listarPorCodigo(
            @PathVariable String codigo,
            @AuthenticationPrincipal Usuario usuario){

        Produtos produto = produtosService.procurarCodigo(codigo, usuario.getId());
        return ResponseEntity.ok(toDTO(produto));
    }

    // ✅ EDITAR
    @PutMapping("/editarProduto/{idProduto}")
    public ResponseEntity<ProdutoResponseDTO> editarProduto(
            @PathVariable Long idProduto,
            @RequestBody Produtos produto,
            @AuthenticationPrincipal Usuario usuario){

        Produtos produtoAtualizado = produtosService.editarProduto(idProduto, produto, usuario.getId());
        return ResponseEntity.ok(toDTO(produtoAtualizado));
    }
}