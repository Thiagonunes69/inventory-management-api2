package com.example.demo.DTOs;
import java.sql.Date;
import com.example.demo.model.TipoTransacao;

public record TransicaoProdutosResponseDTO(
    long id,
    long idProduto,
    String codigoProduto,
    String nomeProduto,
    TipoTransacao tipo,
    int quantidade,
    Date data,
    String observacao
) {}
