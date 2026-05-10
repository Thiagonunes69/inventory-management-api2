package com.example.demo.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Produtos;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos,Long>{

      Optional<Produtos> findByIdAndUsuarioId(Long id, Long usuarioId);
      List<Produtos> findByUsuarioId(Long usuarioId);
      Optional<Produtos> findByCodigoAndUsuarioId(String codigo, Long usuarioId);
      boolean existsByCodigoAndUsuarioId(String codigo, Long usuarioId);
}
