package com.example.demo.DTOs;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    String descricao,
    String codigo,
    Integer qnt,
    String status,
    String usuarioNome
) {}