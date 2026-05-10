package com.example.demo.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Produtos;
import com.example.demo.model.user.Usuario;
import com.example.demo.repository.ProdutosRepository;
import com.example.demo.repository.UsuarioRepository;

@Service

public class ProdutosService {
    private final ProdutosRepository produtosRepository;
    private final UsuarioRepository usuarioRepository;
    
    public ProdutosService(ProdutosRepository produtosRepository,UsuarioRepository usuarioRepository){
        this.produtosRepository = produtosRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Produtos> listarProdutos(Long idUsuario){
        return produtosRepository.findByUsuarioId(idUsuario);
    }

    public Produtos listarProdutosId(Long idProduto, Long idUsuario){
        return produtosRepository.findByIdAndUsuarioId(idProduto, idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    public Produtos adicionarProduto(Produtos produto, Long idUsuario){
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuário não encontrado"));
                
        if (produtosRepository.existsByCodigoAndUsuarioId(produto.getCodigo(), idUsuario)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Já existe um produto com esse código");
        }
        produto.setUsuario(usuario);
        return produtosRepository.save(produto);
    }

    public void removerProduto(Long idProduto,Long idUsuario){
    Produtos produto = produtosRepository.findByIdAndUsuarioId(idProduto, idUsuario)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Produto não encontrado"));
    produtosRepository.delete(produto);
    }

    public Produtos procurarCodigo(String codigo, Long idUsuario){
        return produtosRepository.findByCodigoAndUsuarioId(codigo, idUsuario)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    public Produtos editarProduto(Long idProduto, Produtos produtoAtualizado, Long idUsuario){

    Produtos produto = produtosRepository
            .findByIdAndUsuarioId(idProduto, idUsuario)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Produto não encontrado"));

    produto.setNome(produtoAtualizado.getNome());
    produto.setDescricao(produtoAtualizado.getDescricao());
    produto.setCodigo(produtoAtualizado.getCodigo());
    produto.setQnt(produtoAtualizado.getQnt());

    return produtosRepository.save(produto);
}
}
