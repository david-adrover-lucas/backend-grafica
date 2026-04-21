package com.drover.demo.backend.dto.response;





public class CuentaResponseDTO {
   
    private Integer id;
    
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    private String nombre;

    private Boolean activa;


    public Boolean getActiva() {
        return activa;
    }


    public void setActiva(Boolean activa) {
        this.activa = activa;
    }


  

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }




   
}
