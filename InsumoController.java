package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.drover.demo.backend.dto.request.InsumoRequestDTO;
import com.drover.demo.backend.dto.response.InsumoResponseDTO;
import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.mapper.InsumoMapper;
import com.drover.demo.backend.service.InsumoService;

import jakarta.validation.Valid;


@RestController
@CrossOrigin(origins = "http://localhost:5173")

@RequestMapping("/insumos")
public class InsumoController {

    private final InsumoService service;

    public InsumoController(InsumoService service){
        this.service = service;
    }

    @PostMapping
    public InsumoResponseDTO crear(@Valid @RequestBody InsumoRequestDTO dto){
        Insumo insumo = InsumoMapper.toEntity(dto);
        Insumo guardar = service.guardar(insumo);
        return InsumoMapper.toDTO(guardar);
    }

    @GetMapping
    public List<InsumoResponseDTO> listar(){
        return service.listar()
        .stream()
        .map(InsumoMapper:: toDTO)
        .toList();

    }

    @GetMapping("/activos")
    public List<Insumo> listarActivos(){
        return service.listarActivos();
    }

    @GetMapping("/buscar")
    public List<Insumo> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Insumo.TipoInsumos tipo){

        return service.buscar(nombre, tipo);
    }

    @GetMapping("/empresa/{empresaId}")
    public List<Insumo> porEmpresa(@PathVariable Integer empresaId){
        return service.buscarPorEmpresa(empresaId);
    }

    @PutMapping("/{id}")
    public Insumo actualizar(@PathVariable Integer id, @RequestBody Insumo i){
        i.setId(id);
        return service.guardar(i);
    }

    @DeleteMapping("/{id}/desactivar")
    public void desactivar(@PathVariable Integer id){
        service.desactivar(id);
    }
}
