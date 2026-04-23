package com.drover.demo.backend.repository;


import com.drover.demo.backend.entity.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    List<Usuario> findByRol(String rol);
    List<Usuario> findByActivo(Boolean activo);
    List<Usuario> findByUsuario(String usuario);
} 