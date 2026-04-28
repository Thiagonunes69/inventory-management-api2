package com.example.demo.DTOs;

public record ProdutoStatusDTO(
    int total,
    int emEstoque,
    int estoqueBaixo,
    int esgotados
) {}