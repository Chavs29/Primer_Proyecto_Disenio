package com.proyectoDiseno.Model;

import lombok.Data;

@Data
public class Usuario {
    int id;
    String identificacion;
    String  nombre_completo;
    String email;
    String numero_telefono;

    public Usuario(){

    }
    
}