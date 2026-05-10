package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.TransicaoProdutos;

public interface TransicaoProdutosRepository 
        extends JpaRepository<TransicaoProdutos, Long> {

    List<TransicaoProdutos> findByUsuarioId(Long usuarioId);
    Optional<TransicaoProdutos> findByIdAndUsuarioId(Long id, Long usuarioId);
}