package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.TransicaoProdutos;
import java.util.Date;

@Repository
public interface TransicaoProdutosRepository 
        extends JpaRepository<TransicaoProdutos, Long> {

    List<TransicaoProdutos> findByUsuarioId(Long usuarioId);
    Optional<TransicaoProdutos> findByIdAndUsuarioId(Long id, Long usuarioId);
@Query(value = """
    SELECT 
        CAST(tp.data AS DATE),
        
        SUM(
            CASE
                WHEN tp.tipo = 'ENTRADA'
                THEN tp.quantidade
                ELSE 0
            END
        ),

        SUM(
            CASE
                WHEN tp.tipo = 'SAIDA'
                THEN tp.quantidade
                ELSE 0
            END
        )

    FROM transicao_produtos tp

    WHERE tp.usuario_id = :usuarioId
    AND tp.data BETWEEN :inicio AND :fim

    GROUP BY CAST(tp.data AS DATE)

    ORDER BY CAST(tp.data AS DATE)
""", nativeQuery = true)
    List<Object[]> buscarDadosGrafico(@Param("usuarioId") Long usuarioId, @Param("inicio") Date inicio, @Param("fim") Date fim);
}   