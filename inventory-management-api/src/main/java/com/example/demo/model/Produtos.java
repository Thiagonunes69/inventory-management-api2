package com.example.demo.model;

import com.example.demo.model.user.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Produtos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private String codigo;
    private int qnt;
    private String status;
    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;


    public Produtos(Usuario usuario, String nome, String descricao, String codigo, int qnt) {
        this.nome = nome;
        this.descricao = descricao;
        this.codigo = codigo;
        this.qnt = qnt;
        this.usuario = usuario;
        setStatus(qnt);
    }

    public Produtos() { 
        
    }

    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getDescricao() {
        return descricao;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public String getCodigo() {
        return codigo;
    }


    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    public int getQnt() {
        return qnt;
    }


    public void setQnt(int qnt) {
        this.qnt = qnt;
    }


    public Usuario getUsuario() {
        return usuario;
    }


    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(int qnt) {
    if (qnt == 0) {
        this.status = "esgotado";
    } else if (qnt < 5) {
        this.status = "estoque baixo";
    } else {
        this.status = "em estoque";
    }
}

    
}
