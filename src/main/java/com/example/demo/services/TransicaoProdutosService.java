package com.example.demo.services;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.DTOs.GraficoTransicaoProdutosDTO;
import com.example.demo.model.Produtos;
import com.example.demo.model.TipoTransacao;
import com.example.demo.model.TransicaoProdutos;
import com.example.demo.model.user.Usuario;
import com.example.demo.repository.ProdutosRepository;
import com.example.demo.repository.TransicaoProdutosRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class TransicaoProdutosService {

    private final TransicaoProdutosRepository transicaoRepository;
    private final ProdutosRepository produtosRepository;
    private final UsuarioRepository usuarioRepository;

    public TransicaoProdutosService(
            TransicaoProdutosRepository transicaoRepository,
            ProdutosRepository produtosRepository,
            UsuarioRepository usuarioRepository) {

        this.transicaoRepository = transicaoRepository;
        this.produtosRepository = produtosRepository;
        this.usuarioRepository = usuarioRepository;
    }


    public List<TransicaoProdutos> listar(Long usuarioId){
        return transicaoRepository.findByUsuarioId(usuarioId);
    }


    public TransicaoProdutos buscarPorId(Long id, Long usuarioId){
        return transicaoRepository
                .findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Transação não encontrada"));
    }


    public TransicaoProdutos criar(Long produtoId, Long usuarioId, TransicaoProdutos transacao){

        Produtos produto = produtosRepository
                .findByIdAndUsuarioId(produtoId, usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Transação não encontrada"));

        Usuario usuario = usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));


        if(transacao.getTipo() == TipoTransacao.ENTRADA){
            produto.setQnt(produto.getQnt() + transacao.getQuantidade());
        } else {
            if(produto.getQnt() < transacao.getQuantidade()){
                throw new RuntimeException("Estoque insuficiente");
            }
            produto.setQnt(produto.getQnt() - transacao.getQuantidade());
        }

        produtosRepository.save(produto);

        transacao.setProduto(produto);
        transacao.setUsuario(usuario);

        return transicaoRepository.save(transacao);
    }
    public void deletar(Long id, Long usuarioId){
        TransicaoProdutos transacao = buscarPorId(id, usuarioId);
        transicaoRepository.delete(transacao);
    }

    public List<Map<String, Object>> buscarGrafico(
        Long usuarioId,
        Date inicio,
        Date fim){

        List<Object[]> resultado = transicaoRepository.buscarDadosGrafico(usuarioId, inicio, fim);

        List<Map<String, Object>> dados = new ArrayList<>();

        for(Object[] linha : resultado){

            Map<String, Object> item = new HashMap<>();

            item.put("dia", linha[0]);
            item.put("entradas", linha[1]);
            item.put("saidas", linha[2]);

            dados.add(item);
        }

        return dados;
}
}
