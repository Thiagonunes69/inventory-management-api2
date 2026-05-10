package com.example.demo.model.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nome;
    private String senha;
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;


    public Usuario(String email_usuario, String nome_empresa, String senha) {
        this.email = email_usuario;
        this.nome = nome_empresa;
        this.senha = senha;
        this.role = UserRole.USER;
    }
    public Usuario() {
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email_usuario) {
        this.email = email_usuario;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome_empresa) {
        this.nome = nome_empresa;
    }
    public Long getId() {
        return id;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of( new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
   @Override
    public String getPassword() {
        return senha;
    }
    @Override
    public String getUsername() {
        return email;
    }

}
