
package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.DTOs.GraficoTransicaoProdutosDTO;
import com.example.demo.DTOs.TransicaoProdutosResponseDTO;
import com.example.demo.DTOs.TransicaoProdutosResumoDTO;
import com.example.demo.model.TipoTransacao;
import com.example.demo.model.TransicaoProdutos;
import com.example.demo.model.user.Usuario;
import com.example.demo.services.TransicaoProdutosService;



@RestController
@RequestMapping("/api/transacoes")
public class TransicaoProdutosController {

    private final TransicaoProdutosService service;
    private TransicaoProdutosResponseDTO toDTO(TransicaoProdutos transicaoProdutos){
        return new TransicaoProdutosResponseDTO(
            transicaoProdutos.getId(),
            transicaoProdutos.getProduto().getId(),
            transicaoProdutos.getProduto().getCodigo(),
            transicaoProdutos.getProduto().getNome(),
            transicaoProdutos.getTipo(),
            transicaoProdutos.getQuantidade(),
            transicaoProdutos.getData(),
            transicaoProdutos.getObservacao()
        );
    }
    public TransicaoProdutosController(TransicaoProdutosService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TransicaoProdutosResponseDTO>> listar(@AuthenticationPrincipal Usuario usuario){
        List<TransicaoProdutosResponseDTO> response = service
                .listar(usuario.getId())
                .stream()
                .map(this::toDTO)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{produtoId}")
    public ResponseEntity<TransicaoProdutosResponseDTO> criar(
            @PathVariable Long produtoId,
            @RequestBody TransicaoProdutos transacao,
            @AuthenticationPrincipal Usuario usuario){

        TransicaoProdutos produtoSalvo = service.criar(produtoId, usuario.getId(), transacao);
        return ResponseEntity.ok(toDTO(produtoSalvo));
        //return service.criar(produtoId, usuario.getId(), transacao);
    }

    @DeleteMapping("/{id}")
    public void deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario){

        service.deletar(id, usuario.getId());
    }
    
    @GetMapping("/dadosGrafico/{inicio}/{fim}")
    public List<Map<String, Object>> dadosGrafico(@AuthenticationPrincipal Usuario usuario, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date inicio, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fim) {
        List<Map<String, Object>> dados = service.buscarGrafico(usuario.getId() , inicio, fim);
        return dados;
    }
    
    
    @GetMapping("/resumo")
    public ResponseEntity <TransicaoProdutosResumoDTO> resumo(@AuthenticationPrincipal Usuario usuario) {
        List<TransicaoProdutos> transicaoProdutos = service.listar(usuario.getId());
        int total = transicaoProdutos.size();
        int entrada = 0;
        int saida = 0;
        Date ultimaTransicao = null;

        for (TransicaoProdutos t : transicaoProdutos) {
            if (t.getTipo() ==  TipoTransacao.ENTRADA) {
                entrada ++;
            }else if (t.getTipo() == TipoTransacao.SAIDA) {
                saida ++;
            }
            if (ultimaTransicao == null || t.getData().after(ultimaTransicao)) {
                ultimaTransicao = t.getData();
            }
        }

        TransicaoProdutosResumoDTO resumo = new TransicaoProdutosResumoDTO(total, saida, entrada, ultimaTransicao);
        return ResponseEntity.ok(resumo);
    }
    
}

