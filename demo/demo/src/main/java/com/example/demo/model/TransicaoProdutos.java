package com.example.demo.model;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.model.user.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class TransicaoProdutos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produtos produto;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;
    private int quantidade;
    @CreationTimestamp
    private Date data;
    private String observacao;

    public TransicaoProdutos(Produtos produto,Usuario usuario, TipoTransacao tipo, int quantidade, Date data, String observacao) {
        this.produto = produto;
        this.usuario = usuario;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.data = data;
        this.observacao = observacao;
    }
    

    public TransicaoProdutos() {} 

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public Long getId() {
        return id;
    }

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    
}
