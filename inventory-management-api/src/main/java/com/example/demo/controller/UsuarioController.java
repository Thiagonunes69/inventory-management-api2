package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.user.Usuario;
import com.example.demo.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }
    @GetMapping("/listar")
    public List<Usuario> listar(){
        return usuarioService.listarUsuarios();
    }
    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable Long id){
        return usuarioService.buscarPorId(id);
    }
}
